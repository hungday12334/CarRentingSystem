package hsf302.he191662.hungnt.carrentingsystem.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.hibernate.annotations.Filters;

import java.io.IOException;


@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }
}
