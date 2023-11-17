package com.buaa.interceptors;

import com.buaa.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        try {
            JwtUtils.parseToken(token);
            // 放行
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            // 不放行
            return false;
        }
    }
}
