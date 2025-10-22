package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Car;

import java.util.List;

public interface CarService {
    public List<Car> findTop5ByOrderByRentedDesc();
    public List<Car> findAll();
    public List<String> findDistinctByColor();
    public List<Car> filter(String name, String color, String status, String producerId, String year, String priceFrom, String priceTo);
}
