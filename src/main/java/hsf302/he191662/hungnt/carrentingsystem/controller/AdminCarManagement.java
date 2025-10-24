package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.entity.Car;
import hsf302.he191662.hungnt.carrentingsystem.service.CarService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/cars")
public class AdminCarManagement {
    @Autowired
    private CarService carService;

    @RequestMapping
    public String index(Model model, HttpServletRequest request) {
        String carName = request.getParameter("carNameFilter");
        String carId = request.getParameter("carIdFilter");
        model.addAttribute("carNameFilter", carName);
        model.addAttribute("carIdFilter", carId);
        List<Car> cars= carService.findByCarNameAndCarId(carId, carName);
        model.addAttribute("cars", cars);
        return "admin/car-management";
    }
}
