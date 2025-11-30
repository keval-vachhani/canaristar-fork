package com.canaristar.backend.service.dayDataService;

import com.canaristar.backend.entity.DayData;
import com.canaristar.backend.repository.DayDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DayDataServiceImpl implements DayDataService {

    @Autowired
    private DayDataRepository dayDataRepository;

    private DayData today() {
        String today = LocalDate.now().toString();

        return dayDataRepository.findByDate(today).orElseGet(() -> {
            DayData d = new DayData();

            d.setDate(today);
            d.setVisitors(0);
            d.setRevenue(0);
            d.setNewUsers(0);
            d.setOrderIds(new ArrayList<>());
            d.setProductViews(new HashMap<>());

            return dayDataRepository.save(d);
        });
    }

    @Override
    public void addVisitor() {
        DayData d = today();
        d.setVisitors(d.getVisitors() + 1);

        dayDataRepository.save(d);
    }

    @Override
    public void addOrder(String orderId, double amount) {
        DayData d = today();

        d.getOrderIds().add(orderId);
        d.setRevenue(d.getRevenue() + amount);

        dayDataRepository.save(d);
    }

    @Override
    public void addNewUser() {
        DayData d = today();
        d.setNewUsers(d.getNewUsers() + 1);

        dayDataRepository.save(d);
    }

    @Override
    public void incrementProductView(String productId) {
        DayData d = today();
        Map<String, Integer> map = d.getProductViews();

        map.put(productId, map.getOrDefault(productId, 0) + 1);
        d.setProductViews(map);

        dayDataRepository.save(d);
    }

    @Override
    public DayData getTodayData() {
        return today();
    }

    @Override
    public DayData getDataByDate(String date) {
        return dayDataRepository.findByDate(date).orElse(null);
    }

    @Override
    public List<DayData> getDataBetween(String startDate, String endDate) {
        return dayDataRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public void resetToday() {
        String today = LocalDate.now().toString();
        DayData d = dayDataRepository.findByDate(today).orElse(null);

        if (d == null) return;

        d.setVisitors(0);
        d.setRevenue(0);
        d.setNewUsers(0);
        d.setOrderIds(new ArrayList<>());
        d.setProductViews(new HashMap<>());

        dayDataRepository.save(d);
    }

    @Override
    public List<DayData> findAll() {
        return dayDataRepository.findAll();
    }
}
