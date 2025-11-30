package com.canaristar.backend.service.carousel;

import com.canaristar.backend.entity.Carousel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CarouselService {
    Carousel save(Carousel carousel);
    List<Carousel> findByTitle(String title);
    Carousel findById(String id);
    String uploadImage(MultipartFile file) throws Exception;
    String deleteImage(String imageUrl);
    String deleteById(String id);
    List<Carousel> findAll();
    List<Carousel> findByFeatured(boolean featured);
    List<Carousel> findByCreatedAtBetween(LocalDateTime s, LocalDateTime e);
}
