package com.as.photoapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.as.photoapp.R;
import com.as.photoapp.adapters.UsersAdapter;
import com.as.photoapp.admin.Iterator;
import com.as.photoapp.admin.UserRepository;
import com.as.photoapp.database.IRepository;
import com.as.photoapp.database.RepositoryFactory;
import com.as.photoapp.model.User;

import java.lang.annotation.Repeatable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSetupFragment extends Fragment {

    private ListView lvUsers;
    private IRepository repository;
    private UsersAdapter usersAdapter;

    public AdminSetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_setup, container, false);

        lvUsers = view.findViewById(R.id.lvUsers);

        repository = RepositoryFactory.getRepository(getActivity());

        readUsers();

        return view;
    }

    private void readUsers() {

        //List<User> users = repository.getUsers();


        usersAdapter = new UsersAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<User>());


        /*for (User user : users){
            usersAdapter.add(user);
        }*/

        UserRepository userRepository = new UserRepository(getActivity());

        for(Iterator iter = userRepository.getIterator(); iter.hasNext();){
            User user = (User) iter.next();
            //System.out.println(user.getName());
            usersAdapter.add(user);
        }

        lvUsers.setAdapter(usersAdapter);
    }

}
