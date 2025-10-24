package hsf302.he191662.hungnt.carrentingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/rentals")
public class AdminRentalManagement {
    @RequestMapping
    public String index() {
        return "admin/rental-management";
    }
}
