package hsf302.he191662.hungnt.carrentingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reports")
public class AdminReport {
    @RequestMapping
    public String index() {
        return "admin/report";
    }
}
