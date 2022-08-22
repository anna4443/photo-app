package com.as.photoapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.as.photoapp.R;
import com.as.photoapp.database.IRepository;
import com.as.photoapp.database.RepositoryFactory;
import com.as.photoapp.model.User;
import com.as.photoapp.session.Context;
import com.as.photoapp.session.LoginState;
import com.as.photoapp.session.LogoutState;
import com.as.photoapp.session.SessionManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView tvName;
    private TextView tvSurname;
    private TextView tvEmail;
    private TextView tvUsername;
    private TextView tvLogout;

    private SessionManager sessionManager;

    private IRepository repository;

    Context context = new Context();

    LogoutState logoutState = new LogoutState();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tvName = view.findViewById(R.id.tvName);
        tvSurname = view.findViewById(R.id.tvSurname);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvLogout = view.findViewById(R.id.tvLogout);

        sessionManager = new SessionManager(getActivity());

        repository = RepositoryFactory.getRepository(getActivity());

        getUser();

        setupListeners();

        return view;
    }

    private void getUser() {
        HashMap<String,Integer> userMap = sessionManager.getUserDetails();

        Iterator it = userMap.entrySet().iterator();

        int id = 0;

        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            id = (int) pair.getValue();
            it.remove();
        }

        User user = repository.getUserById(id);
        displayUserProfile(user);
    }

    private void displayUserProfile(User user) {
        tvName.setText(user.getName());
        tvSurname.setText(user.getSurname());
        tvEmail.setText(user.getEmail());
        tvUsername.setText(user.getUsername());
    }

    private void setupListeners() {
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();

                logoutState.doAction(context);

                Toast.makeText(getActivity(), context.getState().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
