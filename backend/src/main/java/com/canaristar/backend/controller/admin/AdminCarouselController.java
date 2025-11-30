package com.canaristar.backend.controller.admin;

import com.canaristar.backend.entity.Carousel;
import com.canaristar.backend.service.carousel.CarouselService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/carousel")
public class AdminCarouselController {

    @Autowired
    private CarouselService carouselService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Carousel> getAllCarousels() {
        return carouselService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/featured")
    public ResponseEntity<?> getFeaturedCarousels() {
        List<Carousel> carousels = carouselService.findByFeatured(true);

        if (carousels.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(carousels, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/between")
    public ResponseEntity<?> getCarouselsByCreatedAtBetween(
            @RequestParam String start,
            @RequestParam String end
    ){
        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);

        List<Carousel> list = carouselService.findByCreatedAtBetween(s, e);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search")
    public ResponseEntity<?> getCarouselsByName(@RequestParam String token){
        List<Carousel> list = carouselService.findByTitle(token);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveCarousel(
            @Valid @ModelAttribute("carousel") Carousel carousel,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws Exception {

        if(carousel == null) {
            return ResponseEntity.badRequest().build();
        }

        String imageUrl = "";
        if(file != null && !file.isEmpty()) {
            imageUrl = carouselService.uploadImage(file);
        }

        carousel.setImageUrl(imageUrl);
        carousel.setCreatedAt(LocalDateTime.now());

        carouselService.save(carousel);

        return ResponseEntity.ok(carousel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarousel(@PathVariable String id) {
        String res = carouselService.deleteById(id);

        return new ResponseEntity<>(res,  HttpStatus.OK);
    }
}
