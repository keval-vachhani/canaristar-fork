package com.canaristar.backend.service.dayDataService;

import com.canaristar.backend.entity.DayData;
import java.util.List;

public interface DayDataService {
    void addVisitor();
    void addOrder(String orderId, double amount);
    void addNewUser();
    void incrementProductView(String productId);
    DayData getTodayData();
    DayData getDataByDate(String date);
    List<DayData> getDataBetween(String startDate, String endDate);
    void resetToday();
    List<DayData> findAll();
}
