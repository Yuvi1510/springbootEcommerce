package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.CarouselImage;

public interface CarouselService {
    CarouselImage addCarousel(CarouselImage carousel);
    CarouselImage updateCarousel(CarouselImage carousel);
    boolean deleteCarousel(int carouselId);
}
