package com.chhai.dataanalyticrestfulapi.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    @NotBlank(message = "Username is required !")
    private String username;
    @NotBlank(message = "Gender is also required !")
    private String gender;
    @NotBlank(message = "Address is required !")
    private String address;
}
