package com.example.helloword.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError () {
        return  "Opps! Đã có lỗi xảy ra" ;
    }

    public String getErrorPath () {
        // có thể xử lý trả về 1 trang html thông báo đã có lỗi
        return  "/error" ;
    }
}
