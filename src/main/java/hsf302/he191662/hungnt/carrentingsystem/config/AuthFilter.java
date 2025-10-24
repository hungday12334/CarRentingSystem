//package hsf302.he191662.hungnt.carrentingsystem.config;
//
//import hsf302.he191662.hungnt.carrentingsystem.entity.Account;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.hibernate.annotations.Filters;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//
//@WebFilter(urlPatterns = "/*")
//public class AuthFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        //Lay uri
//        String uri = request.getRequestURI();
//        //https/localhost:8080/carrentingsystem/customer/list
//        //request.getRequestURI() chỉ /carrentingsystem/customer/list
//        if (uri.startsWith("/auth") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/images")) {
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
//        //Lay session
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            response.sendRedirect( "/auth/login");
//            return;
//        } else {
//            Account account = (Account) session.getAttribute("account");
//            if (account == null) {
//                response.sendRedirect( "/auth/logout");
//                return;
//            } else {
//                boolean isCustomerArea = uri.startsWith("/customer") && "customer".equals(account.getRole());
//                boolean isAdminArea = uri.startsWith("/admin") && "admin".equals(account.getRole());
//                if (isCustomerArea || isAdminArea) {
//                    filterChain.doFilter(servletRequest, servletResponse);
//                    return;
//                } else {
//                    session.invalidate();
//                    response.sendRedirect( "/auth/login");
//                    return;
//                }
//            }
//        }
//    }
//}
