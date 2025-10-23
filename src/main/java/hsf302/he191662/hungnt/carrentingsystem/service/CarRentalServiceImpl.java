package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import hsf302.he191662.hungnt.carrentingsystem.repository.CarRentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarRentalServiceImpl implements CarRentalService{
    @Autowired
    CarRentalRepository carRentalRepository;
    @Override
    public void save(CarRental carRental) {
        carRentalRepository.save(carRental);
    }

    @Override
    public List<CarRental> findByUserId(Long userId) {
        return carRentalRepository.findByUserId(userId);
    }

    @Override
    public CarRental findById(Long id) {
        return carRentalRepository.findById(id).orElse(null);
    }

}
