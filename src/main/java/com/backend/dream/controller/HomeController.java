package com.backend.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String index(){
        return "/user/home/index";
    }
    @RequestMapping("/about")
    public String about(){
        return "/user/home/about";
    }

    @RequestMapping("/products")
    public String product() { return "user/product/products-list"; }
}
