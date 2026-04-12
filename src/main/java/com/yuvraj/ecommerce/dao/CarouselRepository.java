package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.CarouselImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarouselRepository extends JpaRepository<CarouselImage, Integer> {
}