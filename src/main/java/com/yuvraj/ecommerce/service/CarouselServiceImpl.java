package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CarouselRepository;
import com.yuvraj.ecommerce.entity.CarouselImage;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
public class CarouselServiceImpl implements CarouselService{

    private final CarouselRepository carouselRepository;

    public CarouselServiceImpl(CarouselRepository carouselRepository) {
        this.carouselRepository = carouselRepository;
    }

    @Override
    public CarouselImage addCarousel(CarouselImage carousel) {
        return carouselRepository.save(carousel);
    }

    @Override
    public CarouselImage updateCarousel(CarouselImage carousel) {
        return null;
    }

    @Override
    public boolean deleteCarousel(int carouselId) {
        try {
            // First, find the image record (before deleting from DB)
            Optional<CarouselImage> optional = carouselRepository.findById(carouselId);

            if (optional.isEmpty()) {
                throw new RuntimeException("Carousel image not found with id: " + carouselId);
            }

            CarouselImage img = optional.get();

            // Delete the physical file first
            Path filePath = Path.of("uploads/" + File.separator + img.getImagePath());

            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                } else {
                    // Log warning but continue with database deletion
                    System.out.println("File not found: " + filePath);
                }
            } catch (IOException e) {
                // Log error but decide whether to continue or throw
                System.err.println("Failed to delete file: " + e.getMessage());
                // Option: throw new RuntimeException("Failed to delete image file", e);
            }

            // Delete from database
            carouselRepository.deleteById(carouselId);

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete carousel: " + e.getMessage(), e);
        }
    }
    @Override
    public List<CarouselImage> getAllCarousels() {
        return carouselRepository.findAll();
    }
}
