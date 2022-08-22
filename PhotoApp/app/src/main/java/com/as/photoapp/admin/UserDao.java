package com.as.photoapp.admin;

import com.as.photoapp.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
}
