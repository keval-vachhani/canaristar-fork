package com.canaristar.backend.service.user;

import com.canaristar.backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    Optional<User> findByMobile(String mobile);
    void saveUser(User user);
}
