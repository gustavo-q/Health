package com.gustavo.service;

import com.gustavo.pojo.User;

public interface UserService {
    public User findByUsername(String username);
}
