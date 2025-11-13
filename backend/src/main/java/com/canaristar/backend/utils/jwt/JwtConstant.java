package com.canaristar.backend.utils.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class JwtConstant {

    @Value("${jwt.secret}")
    private String secretKey;

    public static final String JWT_HEADER = "Authorization";
}
