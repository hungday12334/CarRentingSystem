package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Car;

import java.util.List;

public interface CarService {
    public List<Car> findTop5ByOrderByRentedDesc();
}
