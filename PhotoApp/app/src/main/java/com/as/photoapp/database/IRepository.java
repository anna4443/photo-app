package com.as.photoapp.database;

import com.as.photoapp.model.Image;
import com.as.photoapp.model.User;

import java.util.List;

public interface IRepository {

    List<User> getUsers();
    void insertUser(User user);
    User findByUsernameAndPassword(String username, String password);
    User getUserById(int id);
    User getUserByUsername(String username);

    List<Image> getImages();
    void insertImage(Image image);
}
