package com.chhai.dataanalyticrestfulapi.model.response;

import com.chhai.dataanalyticrestfulapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private User user;
}
