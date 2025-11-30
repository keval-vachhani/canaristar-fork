package com.canaristar.backend.controller.admin;

import com.canaristar.backend.entity.ContactUs;
import com.canaristar.backend.enums.ContactUsStatus;
import com.canaristar.backend.service.contactUs.ContactUsService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/contact-us")
public class AdminContacUsController {

    @Autowired
    private ContactUsService contactUsService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<ContactUs> list = contactUsService.findAll();

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/status")
    public ResponseEntity<?> findAllByStatus(@RequestParam ContactUsStatus status) {
        List<ContactUs> list = contactUsService.findAllByStatus(status);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all/between")
    public ResponseEntity<?> findAllBetween(
            @RequestParam String start,
            @RequestParam String end
    ) {
        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);

        List<ContactUs> list = contactUsService.findBetweenDates(s, e);

        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/response/{id}")
    public ResponseEntity<?> adminResponseUpdate(
            @PathVariable String id,
            @RequestBody ContactUs req
    ) throws MessagingException {

        if (req.getReply() == null || req.getReply().isEmpty()) {
            return new ResponseEntity<>("Reply text is required.", HttpStatus.BAD_REQUEST);
        }


        ContactUs saved = contactUsService.findById(id);

        if (saved == null) {
            return new ResponseEntity<>("Invalid id", HttpStatus.NOT_FOUND);
        }

//        status update
        if (req.getStatus() == null) {
            saved.setStatus(ContactUsStatus.RESOLVED);
        } else {
            saved.setStatus(req.getStatus());
        }

        saved.setReply(req.getReply());
        saved.setUpdatedDate(LocalDateTime.now());

        ContactUs updated = contactUsService.save(saved);
        contactUsService.sendContactUsResponseMail(updated);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }
}
