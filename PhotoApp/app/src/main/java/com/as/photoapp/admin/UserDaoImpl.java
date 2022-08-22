package com.as.photoapp.admin;

import android.content.Context;

import com.as.photoapp.database.IRepository;
import com.as.photoapp.database.RepositoryFactory;
import com.as.photoapp.model.User;

import java.util.List;

public class UserDaoImpl implements UserDao{

    List<User> users;

    private IRepository repository;

    public UserDaoImpl(Context context) {
        repository = RepositoryFactory.getRepository(context);

        users = repository.getUsers();
    }


    @Override
    public List<User> getAllUsers() {
        return users;
    }
}
