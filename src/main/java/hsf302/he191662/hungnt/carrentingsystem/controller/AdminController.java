package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.entity.Account;
import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import hsf302.he191662.hungnt.carrentingsystem.service.AccountService;
import hsf302.he191662.hungnt.carrentingsystem.service.CustomerService;
import hsf302.he191662.hungnt.carrentingsystem.util.Validation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

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

    @GetMapping("/customers/create")
    public String createCustomer() {
        return "admin/customer-create";
    }

    @PostMapping("/customers/create")
    public String createCustomerP(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Validation  valid= new Validation();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String email = request.getParameter("email").trim();
        String customerName = request.getParameter("customerName").trim();
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("email", email);
        model.addAttribute("customerName", customerName);


        if(username==null||username.trim().isEmpty()){
            model.addAttribute("error", "Tên đăng nhập không đự bỏ trống.");
            return "admin/customer-create";
        }else if(accountService.existsByUsername(username)){
            model.addAttribute("error","Tên đăng nhập đã tồn tại.");
            return "admin/customer-create";
        }

        if(password==null||password.trim().isEmpty()||password.length()<6){
            model.addAttribute("error","Mật khẩu không hợp lệ.");
            return "admin/customer-create";
        }

        if(email==null||email.trim().isEmpty()||!valid.isValidEmail(email)){
            model.addAttribute("error", "Email không hợp lệ.");
            return "admin/customer-create";
        }else if(accountService.existsByEmail(email)){
            model.addAttribute("error","Email đã tồn tại.");
            return "admin/customer-create";
        }

        if(customerName==null||customerName.trim().isEmpty()){
            model.addAttribute("error","Tên Khách hàng không được bỏ trống");
            return "admin/customer-create";
        }
        Account account = new Account(username.trim(), password.trim(), email.trim(), "customer", true);
        Customer customer = new Customer(customerName.trim(),email.trim(), password.trim(),0.0);
        account.setCustomer(customer);
        customer.setAccount(account);
        accountService.save(account);
        model.addAttribute("success", "Tạo khách hàng mới thành công");
        return "admin/customer-create";
    }
    @GetMapping("/customers/update")
    public String updateCustomer() {
        return "admin/customer-update";
    }


}
