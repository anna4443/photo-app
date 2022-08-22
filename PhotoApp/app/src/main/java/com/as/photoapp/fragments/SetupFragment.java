package com.as.photoapp.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.as.photoapp.R;
import com.as.photoapp.database.IRepository;
import com.as.photoapp.database.RepositoryFactory;
import com.as.photoapp.model.Image;
import com.as.photoapp.model.User;
import com.as.photoapp.session.Context;
import com.as.photoapp.session.LoginState;
import com.as.photoapp.session.SessionManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetupFragment extends Fragment {

    private RadioButton rbPackage;
    private RadioGroup rgPackages;

    private Button btnSavePackage;

    //private RadioButton rbPhoto;
    //private RadioGroup rgPhotos;

    private Button btnUploadPhoto;

    private TextView tvUploadPhoto;
    private TextView tvClickToUpload;

    private ImageView ivPhoto;

    private EditText etDescription;

    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD = 1;

    private SessionManager sessionManager;

    private String savedPhotoPath;

    private IRepository repository;

    public SetupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_setup, container, false);

        rgPackages = view.findViewById(R.id.rgPackages);

        btnSavePackage = view.findViewById(R.id.btnSavePackage);

        //rgPhotos = view.findViewById(R.id.rgPhotos);

        btnUploadPhoto = view.findViewById(R.id.btnUploadPhoto);
        tvUploadPhoto = view.findViewById(R.id.tvUploadPhoto);
        tvClickToUpload = view.findViewById(R.id.tvClickToUpload);
        ivPhoto = view.findViewById(R.id.ivPhoto);
        etDescription = view.findViewById(R.id.etDescription);

        sessionManager = new SessionManager(getActivity());

        repository = RepositoryFactory.getRepository(getActivity());

        btnSavePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*int selectedId=rgPackages.getCheckedRadioButtonId();
                rbPackage=view.findViewById(selectedId);

                System.out.println(rbPackage.getText());*/

                try {
                    int selectedId=rgPackages.getCheckedRadioButtonId();
                    rbPackage=view.findViewById(selectedId);

                    System.out.println(rbPackage.getText());
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "Choose package!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkPermissions();

        getPhoto();

        uploadPhoto();

        return view;
    }

    private void uploadPhoto() {
        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ivPhoto.getDrawable() != null && !etDescription.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Uploading photo!", Toast.LENGTH_SHORT).show();
                    //etDescription.getText().clear();
                    //ivPhoto.setImageResource(0);
                    String description = etDescription.getText().toString();

                    savePhoto(savedPhotoPath, description);
                }
                else{
                    Toast.makeText(getActivity(), "Insert photo and description!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void savePhoto(String savedPhotoPath, String description) {
        HashMap<String,Integer> userMap = sessionManager.getUserDetails();
        Image image = new Image();
        Iterator it = userMap.entrySet().iterator();

        int id = 0;

        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            id = (int) pair.getValue();
            it.remove();
        }

        User user = repository.getUserById(id);

        image.setUser(user);
        image.setFileName(savedPhotoPath);
        image.setDescription(description);

        System.out.println(id + " " + savedPhotoPath + " " + description);
        repository.insertImage(image);
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }
    }

    private void getPhoto() {
       tvClickToUpload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(intent, RESULT_LOAD);
           }
       });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Permission not granted", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_LOAD:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    savedPhotoPath = picturePath;
                    cursor.close();
                    ivPhoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
        }
    }
}
