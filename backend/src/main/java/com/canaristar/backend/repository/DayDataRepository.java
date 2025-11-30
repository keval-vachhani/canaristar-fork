package com.canaristar.backend.repository;

import com.canaristar.backend.entity.DayData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DayDataRepository extends MongoRepository<DayData, String> {
    Optional<DayData> findByDate(String date);
    List<DayData> findByDateBetween(String start, String end);
}

