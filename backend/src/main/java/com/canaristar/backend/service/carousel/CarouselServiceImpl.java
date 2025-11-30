package com.canaristar.backend.service.carousel;

import com.canaristar.backend.entity.Carousel;
import com.canaristar.backend.repository.CarouselRepository;
import com.canaristar.backend.service.cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselRepository carouselRepository;


    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public Carousel save(Carousel carousel) {
        return carouselRepository.save(carousel);
    }

    @Override
    public List<Carousel> findByTitle(String title) {
        List<Carousel> carousels = carouselRepository.findByTitleContainingIgnoreCase(title);

        return carousels;
    }

    @Override
    public Carousel findById(String id) {
        return carouselRepository.findById(id).orElse(null);
    }

    @Override
    public String uploadImage(MultipartFile file) throws Exception {
        return cloudinaryService.uploadImage("carousel", file);
    }

    @Override
    public String deleteImage(String imageUrl) {
        String publicUrl = cloudinaryService.extractPublicId(imageUrl);

        return cloudinaryService.deleteImage(publicUrl);
    }

    @Override
    public String deleteById(String id) {
        Carousel carousel = findById(id);

        if (carousel == null) {
            return "Invalid Id";
        }

        String res = deleteImage(carousel.getImageUrl());
        if(!res.equals("true")) {
            return "Image could not be deleted";
        }

        carouselRepository.deleteById(id);

        return "carousel deleted";
    }

    @Override
    public List<Carousel> findAll() {
        return carouselRepository.findAll();
    }

    @Override
    public List<Carousel> findByFeatured(boolean featured) {
        return carouselRepository.findByFeatured(featured);
    }

    @Override
    public List<Carousel> findByCreatedAtBetween(LocalDateTime s, LocalDateTime e) {
        return carouselRepository.findByCreatedAtBetween(s, e);
    }
}
