package com.justinkuchmy.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/api/index/brands", "/api/index/categories", "/api/index/products", "/api/index/stocks", "/api/index/customers", "/api/index/orders", "/api/index/orderitems", "/api/index/staff", "/api/index/store" })
public class ViewController {
 public String index() {
 return "forward:/index.html";
 }
}
