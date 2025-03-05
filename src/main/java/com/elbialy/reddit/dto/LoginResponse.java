package com.elbialy.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    String status;
    String JWTToken;
}
