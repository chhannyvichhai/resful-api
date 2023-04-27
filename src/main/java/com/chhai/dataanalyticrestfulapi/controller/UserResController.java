package com.chhai.dataanalyticrestfulapi.controller;


import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;
import com.chhai.dataanalyticrestfulapi.service.UserService;
import com.chhai.dataanalyticrestfulapi.utils.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
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


    @GetMapping("/{id}")
    public User findUserByID(@PathVariable int id){
        return userService.findUserByID(id);
    }


    @PostMapping("/newuser")
    public String creatingUser(@RequestBody User user){
        try {
            int affectRow = userService.createNewUser(user);
            if (affectRow>0) {
                System.out.println("affected row : "+affectRow);
                return "Create user successfully...!";}
            else
                return "Cannot create a new user!";
        }catch(Exception exception){
            exception.printStackTrace();
            return exception.getMessage();
        }
    }

    @GetMapping("/user-accounts")
    public Response<List<UserAccount>> getAllUserAccounts(){
        try {
            List<UserAccount> data = userService.getAllUserAccounts();
            return Response.<List<UserAccount>>ok().setPayload(data).setMessage("Successfully retrieved all user accounts !");


        }catch (Exception ex){
            return Response.<List<UserAccount>>exception().setMessage("Exception occurs ! Failed to retrieved all user accounts!").setSuccess(false);
        }
    }

}

