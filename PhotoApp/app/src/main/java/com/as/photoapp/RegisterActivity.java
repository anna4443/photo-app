package com.as.photoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.as.photoapp.database.DatabaseHelper;
import com.as.photoapp.database.IRepository;
import com.as.photoapp.database.Repository;
import com.as.photoapp.database.RepositoryFactory;
import com.as.photoapp.model.User;
import com.as.photoapp.utils.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class RegisterActivity extends AppCompatActivity{

    private EditText etName;
    private EditText etSurname;
    private EditText etEmail;
    private EditText editTextUsername;
    private EditText editTextPassword;

    private Button btnRegister;

    private IRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        setListeners();

        repository = RepositoryFactory.getRepository(this);
    }

    private void setListeners() {
       registerUser();
    }

    private void registerUser() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();

                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void validateData() {
        if(!etName.getText().toString().isEmpty() && !etSurname.getText().toString().isEmpty() && !etEmail.getText().toString().isEmpty() &&
                !editTextUsername.getText().toString().isEmpty() && !editTextPassword.getText().toString().isEmpty()){
            saveUser();
            Toast.makeText(this, "User is registered", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "You didn't insert all data", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUser() {
        User user = new User();

        Utils utils = Utils.getInstance();

        String password = utils.hashPassword(editTextPassword.getText().toString());
        user.setName(etName.getText().toString());
        user.setSurname(etSurname.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setUsername(editTextUsername.getText().toString());
        user.setPassword(password);

        repository.insertUser(user);
    }

    private void init() {
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        btnRegister = findViewById(R.id.btnRegister);
    }
}
