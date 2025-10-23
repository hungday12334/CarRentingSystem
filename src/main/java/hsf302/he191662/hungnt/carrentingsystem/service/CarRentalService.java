package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import hsf302.he191662.hungnt.carrentingsystem.repository.CarRentalRepository;

import java.util.List;

public interface CarRentalService {
    public void save(CarRental carRental);
    public List<CarRental> findByUserId(Long userId);
    public CarRental findById(Long id);
    public List<CarRental> filter(String status, Long customerId,String rentalDateFrom, String rentalDateTo, String periodDay, String rentPriceMin, String rentPriceMax);
}
