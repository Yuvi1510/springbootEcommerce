package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CarouselRepository;
import com.yuvraj.ecommerce.entity.CarouselImage;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return false;
    }

    @Override
    public List<CarouselImage> getAllCarousels() {
        return carouselRepository.findAll();
    }
}
