package com.canaristar.backend.service.user;

import com.canaristar.backend.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    List<User> findAll();
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    Optional<User> findByMobile(String mobile);
    User saveUser(User user);
    void deleteUser(User user);
    List<User> findPaginated(int i, int size);
}
