package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CategoryRepository;
import com.yuvraj.ecommerce.dao.ProductRepository;
import com.yuvraj.ecommerce.dao.StoreRepository;
import com.yuvraj.ecommerce.dao.UserRepository;
import com.yuvraj.ecommerce.entity.*;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import com.yuvraj.ecommerce.exceptionHandling.UnAuthenticatedException;
import com.yuvraj.ecommerce.requests.AddProductRequest;
import com.yuvraj.ecommerce.utils.SecurityUtils;
import com.yuvraj.ecommerce.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService{
    private final UserRepository userRepository;
    @Value("${imageDir}")
    private String dir;

    private final StoreService storeService;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, StoreService storeService, UserRepository userRepository) {

        this.productRepository = productRepository;

        this.categoryService = categoryService;
        this.storeService = storeService;
        this.userRepository = userRepository;
    }

    @Override
    public Product findProductById(int id) {
        Optional<Product> optional =  productRepository.findById(id);

        return optional.orElseThrow(() ->  new NotFountException("Product now found with id: " + id));
    }

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findProductsByCategoryId(int id, Pageable pageable) {
        return productRepository.findProductByCategoryCategoryId(id, pageable);
    }

    @Override
    public Product addProduct(AddProductRequest req) {

        // get the authenticated user
        User authenticatedUser = SecurityUtils.getCurrentUser();

        if(authenticatedUser == null){
            throw new UnAuthenticatedException("Please login first");
        }
        Optional<User> optional = userRepository.findUserByEmail(authenticatedUser.getEmail());
        User user = optional.orElseThrow(() -> new NotFountException("User not found"));


        // create a new product object and set the details
          Product product = new Product();
          product.setName(req.getName());
          product.setBrand(req.getBrand());
          product.setDescription(req.getDescription());
          product.setPrice(req.getPrice());
          product.setQuantity(req.getQuantity());
          product.setCreatedAt(LocalDate.now());
          product.setUpdatedAt(LocalDate.now());

          // get store and category and set it to product
        Store store = user.getStore();


          product.setStore(store);
        Category category = categoryService.findCategoryByName(req.getCategory());
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
          int end = Math.min(product.getName().length(), 3); // get no of characters for sku and slug
          int brandEnd = Math.min(product.getBrand().length(), 3);
          savedProduct.setSku(
                  savedProduct.getBrand().substring(0,brandEnd) + "-"+
                          savedProduct.getName().substring(0,end) + "-"+
                          savedProduct.getProductId()
          );
          savedProduct.setSlug(Utils.generateSlug(req.getName(),  savedProduct.getProductId()));

          store.addProducts(savedProduct);
          category.addProduct(savedProduct);
          return productRepository.save(savedProduct);

        } catch (Exception e) {
          // if any error occurs then delete all the uploaded files
          for(String file: uploadedFiles){
              deleteUploadedFile(file);
          }
          throw e;
      }
    }

    @Override
    public List<Product> findProductsByStore(int id) {
        Store store = storeService.findStoreById(id);
        List<Product> products = store.getProducts();
        return products;
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
