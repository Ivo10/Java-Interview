package com.buaa.controller;

import com.buaa.pojo.Result;
import com.buaa.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    public Result<String> list(@RequestHeader(name = "Authorization") String token, HttpServletResponse response) {
        // 验证token
        try {
            Claims claims = JwtUtils.parseToken(token);
            return Result.success("所有文章数据");
        } catch (Exception e) {
            response.setStatus(401);
            return Result.error("未登录");
        }
    }
}
