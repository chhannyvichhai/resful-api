package com.chhai.dataanalyticrestfulapi.controller;


import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserResController {
    // inject UserService
    private final UserService userService;
    UserResController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/allusers")
    List<User> getAllUsers(){
        return userService.allUsers();
    }

}
