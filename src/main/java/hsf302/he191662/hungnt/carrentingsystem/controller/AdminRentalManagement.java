package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.entity.CarRental;
import hsf302.he191662.hungnt.carrentingsystem.service.CarRentalService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/rentals")
public class AdminRentalManagement {

    @Autowired
    private CarRentalService carRentalService;
    @GetMapping(value = {"", "/" })
    public String index(Model model, HttpServletRequest request) {
        List<CarRental> carRentals = carRentalService.findAll();
        model.addAttribute("rentals", carRentals);
        return "admin/rental-managment";
    }
    @PostMapping("/update")
    public String updateRentals(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        String sRentalId = request.getParameter("rentalId");
        Long rentalId = null;
        try{
            rentalId = Long.parseLong(sRentalId);
            CarRental carRental = carRentalService.findById(rentalId);
            if(carRental == null){
                redirectAttributes.addFlashAttribute("error","Đơn này không tồn tại.");
                return "redirect:/admin/rentals";
            }
            if("pending".equalsIgnoreCase(carRental.getStatus())){
                carRental.setStatus("confirmed");
            }else if("confirmed".equalsIgnoreCase(carRental.getStatus())){
                carRental.setStatus("shipping");
            }else if("shipping".equalsIgnoreCase(carRental.getStatus())){
                carRental.setStatus("shipped");
            }else if("shipped".equalsIgnoreCase(carRental.getStatus())){
                carRental.setStatus("completed");
            }else{
                redirectAttributes.addFlashAttribute("error","Không thể thay đổi trạng thái của những đơn này.");
                return "redirect:/admin/rentals";
            }
            carRentalService.save(carRental);
            redirectAttributes.addFlashAttribute("success","Cập nhật trạng thái mới thành công");
            return "redirect:/admin/rentals";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error","Đơn này không tồn tại.");
            return "redirect:/admin/rentals";
        }
    }
}
