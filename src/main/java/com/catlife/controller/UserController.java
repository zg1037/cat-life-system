package com.catlife.controller;

import com.catlife.common.result.Result;
import com.catlife.dto.LoginDTO;
import com.catlife.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid LoginDTO loginDTO){

        return userService.login(loginDTO);

    }

}