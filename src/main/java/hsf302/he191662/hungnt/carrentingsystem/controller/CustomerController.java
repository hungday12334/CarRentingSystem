package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.entity.Account;
import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import hsf302.he191662.hungnt.carrentingsystem.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CarService carService;
    @Autowired
    private CarProducerService carProducerService;
    @Autowired
    private CustomerService customerService;
    @GetMapping
    public String index(Model model) {
        model.addAttribute("listCar", carService.findTop5ByOrderByRentedDesc());
        return "/customer/index";
    }

    @GetMapping("/cars")
    public String cars(Model model, HttpServletRequest request) {
        String carName = request.getParameter("carName");
        String color = request.getParameter("color");
        String status= request.getParameter("status");
        String producerId = request.getParameter("producerId");
        String year = request.getParameter("year");
        String priceFrom = request.getParameter("priceFrom");
        String priceTo = request.getParameter("priceTo");
        model.addAttribute("carName", carName);
        model.addAttribute("color", color);
        model.addAttribute("status", status);
        model.addAttribute("producerId", producerId);
        model.addAttribute("year", year);
        model.addAttribute("priceFrom", priceFrom);
        model.addAttribute("priceTo", priceTo);
        model.addAttribute("listCar", carService.filter(carName,color,status,producerId,year,priceFrom,priceTo));

        model.addAttribute("listColor", carService.findDistinctByColor());
        model.addAttribute("listProducer", carProducerService.findAll());
        return "/customer/cars";
    }

    @GetMapping("rental-history")
    public String rentalHistory() {
        return "/customer/rental-history";
    }

    @GetMapping("profile")
    public String profile(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if(account==null) {
            model.addAttribute("error", "Session không lưu account");
            return "/customer/profile";
        }

        Customer customer= customerService.findByAccountId(account.getAccountId());
        if(customer==null) {
            model.addAttribute("error", "Không lấy được customer");
            return "/customer/profile";
        }
        model.addAttribute("customer", customer);
        return "/customer/profile";
    }

    @PostMapping("/profile")
    public String profilePost(HttpServletRequest request, Model model, HttpSession session) {
        // Kiểm tra session
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/auth/login";
        }

        // Lấy dữ liệu từ form
        Long customerId = Long.valueOf(request.getParameter("customerId"));
        String customerName = request.getParameter("customerName");
        String mobile = request.getParameter("mobile");
        String birthdayStr = request.getParameter("birthday");
        String identityCard = request.getParameter("identityCard");
        String licenceNumber = request.getParameter("licenceNumber");
        String licenceDateStr = request.getParameter("licenceDate");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validation
        if (customerName == null || customerName.trim().isEmpty()) {
            model.addAttribute("error", "Họ và tên không được để trống.");
            model.addAttribute("customer", customerService.findByCustomerId(customerId));
            return "customer/profile";
        }

        // Kiểm tra customerId khớp với tài khoản
        if (!customerId.equals(account.getCustomer().getCustomerId())) {
            model.addAttribute("error", "Không có quyền cập nhật thông tin này.");
            model.addAttribute("customer", customerService.findByCustomerId(customerId));
            return "customer/profile";
        }

        // Validate email
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (email == null || !Pattern.matches(emailRegex, email)) {
            model.addAttribute("error", "Email không hợp lệ.");
            model.addAttribute("customer", customerService.findByCustomerId(customerId));
            return "customer/profile";
        }

        // Validate mobile
        String mobileRegex = "^[0-9]{10}$";
        if (mobile != null && !mobile.isEmpty() && !Pattern.matches(mobileRegex, mobile)) {
            model.addAttribute("error", "Số điện thoại phải có 10 chữ số.");
            model.addAttribute("customer", customerService.findByCustomerId(customerId));
            return "customer/profile";
        }

        // Validate identityCard và licenceNumber
        if (identityCard != null && identityCard.length() != 12) {
            model.addAttribute("error", "CMND/CCCD phải 12 ký tự.");
            model.addAttribute("customer", customerService.findByCustomerId(customerId));
            return "customer/profile";
        }
        if (licenceNumber != null && licenceNumber.length() != 12) {
            model.addAttribute("error", "Số GPLX phải 12 ký tự.");
            model.addAttribute("customer", customerService.findByCustomerId(customerId));
            return "customer/profile";
        }

        // Validate ngày
        LocalDate birthday = birthdayStr != null && !birthdayStr.isEmpty() ? LocalDate.parse(birthdayStr) : null;
        LocalDate licenceDate = licenceDateStr != null && !licenceDateStr.isEmpty() ? LocalDate.parse(licenceDateStr) : null;
        LocalDate now = LocalDate.now();
        if (birthday != null && birthday.isAfter(now)) {
            model.addAttribute("error", "Ngày sinh không được trong tương lai.");
            model.addAttribute("customer", customerService.findByCustomerId(customerId));
            return "customer/profile";
        }
        if (licenceDate != null && licenceDate.isAfter(now)) {
            model.addAttribute("error", "Ngày cấp GPLX không được trong tương lai.");
            model.addAttribute("customer", customerService.findByCustomerId(customerId));
            return "customer/profile";
        }

        // Tạo đối tượng Customer
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setCustomerName(customerName);
        customer.setMobile(mobile);
        customer.setBirthday(birthday);
        customer.setIdentityCard(identityCard);
        customer.setLicenceNumber(licenceNumber);
        customer.setLicenceDate(licenceDate);
        customer.setEmail(email);
        customer.setPassword(password); // Lưu ý: Nên mã hóa password trước khi lưu

        // Cập nhật và xử lý lỗi
        try {
            customerService.save(customer);
            model.addAttribute("success", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            model.addAttribute("error", "Cập nhật thất bại: " + e.getMessage());
        }

        // Load lại customer để hiển thị
        model.addAttribute("customer", customerService.findByCustomerId(customerId));
        return "customer/profile";
    }
    @GetMapping("deposit")
    public String deposit(Model model, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if(account==null) {
            model.addAttribute("error", "Session không lưu account");
            return "/customer/deposit";
        }

        Customer customer= customerService.findByAccountId(account.getAccountId());
        if(customer==null) {
            model.addAttribute("error", "Không lấy được customer");
            return "/customer/deposit";
        }
        model.addAttribute("balance", customer.getBalance());
        return "/customer/deposit";
    }

    @PostMapping("deposit")
    public String depositPost(HttpServletRequest request, Model model, HttpSession session) {
        String sDepositAmount = request.getParameter("depositAmount");
        model.addAttribute("depositAmount", sDepositAmount);
        try{
            Double depositAmount = Double.parseDouble(sDepositAmount);
            Account account = (Account) session.getAttribute("account");
            Customer customer= customerService.findByAccountId(account.getAccountId());
            customer.setBalance(customer.getBalance()+depositAmount);
            customerService.save(customer);
            model.addAttribute("balance", customer.getBalance());
            model.addAttribute("success", "Nạp tiền thành công");
        }catch(Exception e){
            model.addAttribute("error","Vui lòng nhập số tiền hợp lệ!!!");
            return "/customer/deposit";
        }
        return "/customer/deposit";
    }
}
