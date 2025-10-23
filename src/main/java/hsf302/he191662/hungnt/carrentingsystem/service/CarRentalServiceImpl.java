package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import hsf302.he191662.hungnt.carrentingsystem.repository.CarRentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarRentalServiceImpl implements CarRentalService{
    @Autowired
    CarRentalRepository carRentalRepository;
    @Override
    public void save(CarRental carRental) {
        carRentalRepository.save(carRental);
    }
}
