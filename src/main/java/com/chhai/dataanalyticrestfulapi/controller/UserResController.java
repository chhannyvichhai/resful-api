package com.chhai.dataanalyticrestfulapi.controller;


import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.UserAccount;
import com.chhai.dataanalyticrestfulapi.model.request.UserRequest;
import com.chhai.dataanalyticrestfulapi.service.UserService;
import com.chhai.dataanalyticrestfulapi.utils.Response;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import org.apache.coyote.Request;
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
    public Response<PageInfo<User>> getAllUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size, @RequestParam(defaultValue = "") String username){
        try {
            PageInfo<User> user = userService.allUsers(page, size, username);
            return Response.<PageInfo<User>>ok().setPayload(user).setMessage("Successfully retrieved all users!");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return Response.<PageInfo<User>>exception().setMessage("Fail to retrieved the users! Exception occurred");
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
    public Response<User> creatingUser(@Valid @RequestBody UserRequest user){
        try {
           int userID = userService.createNewUser(user);
            if (userID > 0){
                User response = new User().setUsername(user.getUsername()).setAddress(user.getAddress()).setGender(user.getGender()).setUserId(userID);
                return Response.<User>createSuccess().setPayload(response).setMessage("Create New User Successfully!").setSuccess(true);
            }else {
                return Response.<User>badRequest().setMessage("Bad Request");
            }
        }catch(Exception exception){
            exception.printStackTrace();
            return Response.<User>exception().setMessage("Cannot Create User").setSuccess(false);
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

