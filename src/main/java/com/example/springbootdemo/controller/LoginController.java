package com.example.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录控制器
 *
 * @author Ding RD
 * @date 2019/6/19
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    @ResponseBody
    public String login() {
        System.out.println("login");
        return "success";
    }
}
