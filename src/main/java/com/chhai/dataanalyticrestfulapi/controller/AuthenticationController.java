package com.chhai.dataanalyticrestfulapi.controller;

import com.chhai.dataanalyticrestfulapi.model.User;
import com.chhai.dataanalyticrestfulapi.model.request.LoginRequest;
import com.chhai.dataanalyticrestfulapi.model.response.LoginResponse;
import com.chhai.dataanalyticrestfulapi.service.UserService;
import com.chhai.dataanalyticrestfulapi.utils.Response;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    UserService userService;
    private final AuthenticationProvider authenticationProvider;
    public AuthenticationController(UserService userService, AuthenticationProvider authenticationProvider){
        this.userService = userService;
        this.authenticationProvider = authenticationProvider;
    }



    @PostMapping("/register")
    public Response<?> register(){
        return null;
    }

    @GetMapping("/login")
    public Response<LoginResponse> login(@RequestBody LoginRequest request){
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername()
                ,request.getPassword()
        );
        authentication = authenticationProvider.authenticate(authentication);
        // raise exception when username or password in invalid

        String tokenFormat = authentication.getName() + ":" + authentication.getCredentials();
        String tokenFormatEncoded = Base64.getEncoder().encodeToString(tokenFormat.getBytes());
        String token = "Basic " + tokenFormatEncoded;

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        User user = userService.findUserByName(request.getUsername()).stream().findFirst().orElse(null);
        response.setUser(user);

        return Response.<LoginResponse>ok().setPayload(response).setMessage("Successfully Login Please user your token to authentication");
    }

    @PatchMapping("/resetPassword")
    public Response<?> resetPassword(){
        return null;
    }

}
