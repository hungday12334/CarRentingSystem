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
import java.time.format.DateTimeParseException;
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
        List<Customer> customers = customerService.findByCustomerIdAndCustomerName(customerIdFilter, customerNameFilter);
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
        Validation valid = new Validation();
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        String email = request.getParameter("email").trim();
        String customerName = request.getParameter("customerName").trim();
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("email", email);
        model.addAttribute("customerName", customerName);


        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "Tên đăng nhập không đự bỏ trống.");
            return "admin/customer-create";
        } else if (accountService.existsByUsername(username)) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại.");
            return "admin/customer-create";
        }

        if (password == null || password.trim().isEmpty() || password.length() < 6) {
            model.addAttribute("error", "Mật khẩu không hợp lệ.");
            return "admin/customer-create";
        }

        if (email == null || email.trim().isEmpty() || !valid.isValidEmail(email)) {
            model.addAttribute("error", "Email không hợp lệ.");
            return "admin/customer-create";
        } else if (accountService.existsByEmail(email)) {
            model.addAttribute("error", "Email đã tồn tại.");
            return "admin/customer-create";
        }

        if (customerName == null || customerName.trim().isEmpty()) {
            model.addAttribute("error", "Tên Khách hàng không được bỏ trống");
            return "admin/customer-create";
        }
        Account account = new Account(username.trim(), password.trim(), email.trim(), "customer", true);
        Customer customer = new Customer(customerName.trim(), email.trim(), password.trim(), 0.0);
        account.setCustomer(customer);
        customer.setAccount(account);
        accountService.save(account);
        model.addAttribute("success", "Tạo khách hàng mới thành công");
        return "admin/customer-create";
    }

    @GetMapping("/customers/update")
    public String updateCustomer(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {

        String customerId = request.getParameter("customerId");
        try {
            Long id = Long.parseLong(customerId);
            Customer customer = customerService.findByCustomerId(id);
            model.addAttribute("customer", customer);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không tồn tại khách hàng này");
            return "redirect:/admin/customers";
        }
        return "admin/customer-update";
    }

    @PostMapping("customers/update")
    public String updateCustomerP(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Validation valid = new Validation();
        try {
            Long id = Long.parseLong(request.getParameter("customerId"));
            Customer customer = customerService.findByCustomerId(id);
            model.addAttribute("customer", customer);
            if (customer == null) {
                redirectAttributes.addFlashAttribute("error", "Không tồn tại khách hàng này");
                return "redirect:/admin/customers";
            }

            String customerName = request.getParameter("customerName").trim();
            String email = request.getParameter("email").trim();
            String password = request.getParameter("password").trim();
            String mobile = (request.getParameter("mobile") != null) ? request.getParameter("mobile").trim() : "";
            String identityCard = (request.getParameter("identityCard") != null) ? request.getParameter("identityCard").trim() : "";
            String licenceNumber = (request.getParameter("licenceNumber") != null) ? request.getParameter("licenceNumber").trim() : "";
            String licenceDate = request.getParameter("licenceDate");

            if (customerName.isEmpty()) {
                model.addAttribute("error", "Tên khách hàng không hợp lệ.");
                return "admin/customer-update";
            }

            if (email.isEmpty() || !valid.isValidEmail(email)) {
                model.addAttribute("error", "Email không hợp lệ.");
                return "admin/customer-update";
            } else if (accountService.existsByEmail(email) && !email.equals(customer.getEmail())) {
                model.addAttribute("error", "Email đã tồn tại.");
                return "admin/customer-update";
            }

            if (password.isEmpty() || password.length() < 6) {
                model.addAttribute("error", "Mật khẩu không hợp lệ.");
                return "admin/customer-update";
            }

            if (!mobile.isEmpty() && !valid.isValidVietnamPhone(mobile)) {
                model.addAttribute("error", "Số điện thoại không hợp lệ.");
                return "admin/customer-update";
            }

            if (!identityCard.isEmpty()) {
                if (identityCard.length() != 12) {
                    model.addAttribute("error", "CCCD phải có 12 ký tự");
                    return "admin/customer-update";
                }

                if(customerService.findByIdentityCard(identityCard)!=null){
                    model.addAttribute("error", "CCCD đã tồn tại");
                    return "admin/customer-update";
                }

            }

            if (!licenceNumber.isEmpty()) {
                if (licenceNumber.length() != 12) {
                    model.addAttribute("error", "GPLX phải có 12 ký tự");
                    return "admin/customer-update";
                }

                if(customerService.findByLicenceNumber(licenceNumber)!=null){
                    model.addAttribute("error", "GPLX đã tồn tại");
                    return "admin/customer-update";
                }

            }

            try {
                if (licenceDate != null && !licenceDate.trim().isEmpty()) {
                    customer.setLicenceDate(LocalDate.parse(licenceDate));
                }
            } catch (DateTimeParseException e) {
                model.addAttribute("error", "Ngày cấp GPLX không hợp lệ.");
                return "admin/customer-update";
            }

            // Cập nhật thông tin
            customer.setCustomerName(customerName);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setMobile(mobile);
            customer.setIdentityCard(identityCard == "" ? null : identityCard);
            customer.setLicenceNumber(licenceNumber == "" ? null : licenceNumber);

            customerService.save(customer);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công");
            return "redirect:/admin/customers";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Không tồn tại khách hàng này");
            return "redirect:/admin/customers";
        }
    }
    @PostMapping("customers/deactive")
    public String deactiveCustomer(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        String sId= request.getParameter("customerId");
        Long customerId;
        try{
            customerId = Long.parseLong(sId);
        }catch(Exception e) {
            redirectAttributes.addFlashAttribute("error","Không tồn tại người dùng.");
            return "redirect:/admin/customers";
        }
        Account account = accountService.findByCustomerId(customerId);
        if(account==null){
            redirectAttributes.addFlashAttribute("error","Không tồn tại người dùng.");
            return "redirect:/admin/customers";
        }else if(!("customer".equalsIgnoreCase(account.getRole())||"admin".equalsIgnoreCase(account.getRole()))||account.isActive() == false){
            redirectAttributes.addFlashAttribute("error","Tài khoản này đã bị vô hiệu hóa trước đó.");
            return "redirect:/admin/customers";
        }else{
            account.setActive(false);
            accountService.save(account);
            redirectAttributes.addFlashAttribute("success", "Vô hiệu hóa thành công, bây giờ người dùng này không thể hoạt động trong hệ thống nữa");
            return "redirect:/admin/customers";
        }
    }
}
