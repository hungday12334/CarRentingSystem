package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.service.AccountService;
import hsf302.he191662.hungnt.carrentingsystem.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CarService carService;
    @GetMapping
    public String index(Model model) {
        model.addAttribute("listCar", carService.findTop5ByOrderByRentedDesc());
        return "customer/index";
    }

    @GetMapping("/cars")
    public String cars() {
        return "customer/cars";
    }

    @GetMapping("rental-history")
    public String rentalHistory() {
        return "customer/rental-history";
    }

    @GetMapping("profile")
    public String profile() {
        return "customer/profile";
    }
    @GetMapping("deposit")
    public String deposit() {
        return "customer/deposit";
    }
}
