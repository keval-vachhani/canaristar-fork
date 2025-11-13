package com.canaristar.backend.repository;

import com.canaristar.backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAll();
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByMobile(String mobile);
}
