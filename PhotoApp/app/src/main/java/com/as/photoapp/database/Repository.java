package com.as.photoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.as.photoapp.model.Image;
import com.as.photoapp.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Repository implements IRepository {

    private Context c;
    private DatabaseHelper dbHelper;

    /*public Repository() {
    }*/

    public Repository(Context context) {
        this.c = context;
        dbHelper = new DatabaseHelper(c);
    }



    @Override
    public List<User> getUsers() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DatabaseHelper.ID,
                DatabaseHelper.NAME,
                DatabaseHelper.SURNAME,
                DatabaseHelper.EMAIL,
                DatabaseHelper.USERNAME,
                DatabaseHelper.PASSWORD
        };

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, null, null, null, null, null);

        List<User> users = new ArrayList<>();
        while(cursor.moveToNext()) {
            User user = new User();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
            String surname = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.SURNAME));
            String email = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.EMAIL));
            String username = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.USERNAME));
            String password = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));

            user.setId(id);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);

            users.add(user);
        }

        cursor.close();

        return users;
    }

    @Override
    public void insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME, user.getName());
        values.put(DatabaseHelper.SURNAME, user.getSurname());
        values.put(DatabaseHelper.EMAIL, user.getEmail());
        values.put(DatabaseHelper.USERNAME, user.getUsername());
        values.put(DatabaseHelper.PASSWORD, user.getPassword());

        db.insert(DatabaseHelper.TABLE_USERS, null, values);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {

        User user = new User();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DatabaseHelper.ID,
                DatabaseHelper.NAME,
                DatabaseHelper.SURNAME,
                DatabaseHelper.EMAIL,
                DatabaseHelper.USERNAME,
                DatabaseHelper.PASSWORD
        };

        String selection =  DatabaseHelper.USERNAME + " = ?" + " AND " + DatabaseHelper.PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
            String surname = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.SURNAME));
            String email = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.EMAIL));
            String uname = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.USERNAME));
            String pass = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));

            user.setId(id);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setUsername(uname);
            user.setPassword(pass);
        }

        return user;
    }

    @Override
    public User getUserById(int id) {

        User user = new User();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DatabaseHelper.ID,
                DatabaseHelper.NAME,
                DatabaseHelper.SURNAME,
                DatabaseHelper.EMAIL,
                DatabaseHelper.USERNAME,
                DatabaseHelper.PASSWORD
        };

        String selection =  DatabaseHelper.ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        while(cursor.moveToNext()) {
            int idUser = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
            String surname = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.SURNAME));
            String email = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.EMAIL));
            String username = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.USERNAME));
            String password = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));

            user.setId(idUser);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = new User();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DatabaseHelper.ID,
                DatabaseHelper.NAME,
                DatabaseHelper.SURNAME,
                DatabaseHelper.EMAIL,
                DatabaseHelper.USERNAME,
                DatabaseHelper.PASSWORD
        };

        String selection =  DatabaseHelper.USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        while(cursor.moveToNext()) {
            int idUser = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.NAME));
            String surname = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.SURNAME));
            String email = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.EMAIL));
            String uname = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.USERNAME));
            String password = cursor.getString(
                    cursor.getColumnIndexOrThrow(DatabaseHelper.PASSWORD));

            user.setId(idUser);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setUsername(uname);
            user.setPassword(password);
        }
        return user;
    }

    @Override
    public List<Image> getImages() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String columns[] = {DatabaseHelper.ID_PHOTO, DatabaseHelper.USER_NAME, DatabaseHelper.PHOTO, DatabaseHelper.DESCRIPTION};

        Cursor cursor = db.query(DatabaseHelper.TABLE_PHOTOS, columns, null, null, null, null, null);

        List<Image> images = new ArrayList<>();

        while(cursor.moveToNext()){
            Image image = new Image();
            User user = new User();

            String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.USER_NAME));
            String fileName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PHOTO));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.DESCRIPTION));

            user = getUserByUsername(username);

            image.setUser(user);
            image.setFileName(fileName);
            image.setDescription(description);

            images.add(image);
        }
        cursor.close();

        return images;
    }

    @Override
    public void insertImage(Image image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.USER_NAME, image.getUser().getUsername());
        values.put(DatabaseHelper.PHOTO, image.getFileName());
        values.put(DatabaseHelper.DESCRIPTION, image.getDescription());

     /*   Set<Map.Entry<String, Object>> s = values.valueSet();
        for (Map.Entry<String, Object> entry : s) {
            System.out.println(entry.getValue());
        }*/


        db.insert(DatabaseHelper.TABLE_PHOTOS, null, values);
    }
}
