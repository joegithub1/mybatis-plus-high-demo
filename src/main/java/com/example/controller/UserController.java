package com.example.controller;


import com.example.beans.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author HuangJian
 * @since 2020-07-09
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserMapper userMapper;

    @RequestMapping("/findAll")
    @ResponseBody
    public int findAll(){
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
        return 1;
    }
}

