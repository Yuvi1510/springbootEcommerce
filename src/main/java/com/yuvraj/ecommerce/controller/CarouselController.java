package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.CarouselImage;
import com.yuvraj.ecommerce.responses.ApiResponse;
import com.yuvraj.ecommerce.service.CarouselService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/carousel")
public class CarouselController {
    private final CarouselService service;

    public CarouselController(CarouselService carouselService) {
        this.service = carouselService;
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteCarouselImage(@PathVariable int id){
        boolean success = service.deleteCarousel(id);

            ApiResponse response = null;
        if(success){
            response = new ApiResponse("Success", HttpStatus.OK);
        }else{
            response = new ApiResponse("Failed", HttpStatus.BAD_REQUEST);

        }

        return response;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @RequestParam("title") String title,
            @RequestParam("subTitle") String subtitle,
            @RequestParam("file")MultipartFile file
            ){
       try{
           String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

           Path dirPath = Path.of("uploads/");
           Files.createDirectories(dirPath);

           Path filePath = Path.of(dirPath + File.separator + fileName);
           Files.write(filePath, file.getBytes());

           CarouselImage image = new CarouselImage(title, subtitle, fileName);
           CarouselImage savedImage = service.addCarousel(image);

           return ResponseEntity.ok().body(savedImage);
       } catch (IOException e) {
           System.out.println(e.getMessage());
           return ResponseEntity.internalServerError().body("uploadFailed");
       }
    }


    @GetMapping
    public ResponseEntity<List<CarouselImage>> getCarousels(){

        List<CarouselImage> images = service.getAllCarousels();

        return ResponseEntity.ok().body(images);
    }

}
