package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import hsf302.he191662.hungnt.carrentingsystem.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @GetMapping()
    public String index() {
        return "redirect:admin/customers";
    }

    @GetMapping("/customers")
    public String customers(HttpServletRequest request, HttpServletRequest response, HttpServletRequest session, Model model) {
        String customerIdFilter = request.getParameter("customerIdFilter");
        String customerNameFilter = request.getParameter("customerNameFilter");
        List<Customer> customers = customerService.findByCustomerIdAndCustomerName(customerIdFilter,customerNameFilter);
        model.addAttribute("customerIdFilter", customerIdFilter);
        model.addAttribute("customerNameFilter", customerNameFilter);
        model.addAttribute("customers", customers);
        return "admin/customer-management";
    }

}
