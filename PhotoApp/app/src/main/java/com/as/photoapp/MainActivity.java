package com.as.photoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.as.photoapp.admin.AdminActivity;
import com.as.photoapp.database.DatabaseHelper;
import com.as.photoapp.database.IRepository;
import com.as.photoapp.database.RepositoryFactory;
import com.as.photoapp.fragments.HomeFragment;
import com.as.photoapp.fragments.ProfileFragment;
import com.as.photoapp.model.User;
import com.as.photoapp.session.Context;
import com.as.photoapp.session.LoginState;
import com.as.photoapp.session.SessionManager;
import com.as.photoapp.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;

    private Button btnLogin;

    private TextView tvClickRegisterScreen;

    private IRepository repository;

    private SessionManager sessionManager;

    Context context = new Context();

    LoginState loginState = new LoginState();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setListeners();

        repository = RepositoryFactory.getRepository(this);

        sessionManager = new SessionManager(this);
    }

    private void setListeners() {
        loginUser();
        switchToRegisterScreen();
    }

    private void switchToRegisterScreen() {
        tvClickRegisterScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    private void checkData() {
        if (!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){

            Utils utils = Utils.getInstance();

            String username = etUsername.getText().toString();
            String password = utils.hashPassword(etPassword.getText().toString());

            whetherUserExists(username, password);
            //Toast.makeText(this, "User is logged in", Toast.LENGTH_SHORT).show();

            loginState.doAction(context);
            Toast.makeText(this, context.getState().toString(), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "You didn't insert all data", Toast.LENGTH_SHORT).show();
        }
    }

    private void whetherUserExists(String username, String password) {

        User user = repository.findByUsernameAndPassword(username, password);

        if (user.getUsername() != null && user.getPassword() != null){
            checkUserRole(user, password);
            sessionManager.createLoginSession(user.getId());
        }
        else{
            Toast.makeText(this, "You are not logged in, register!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserRole(User user, String password) {
        if (user.getUsername().equals("admin") && user.getPassword().equals(password)){
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void init() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);

        tvClickRegisterScreen = findViewById(R.id.tvClickRegisterScreen);
    }
}
