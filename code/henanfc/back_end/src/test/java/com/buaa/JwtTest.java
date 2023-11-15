package com.buaa;

import com.buaa.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    @Test
    public void testGen() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "Ivo10");
        String token = JwtUtils.genToken(claims);
        System.out.println(token);
    }

    @Test
    public void testParse() {
        Claims claims = JwtUtils.parseToken("eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZXhwIjoxNzAwMTUxMDQ0LCJ1c2VybmFtZSI6Ikl2bzEwIn0.EacSVOXZZs3raNh7iESAJoZj2dZ_E9o9oRz5rj4Naug");
        System.out.println(claims);
    }
}
