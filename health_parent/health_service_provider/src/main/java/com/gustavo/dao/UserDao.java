package com.gustavo.dao;

import com.gustavo.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
