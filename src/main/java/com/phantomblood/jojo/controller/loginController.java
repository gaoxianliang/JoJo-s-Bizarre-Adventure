package com.phantomblood.jojo.controller;


import com.phantomblood.jojo.service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.phantomblood.jojo.utils.JsonUtils.toJson;

@Controller
public class loginController {

    @Autowired
    private testService testService;

    @RequestMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("/user")
    @ResponseBody
    public String user(@RequestBody Map map){
        return toJson(testService.findUserById(map));
    }
}
