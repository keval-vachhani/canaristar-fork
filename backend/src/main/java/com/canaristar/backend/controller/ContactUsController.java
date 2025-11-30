package com.canaristar.backend.controller;

import com.canaristar.backend.entity.ContactUs;
import com.canaristar.backend.enums.ContactUsStatus;
import com.canaristar.backend.service.cloudinary.CloudinaryService;
import com.canaristar.backend.service.contactUs.ContactUsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/contact-us")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/all")
    public ResponseEntity<?> findAllByEmail(@RequestParam String email) {
        List<ContactUs> list = contactUsService.findByEmail(email);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        ContactUs contactUs = contactUsService.findById(id);

        if (contactUs == null) {
            return new ResponseEntity<>("Invalid id", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(contactUs, HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> save(
            @Valid @ModelAttribute("contactUs") ContactUs contactUs,
            @RequestPart(value = "files", required = false) MultipartFile[] files
    ) {
        try {
            LinkedList<String> imageUrls = new LinkedList<>();

            if (files != null) {
                for (MultipartFile file : files) {
                    String url = cloudinaryService.uploadImage("contact-us", file);
                    imageUrls.add(url);
                }
            }

            contactUs.setImageUrls(imageUrls);
            contactUs.setCreatedDate(LocalDateTime.now());
            contactUs.setUpdatedDate(LocalDateTime.now());
            contactUs.setStatus(ContactUsStatus.PENDING);

            ContactUs saved = contactUsService.save(contactUs);
            contactUsService.sendContactUsRequestMail(saved);

            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @Valid @RequestBody ContactUs req
    ) {
        ContactUs saved = contactUsService.findById(id);
        if (saved == null) {
            return new ResponseEntity<>("No data found", HttpStatus.NOT_FOUND);
        }

        saved.setDescription(req.getDescription());
        saved.setName(req.getName());
        saved.setMobile(req.getMobile());
        saved.setUpdatedDate(LocalDateTime.now());

        ContactUs updated = contactUsService.save(saved);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
