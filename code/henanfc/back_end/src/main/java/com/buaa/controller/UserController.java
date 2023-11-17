package com.buaa.controller;

import com.buaa.pojo.Result;
import com.buaa.pojo.User;
import com.buaa.service.UserService;
import com.buaa.utils.JwtUtils;
import com.buaa.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
        User resultUser = userService.findByUsername(username);
        if (resultUser == null) {
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户已存在！");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "\\S{5,16}$") String username,
                                @Pattern(regexp = "\\S{5,16}$") String password) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return Result.error("用户不存在!");
        }
        if (Md5Util.getMD5String(password).equals(user.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            String token = JwtUtils.genToken(claims);
            return Result.success(token);
        } else {
            return Result.error("密码错误!");
        }
    }
}
