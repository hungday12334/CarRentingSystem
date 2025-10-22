package hsf302.he191662.hungnt.carrentingsystem.controller;

import hsf302.he191662.hungnt.carrentingsystem.entity.Account;
import hsf302.he191662.hungnt.carrentingsystem.entity.Customer;
import hsf302.he191662.hungnt.carrentingsystem.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String error = request.getParameter("error");
        if(error!=null){
            model.addAttribute("error","Bạn không có quyền truy cập vào trang này.");
        }
        return "login/login";
    }

    @PostMapping("/login")
    public String loginSubmit(HttpServletRequest request, Model model, HttpSession session){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        if(username==null||username.trim().isEmpty()){
            model.addAttribute("error","Không được để trống tên đăng nhập!!!");
            return "login/login";
        }else{
            if(!accountService.existsByUsername(username)){
                model.addAttribute("error","Tên đăng nhập không tồn tại!!!");
                return "login/login";
            }
        }
        if(password==null||password.trim().isEmpty()){
            model.addAttribute("error","Mật khẩu không được để trống!!!");
            return "login/login";
        }else{
            if(password.trim().length()<6){
                model.addAttribute("error","Password phải dài hơn 6 kí tự!!!");
                return "login/login";
            }
        }
        if(accountService.existsByUsername(username)){
            if(!accountService.existsByUsernameAndPassword(username, password)){
                model.addAttribute("error","Tên đăng nhập và mật khẩu không khớp!!!");
                return "login/login";
            }else{
                Account account = accountService.findByUsername(username);
                session.setAttribute("account", account);
                if("admin".equals(account.getRole())){
                    return "redirect:/admin";
                }else{
                    return "redirect:/customer";
                }

            }
        }else{
            model.addAttribute("error", "Tên đăng nhập không tồn tại!!!");
            return "login/login";
        }
    }

    @GetMapping("register")
    public String register() {
        return "login/register";
    }
    @PostMapping("register")
    public String registerSubmit(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String name= request.getParameter("name");
        String username = request.getParameter("username");
        String email= request.getParameter("email");
        String password= request.getParameter("password");
        String password2= request.getParameter("password2");
        model.addAttribute("name", name);
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        model.addAttribute("password2", password2);
        if(name==null||name.trim().isEmpty()){
            model.addAttribute("error", "Không được để trống Họ và tên!");
            return "login/register";
        }
        if(username==null||username.trim().isEmpty()){
            model.addAttribute("error","Không được để trống tên đăng nhập!!!");
            return "login/register";
        }else{
            if(accountService.existsByUsername(username)){
                model.addAttribute("error","Tên đăng nhập đã tồn tại!!!");
                return "login/register";
            }
        }
        if(email==null||email.trim().isEmpty()){
            model.addAttribute("error","Không được để trống Email!!!");
            return "login/register";
        }else{
            if(accountService.existsByEmail(email)){
                model.addAttribute("error","Email đã tồn tại!!!");
                return "login/register";
            }
        }
        if(password==null||password.trim().isEmpty()){
            model.addAttribute("error","Mật khẩu không được để trống!!!");
            return "login/register";
        }else{
            if(password.trim().length()<6){
                model.addAttribute("error","Password phải dài hơn 6 kí tự!!!");
                return "login/register";
            }
        }
        if(password2==null||password2.trim().isEmpty()){
            model.addAttribute("error","Mật khẩu nhâp lại không được để trống!!!");
            return "login/register";
        }else{
            if(!password.equals(password2)){
                model.addAttribute("error","Mật khẩu nhâp lại không khớp!!!");
                return "login/register";
            }
        }
        Account account = new Account(username.trim(), password, email.trim(), "customer", true);
        Customer customer = new Customer(name, account.getEmail(), account.getPassword(),0.0);
        account.setCustomer(customer);
        customer.setAccount(account);
        accountService.save(account);
        redirectAttributes.addFlashAttribute("success", "Đăng ký tài khoản thành công.");
        return "redirect:/auth/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, @RequestParam(value= "logout", required = false) String logout,  Model model, RedirectAttributes redirectAttributes, HttpServletRequest request){
        if(session!=null){
            session.invalidate();
        }
        String unauthorized = request.getParameter("unauthorized");
        if(unauthorized!=null){
            model.addAttribute("error", "Bạn không có quyền truy cập vào trang này.");
        }
        if(logout!=null){
            model.addAttribute("success", logout);
        }
        return "login/login";
    }
}
