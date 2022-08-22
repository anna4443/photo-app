package com.as.photoapp.admin;

import android.content.Context;

import com.as.photoapp.database.IRepository;
import com.as.photoapp.database.RepositoryFactory;
import com.as.photoapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Container{

    public User users[] = null;

    private Context context;

    public UserRepository(Context context) {
        this.context = context;
    }

    @Override
    public Iterator getIterator() {
        return new UserIterator(context);
    }

    private class UserIterator implements Iterator {

        private Context context;
        //private IRepository repository;

        UserDao userDao;

        int index;

        public UserIterator(Context context) {
            this.context = context;

            userDao = new UserDaoImpl(context);
        }

        @Override
        public boolean hasNext() {

            //repository = RepositoryFactory.getRepository(context);

            //List<User> list = repository.getUsers();

            //users = list.toArray(new User[0]);

            List<User> list = userDao.getAllUsers();

            users = list.toArray(new User[0]);


            if(index < users.length){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {

            if(this.hasNext()){
                return users[index++];
            }
            return null;
        }
    }
}
