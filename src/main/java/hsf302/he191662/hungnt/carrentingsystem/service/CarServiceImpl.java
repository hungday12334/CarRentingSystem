package hsf302.he191662.hungnt.carrentingsystem.service;

import hsf302.he191662.hungnt.carrentingsystem.entity.Car;
import hsf302.he191662.hungnt.carrentingsystem.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> findTop5ByOrderByRentedDesc() {
        return carRepository.findTop5ByOrderByRentedDesc();
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public List<String> findDistinctByColor() {
        return carRepository.findDistinctByColor();
    }

    @Override
    public List<Car> filter(String name, String color, String status,
                            String producerId, String year,
                            String priceFrom, String priceTo) {

        // Chuyển producerId sang Long
        Long producerIdLong = null;
        if (producerId != null && !producerId.isEmpty()) {
            try {
                producerIdLong = Long.parseLong(producerId);
            } catch (NumberFormatException e) {
                producerIdLong = null; // bỏ qua nếu không hợp lệ
            }
        }

        // Chuyển year sang Long
        Integer yearLong = null;
        if (year != null && !year.isEmpty()) {
            try {
                yearLong = Integer.parseInt(year);
            } catch (NumberFormatException e) {
                yearLong = null;
            }
        }

        // Chuyển priceFrom sang Double
        Double priceFromDouble = null;
        if (priceFrom != null && !priceFrom.isEmpty()) {
            try {
                priceFromDouble = Double.parseDouble(priceFrom);
            } catch (NumberFormatException e) {
                priceFromDouble = null;
            }
        }

        // Chuyển priceTo sang Double
        Double priceToDouble = null;
        if (priceTo != null && !priceTo.isEmpty()) {
            try {
                priceToDouble = Double.parseDouble(priceTo);
            } catch (NumberFormatException e) {
                priceToDouble = null;
            }
        }

        // Xử lý name, color, status: nếu rỗng → coi như null
        String nameParam = (name != null && !name.isEmpty()) ? name : null;
        String colorParam = (color != null && !color.isEmpty()) ? color : null;
        String statusParam = (status != null && !status.isEmpty()) ? status : null;

        // Gọi repository
        return carRepository.filter(
                nameParam,
                colorParam,
                statusParam,
                producerIdLong,
                yearLong,
                priceFromDouble,
                priceToDouble
        );


    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }
}
