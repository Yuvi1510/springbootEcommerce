package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CategoryRepository;
import com.yuvraj.ecommerce.dao.ProductRepository;
import com.yuvraj.ecommerce.dao.StoreRepository;
import com.yuvraj.ecommerce.entity.Category;
import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.ProductImage;
import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.requests.AddProductRequest;
import com.yuvraj.ecommerce.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    private final StoreRepository storeRepository;
    private final StoreService storeService;
    private final CategoryRepository categoryRepository;
    @Value("${imageDir}")
    private String dir;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, StoreRepository storeRepository, CategoryService categoryService, StoreService storeService, CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
        this.categoryService = categoryService;
        this.storeService = storeService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(AddProductRequest req) {
        // create a new product object and set the details
          Product product = new Product();
          product.setName(req.getName());
          product.setBrand(req.getBrand());
          product.setDescription(req.getDescription());
          product.setPrice(req.getPrice());
          product.setQuantity(req.getQuantity());
          product.setCreatedAt(LocalDate.now());
          product.setUpdatedAt(LocalDate.now());

          Store store = storeRepository.findById(1).get();
          product.setStore(store);// hardcoded for testing purpose later we will change it
        Category category = categoryService.getCategoryByid(1);
        product.setCategory(category);
          product.setSku(""); // db columns is not nullable so i passed empty string, later we will change it
            product.setSlug("");

          // store the name of uploaded files in an arraylist
        // if any error eccurs then we can loop through this list and delete all the files
          List<String> uploadedFiles = new ArrayList<>();
      try{
          // add all images to product
          for(int i=0; i < req.getImages().size(); i++ ){
              ProductImage image = new ProductImage();
              image.setAltText(req.getName());
              image.setDisplayOrder(i);

              // if the image is the first image then it will be primary image
              image.setPrimary(i == 0);

              // add the image to the products
              product.addImage(image);

              // if one image fails to get uploaded then revert all uploads and show error msg
              String uploadedFileName =  writeImageToFile(req.getImages().get(i));
              if(uploadedFileName != null ){
                  uploadedFiles.add(uploadedFileName);
                  image.setImageName(uploadedFileName);
              }else {
                  throw new RuntimeException("Failed to upload image");
              }
          }

          Product savedProduct = productRepository.save(product);
          int end = Math.min(product.getName().length(), 3);
          int brandEnd = Math.min(product.getBrand().length(), 3);
          savedProduct.setSku(
                  savedProduct.getBrand().substring(0,brandEnd) + "-"+
                          savedProduct.getName().substring(0,end) + "-"+
                          savedProduct.getProductId()
          );
          savedProduct.setSlug(Utils.generateSlug(req.getName(),  savedProduct.getProductId()));

          store.addProducts(savedProduct);
          categoryRepository.findById(1).get().addProduct(savedProduct); //  hardcoded for testing
          return productRepository.save(savedProduct);

        } catch (Exception e) {
          // if any error occurs then delete all the uploaded files
          for(String file: uploadedFiles){
              deleteUploadedFile(file);
          }
          throw e;
      }
    }

    private void deleteUploadedFile(String file) {
        try{
            Path path = Path.of(dir + File.separator + file);
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String writeImageToFile(MultipartFile file) {
        try{
            Path dirPath = Path.of(dir);

            if(!Files.exists(dirPath)){
                Files.createDirectories(dirPath);
            }

//            String fileName = UUID.randomUUID().toString().substring(0,10) +  file.getOriginalFilename();
            // original file name may contain unsupported characters
            String fileName = UUID.randomUUID().toString().substring(0,10) +  file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf(".")
            );
            Path path = Path.of(dir + File.separator + fileName);

            Files.write(path, file.getBytes());
            return fileName;
        } catch (IOException e) {
            System.out.println("Failed to upload file");
            return null;
        }
    }
}
