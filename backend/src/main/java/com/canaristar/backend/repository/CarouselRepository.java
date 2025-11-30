package com.canaristar.backend.repository;

import com.canaristar.backend.entity.Carousel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarouselRepository extends MongoRepository<Carousel, String> {
    List<Carousel> findByFeatured(boolean featured);
    List<Carousel> findByTitleContainingIgnoreCase(String title);
    List<Carousel> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
