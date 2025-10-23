package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.dto.ReviewDTO;
import hsf302.he191662.hungnt.carrentingsystem.entity.Account;
import hsf302.he191662.hungnt.carrentingsystem.entity.Car;
import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import hsf302.he191662.hungnt.carrentingsystem.service.*;
import hsf302.he191662.hungnt.carrentingsystem.util.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CarService carService;
    @Autowired
    private CarProducerService carProducerService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CarRentalService carRentalService;



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
    public String rentalHistory(Model model, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        Account account = (Account) session.getAttribute("account");
        if(account==null) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi cần đăng nhập lại.");
            return "redirect:/auth/logout";
        }
        Customer customer= customerService.findByAccountId(account.getAccountId());
        if(customer==null) {
            redirectAttributes.addFlashAttribute("error", "Có lỗi cần đăng nhập lại.");
            return "redirect:/auth/logout";
        }
        List<CarRental> listRental= carRentalService.findByUserId(customer.getCustomerId());
        model.addAttribute("listRental", listRental);
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
        Validation valid = new Validation();
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

        Customer customer = customerService.findByCustomerId(customerId);
        // Validation
        if (customerName == null || customerName.trim().isEmpty()) {
            model.addAttribute("error", "Họ và tên không được để trống.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }


        // Kiểm tra customerId khớp với tài khoản
        if (!customerId.equals(account.getCustomer().getCustomerId())) {
            model.addAttribute("error", "Không có quyền cập nhật thông tin này.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }

        // Validate email
        if (!valid.isValidEmail(email)) {
            model.addAttribute("error", "Email không hợp lệ.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }

        // Validate mobile
        if (!valid.isValidVietnamPhone(mobile)) {
            model.addAttribute("error", "Số điện thoại phải có 10 chữ số.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }

        // Validate identityCard và licenceNumber
        try{
            if (identityCard != null && identityCard.length() != 12) {
                model.addAttribute("error", "CMND/CCCD phải 12 ký tự.");
                model.addAttribute("customer", customer);
                return "customer/profile";
            }
        }catch(Exception e){
            model.addAttribute("error", "CMND/CCCD đã tồn tại.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }

        try{
            if (licenceNumber != null && licenceNumber.length() != 12) {
                model.addAttribute("error", "Số GPLX phải 12 ký tự.");
                model.addAttribute("customer", customer);
                return "customer/profile";
            }
        }catch(Exception e){
            model.addAttribute("error", "GPLX đã tồn tại.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }

        // Validate ngày
        LocalDate birthday = birthdayStr != null && !birthdayStr.isEmpty() ? LocalDate.parse(birthdayStr) : null;
        LocalDate licenceDate = licenceDateStr != null && !licenceDateStr.isEmpty() ? LocalDate.parse(licenceDateStr) : null;
        LocalDate now = LocalDate.now();
        if (birthday != null && birthday.isAfter(now)) {
            model.addAttribute("error", "Ngày sinh không được trong tương lai.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }
        if (licenceDate != null && licenceDate.isAfter(now)) {
            model.addAttribute("error", "Ngày cấp GPLX không được trong tương lai.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }

        if(password==null||password.trim().isEmpty()||password.length()<6){
            model.addAttribute("error", "Mật khẩu không được để trống và phải nhất 6 kí tự.");
            model.addAttribute("customer", customer);
            return "customer/profile";
        }

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
        model.addAttribute("customer", customer);
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

    @GetMapping("car-detail")
    public String carDetail(Model model, HttpServletRequest request) {
        String carId = request.getParameter("carId");
        try{
            Long id = Long.parseLong(carId);
            Car car = carService.findById(id);
            if(car==null||car.getCarId()==null){
                model.addAttribute("error", "Sản phẩm không tồn tại.");
                return "/customer/cars";
            }
            List<ReviewDTO> listReview = reviewService.findByCarId(id);
            model.addAttribute("listReview", listReview);
            model.addAttribute("car", car);
            return "/customer/car-detail";
        }catch(Exception e){
            model.addAttribute("error", "Sản phẩm không tồn tại.");
            return "/customer/cars";
        }
    }

    @PostMapping("rent")
    public String rentCar(Model model, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        Long customerId = ((Account)session.getAttribute("account")).getAccountId();
        String sCarId= request.getParameter("carId");
        try{
            Long carId = Long.parseLong(sCarId);
            Customer customer= customerService.findByCustomerId(customerId);
            Car car= carService.findById(carId);
            String sRentDate= request.getParameter("startDate");
            String sReturnDate= request.getParameter("endDate");
            if(sRentDate==null||sRentDate.trim().isEmpty()||sReturnDate==null||sReturnDate.trim().isEmpty()){
                redirectAttributes.addFlashAttribute("error", "Vui lòng nhập ngày hợp lệ.");
                return "redirect:/customer/car-detail?carId="+carId;
            }
            LocalDate rentDate = LocalDate.parse(sRentDate);
            LocalDate returnDate = LocalDate.parse(sReturnDate);
            long dayCount =ChronoUnit.DAYS.between(rentDate, returnDate)+1;
            Integer days = Integer.parseInt(dayCount+"");
            Double rentAmount= days*car.getRentPrice();
            CarRental carRental = new CarRental(customer, car, rentDate, returnDate, rentAmount, days, "pending", LocalDate.now(), LocalDate.now());
            Double balance = customer.getBalance()-rentAmount;
            if(balance<0){
                redirectAttributes.addFlashAttribute("error","Bạn không đủ tiền để thanh toán.");
                return "redirect:/customer/car-detail?carId="+carId;
            }
            if(customer.getIdentityCard()==null||customer.getLicenceNumber()==null||customer.getLicenceDate()==null||customer.getIdentityCard().trim().isEmpty()||customer.getLicenceNumber().trim().isEmpty()){
                redirectAttributes.addFlashAttribute("error","Bạn chưa cập nhật thông tin liên quan đến CCCD và GPLX nên không thuê xe được");
                return "redirect:/customer/car-detail?carId="+carId;
            }
            carRentalService.save(carRental);
            customer.setBalance(balance);
            customerService.save(customer);
            return "redirect:/customer/rental-history";
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error","Có lỗi xảy ra trong qus trình xử lý. Vui long đăng nhập lại.");
            return "redirect:/auth/logout";
        }
    }
    @PostMapping("/rental-history/cancel")
    public String cancelRental(Model model, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes) {
        String sCarRentalId = request.getParameter("rentalId");
        if(sCarRentalId==null||sCarRentalId.trim().isEmpty()){
            model.addAttribute("error","Đơn hàng không tồn tại.");
            return "/customer/rental-history";
        }
        try{
            Long rentalId = Long.parseLong(sCarRentalId);
            CarRental carRental = carRentalService.findById(rentalId);
            if(carRental==null){
                model.addAttribute("error","Đơn hàng không tồn tại.");
                return "/customer/rental-history";
            }
            LocalDate today = LocalDate.now();
            LocalDate  updateDate = carRental.getUpdateAt();
            Customer customer = carRental.getCustomer();

            if("pending".equals(carRental.getStatus())){
                //Neu pending thi cho huy thoai mai
                customer.setBalance(customer.getBalance()+carRental.getRentPrice());
                customerService.save(customer);
                //huy
                carRental.setStatus("cancelled");
                carRentalService.save(carRental);
                redirectAttributes.addFlashAttribute("success","Bạn đã hủy thành công  đơn đang ở trạng thái 'Pending' nên được hoàn 100% tiền vào ví.");
                return "redirect:/customer/rental-history";
            }else if("confirmed".equals(carRental.getStatus())){
                //Neu da duoc chu xe confirm roi thi phai check
                if(today.isAfter(updateDate)){
                    //Neu sau do moi huy thi nhan lai 60%
                    customer.setBalance(customer.getBalance()+carRental.getRentPrice()*0.6);
                    customerService.save(customer);
                    //huy
                    carRental.setStatus("cancelled");
                    carRentalService.save(carRental);
                    redirectAttributes.addFlashAttribute("success","Bạn đã hủy thành công  đơn đang ở trạng thái 'Confirmed' nhưng không cùng ngày 'Confirmed' nên chỉ được hoàn 60% tổng số tiền.");
                    return "redirect:/customer/rental-history";
                }else{
                    //Neu huy trong ngay confirm duoc hoan tien 100%
                    customer.setBalance(customer.getBalance()+carRental.getRentPrice());
                    customerService.save(customer);
                    carRental.setStatus("cancelled");
                    carRentalService.save(carRental);
                    redirectAttributes.addFlashAttribute("success","Bạn đã hủy thành công  đơn cùng ngày chủ xe 'Confirmed' nên được hoàn 100% tiền vào ví.");
                    return "redirect:/customer/rental-history";
                }
            }else{
                //Đang trong shipping, shipped, completed, cancelled
                redirectAttributes.addFlashAttribute("error","Bạn không thể hủy đơn này.");
                return "redirect:/customer/rental-history";
            }
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", "Có lỗi trong quá trình xử lý vui lòng đăng nhập lại.");
            return "redirect:/auth/logout";
        }
    }
}
