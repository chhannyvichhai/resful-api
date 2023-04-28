package com.chhai.dataanalyticrestfulapi.controller;


import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;
import com.chhai.dataanalyticrestfulapi.service.UserService;
import com.chhai.dataanalyticrestfulapi.utils.Response;
import org.mapstruct.control.MappingControl;
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
    public Response<List<User>> getAllUsers(){
        try {
            List<User> user = userService.allUsers();
            return Response.<List<User>>ok().setPayload(user).setMessage("Successfully retrieved all users!");
        }catch (Exception ex){
            return Response.<List<User>>exception().setMessage("Fail to retrieved users!");
        }

    }


    @GetMapping("/{id}")
    public Response<User>findUserByID(@PathVariable int id){
       try {
           if (isUserExists(id)){
               User user = userService.findUserByID(id);
               return Response.<User>ok().setPayload(user).setMessage("User has found");
           }else {
             return userNotFound(id);
           }
       }catch (Exception ex){
           return Response.<User>exception().setMessage("User not found!");
       }
    }


    @PostMapping("/newuser")
    public Response<User> creatingUser(@RequestBody User user){
        try {
            userService.createNewUser(user);
            return Response.<User>createSuccess().setPayload(user).setMessage("Create New User Successfully!");

        }catch(Exception exception){
            exception.printStackTrace();
            return Response.<User>exception().setMessage("Cannot Create User");
        }
    }

    private boolean isUserExists(int id){
        User user = userService.findUserByID(id);
        return user != null;
    }

    private Response<User> userNotFound(int id){
        return Response.<User>notFound().setMessage("Cannot find user with id: "+id).setSuccess(false).setStatus(Response.Status.NOT_FOUND);
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

    @PutMapping("/{id}")
    public Response<User> updateUser(@PathVariable int id, @RequestBody User user){
        try {
            if (isUserExists(id)){
                user.setUserId(id);
                userService.updateUser(user, id);
                return Response.<User>updateSuccess().setPayload(user).setMessage("Update Successfully !");
            }else {
               return userNotFound(id);
            }
        }catch (Exception ex){
            return Response.<User>exception().setMessage("Update Not Success !").setSuccess(false);
        }
    }

    @DeleteMapping("/{id}")
    public Response<User> removeUser(@PathVariable int id){
        try {
           if (isUserExists(id)){
               userService.removeUser(id);
               return Response.<User>deleteSuccess().setMessage("Delete Successfully!");
           }else {
               return userNotFound(id);
           }
        }catch (Exception ex){
            return Response.<User>exception().setMessage("Delete Not Success...!");
        }
    }

}

