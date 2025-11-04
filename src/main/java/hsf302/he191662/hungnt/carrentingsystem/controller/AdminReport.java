package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import hsf302.he191662.hungnt.carrentingsystem.service.CarRentalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/reports")
public class AdminReport {
    @Autowired
    private CarRentalService carRentalService;
    @GetMapping(value = {"", "/" })
    public String index(Model model, HttpServletRequest request) {
        List<CarRental> carRentals = carRentalService.findByStatus("completed");
        int totalOrder = 0;
        double totalPrice = 0;
        double maxPrice = 0;
        if(carRentals!=null) {
            for(CarRental carRental : carRentals) {
                totalOrder++;
                totalPrice += carRental.getRentPrice();
                if(carRental.getRentPrice() > maxPrice) {
                    maxPrice = carRental.getRentPrice();
                }
            }
        }
        model.addAttribute("totalOrders", totalOrder);
        model.addAttribute("totalAmount", totalPrice);
        model.addAttribute("maxAmount", maxPrice);
        model.addAttribute("rentals", carRentals);
        return "admin/report";
    }

    @GetMapping("/filter")
    public String filter(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        List<CarRental> carRentals = carRentalService.filter(request.getParameter("fromDate"), request.getParameter("toDate"), request.getParameter("filterType"));
        int totalOrder = 0;
        double totalPrice = 0;
        double maxPrice = 0;
        if(carRentals!=null) {
            for(CarRental carRental : carRentals) {
                totalOrder++;
                totalPrice += carRental.getRentPrice();
                if(carRental.getRentPrice() > maxPrice) {
                    maxPrice = carRental.getRentPrice();
                }
            }
        }
        model.addAttribute("rentals", carRentals);
        model.addAttribute("fromDate", request.getParameter("fromDate"));
        model.addAttribute("toDate", request.getParameter("toDate"));
        model.addAttribute("orderBy", request.getParameter("orderBy"));
        model.addAttribute("totalOrders", totalOrder);
        model.addAttribute("totalAmount", totalPrice);
        model.addAttribute("maxAmount", maxPrice);
        return "admin/report";
    }
}
