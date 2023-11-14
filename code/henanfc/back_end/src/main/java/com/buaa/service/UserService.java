package com.buaa.service;

import com.buaa.pojo.User;

public interface UserService {
    User findByUsername(String username);

    void register(String username, String password);
}
