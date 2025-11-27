package com.canaristar.backend.controller.admin;

import com.canaristar.backend.config.AdminConfig;
import com.canaristar.backend.entity.user.User;
import com.canaristar.backend.enums.Role;
import com.canaristar.backend.request.AuthRequest;
import com.canaristar.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminConfig adminConfig;

    @PostMapping("/make-admin")
    public ResponseEntity<?> makeAdmin(@RequestBody AuthRequest authRequest) {
        Optional<User> user = userService.findByEmail(authRequest.getEmail());

        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }else if (!authRequest.getPassword().equals(adminConfig.getAdminCreationPassword())) {
            return new ResponseEntity<>("Incorrect password", HttpStatus.UNAUTHORIZED);
        }

        User existingUser = user.get();

        if (existingUser.getRole().equals(Role.ADMIN)) {
            return new ResponseEntity<>("You are already an Admin", HttpStatus.UNAUTHORIZED);
        }

        existingUser.setRole(Role.ADMIN);
        userService.saveUser(existingUser);

        return  new ResponseEntity<>("Admin created", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User existingUser = user.get();
        existingUser.setRole(Role.ADMIN);

        userService.saveUser(existingUser);

        return  new ResponseEntity<>("Admin created", HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/search-users")
    public ResponseEntity<?> searchUsers(@RequestParam String token) {
        List<User> all = userService.findAll();

        if(all.isEmpty()) {
            return new ResponseEntity<>("No users", HttpStatus.NOT_FOUND);
        }

        List<User> list = all.stream()
                .filter(user -> user.getName().contains(token))
                .toList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        if(page < 1 || size < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<User> users = userService.findPaginated(page - 1, size);

        if (users.isEmpty()) {
            return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser(){
        List<User> users = userService.findAll();

        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
