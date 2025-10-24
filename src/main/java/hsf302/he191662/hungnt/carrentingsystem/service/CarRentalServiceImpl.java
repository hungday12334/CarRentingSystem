package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import hsf302.he191662.hungnt.carrentingsystem.repository.CarRentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public List<CarRental> filter(String status, Long customerId, String rentalDateFrom, String rentalDateTo, String periodDay, String rentPriceMin, String rentPriceMax) {
        if(status!=null && status.trim().isEmpty()) status=null;

        //Xu ly local date

        LocalDate rentalDateFromL;
        try{
            rentalDateFromL = LocalDate.parse(rentalDateFrom);
        }catch (Exception e){
            rentalDateFromL = null;
        }

        LocalDate rentalDateToL;
        try{
            rentalDateToL = LocalDate.parse(rentalDateTo);
        }catch (Exception e){
            rentalDateToL = null;
        }

        //Xu ly period day
        Integer periodDayI;
        try{
            periodDayI = Integer.parseInt(periodDay);
        }catch (Exception e){
            periodDayI = null;
        }

        //Xu ly rent price
        Double rentPriceMinD;
        try{
            rentPriceMinD = Double.parseDouble(rentPriceMin);
        }catch (Exception e){
            rentPriceMinD = 0.0;
        }

        //Xu ly rent price max
        Double rentPriceMaxD;
        try{
            rentPriceMaxD = Double.parseDouble(rentPriceMax);
        }catch (Exception e){
            rentPriceMaxD = Double.MAX_VALUE;
        }
        return carRentalRepository.filter(status, customerId, rentalDateFromL, rentalDateToL, periodDayI, rentPriceMinD, rentPriceMaxD);
    }

    @Override
    public List<CarRental> findAll() {
        return carRentalRepository.findAll();
    }

}
