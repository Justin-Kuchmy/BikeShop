package com.justinkuchmy.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping({ "/api/index/brands", "/api/index/categories", "/api/index/product/all", "/api/index/stocks", "/api/index/customer/all", "/api/index/order/all", "/api/index/orderitem/all", "/api/index/staff", "/api/index/store" })
public class ViewController implements WebMvcConfigurer  {

 @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Map the root URL to index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }

 
}
