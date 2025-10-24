package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.entity.Car;
import hsf302.he191662.hungnt.carrentingsystem.entity.CarProducer;
import hsf302.he191662.hungnt.carrentingsystem.service.CarProducerService;
import hsf302.he191662.hungnt.carrentingsystem.service.CarProducerServiceImpl;
import hsf302.he191662.hungnt.carrentingsystem.service.CarService;
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
@RequestMapping("/admin/cars")
public class AdminCarManagement {
    @Autowired
    private CarService carService;

    @Autowired
    private CarProducerService carProducerService;

    @RequestMapping
    public String index(Model model, HttpServletRequest request) {
        String carName = request.getParameter("carNameFilter");
        String carId = request.getParameter("carIdFilter");
        model.addAttribute("carNameFilter", carName);
        model.addAttribute("carIdFilter", carId);
        List<Car> cars = carService.findByCarNameAndCarId(carId, carName);
        model.addAttribute("cars", cars);
        return "admin/car-management";
    }

    @GetMapping("/create")
    public String createCar(Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        List<CarProducer> producers = carProducerService.findAll();
        model.addAttribute("producers", producers);
        return "admin/car-create";
    }

    @PostMapping("create")
    public String createCarP(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        Validation valid = new Validation();

        String carName = request.getParameter("carName");
        String imageUrl = request.getParameter("imageUrl");
        String carModelYearStr = request.getParameter("carModelYear");
        String color = request.getParameter("color");
        String capacityStr = request.getParameter("capacity");
        String description = request.getParameter("description");
        String importDateStr = request.getParameter("importDate");
        String producerIdStr = request.getParameter("producerId");
        String rentPrice = request.getParameter("rentPrice");
        String status = request.getParameter("status");
        String address = request.getParameter("address");

        model.addAttribute("carName", carName);
        model.addAttribute("imageUrl", imageUrl);
        model.addAttribute("carModelYear", carModelYearStr);
        model.addAttribute("color", color);
        model.addAttribute("capacity", capacityStr);
        model.addAttribute("description", description);
        model.addAttribute("importDate", importDateStr);
        model.addAttribute("producerId", producerIdStr);
        model.addAttribute("rentPrice", rentPrice);
        model.addAttribute("status", status);
        model.addAttribute("address", address);
        List<CarProducer> producers = carProducerService.findAll();
        model.addAttribute("producers", producers);
        if (carName == null || carName.trim().isEmpty()) {
            model.addAttribute("error", "Tên không hợp lệ.");
            return "admin/car-create";
        } else carName = carName.trim();

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            model.addAttribute("error", "Link ảnh không hợp lệ.");
            return "admin/car-create";
        } else imageUrl = imageUrl.trim();

        if (carModelYearStr == null || carModelYearStr.trim().isEmpty()) {
            model.addAttribute("error", "Năm sản xuất không được để trống.");
            return "admin/car-create";
        }

        if (color == null || color.trim().isEmpty()) {
            model.addAttribute("error", "Màu sắc không được để trống.");
            return "admin/car-create";
        } else color = color.trim();

        if (capacityStr == null || capacityStr.trim().isEmpty()) {
            model.addAttribute("error", "Sức chứa không được để trống.");
            return "admin/car-create";
        }

        if (description == null || description.trim().isEmpty()) {
            model.addAttribute("error", "Mô tả không được để trống.");
            return "admin/car-create";
        } else description = description.trim();

        if (importDateStr == null || importDateStr.trim().isEmpty()) {
            model.addAttribute("error", "Ngày nhập không được để trống.");
            return "admin/car-create";
        }

        if (producerIdStr == null || producerIdStr.trim().isEmpty()) {
            model.addAttribute("error", "Nhà sản xuất không được để trống.");
            return "admin/car-create";
        }

        if (rentPrice == null || rentPrice.trim().isEmpty()) {
            model.addAttribute("error", "Giá không được để trống.");
            return "admin/car-create";
        }

        if (status == null || status.trim().isEmpty()) {
            model.addAttribute("error", "Trạng thái không được để trống.");
            return "admin/car-create";
        } else status = status.trim();

        if (address == null || address.trim().isEmpty()) {
            model.addAttribute("error", "Địa chỉ không được để trống.");
            return "admin/car-create";
        } else address = address.trim();

        // Parse các giá trị số và ngày
        int carModelYear;
        int capacity;
        LocalDate importDate;
        Long producerId;
        double price;

        try {
            carModelYear = Integer.parseInt(carModelYearStr.trim());
            if (carModelYear <= 1900) {
                model.addAttribute("error", "Năm sản xuất không hợp lệ.");
                return "admin/car-create";
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Năm sản xuất phải là số nguyên.");
            return "admin/car-create";
        }

        try {
            capacity = Integer.parseInt(capacityStr.trim());
            if (capacity <= 0) {
                model.addAttribute("error", "Sức chứa phải là số nguyên dương.");
                return "admin/car-create";
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Sức chứa phải là số nguyên dương.");
            return "admin/car-create";
        }

        try {
            importDate = LocalDate.parse(importDateStr.trim());
        } catch (Exception e) {
            model.addAttribute("error", "Ngày nhập không hợp lệ. Định dạng yyyy-MM-dd.");
            return "admin/car-create";
        }

        try {
            producerId = Long.parseLong(producerIdStr.trim());
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Nhà sản xuất không hợp lệ.");
            return "admin/car-create";
        }

        try {
            price = Double.parseDouble(rentPrice.trim());
            if (price <= 0) {
                model.addAttribute("error", "Giá phải là số dương.");
                return "admin/car-create";
            }
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Giá phải là số hợp lệ.");
            return "admin/car-create";
        }

        // Lấy đối tượng CarProducer từ DB
        CarProducer producer = carProducerService.findByProducerId(producerId);
        if (producer == null) {
            model.addAttribute("error", "Nhà sản xuất không tồn tại.");
            return "admin/car-create";
        }

        // Tạo Car mới
        Car car = new Car();
        car.setCarName(carName);
        car.setImageUrl(imageUrl);
        car.setCarModelYear(carModelYear);
        car.setColor(color);
        car.setCapacity(capacity);
        car.setDescription(description);
        car.setImportDate(importDate);
        car.setProducer(producer);
        car.setRentPrice(price);
        car.setStatus(status);
        car.setHiden(false);
        car.setAddress(address);
        car.setRented(0);
        // Lưu vào DB
        carService.save(car);

        redirectAttributes.addFlashAttribute("success", "Tạo xe thành công!");
        return "redirect:/admin/cars";
    }

}
