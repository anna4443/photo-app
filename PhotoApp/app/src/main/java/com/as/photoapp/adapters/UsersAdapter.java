package com.as.photoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.as.photoapp.R;
import com.as.photoapp.model.User;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {

    private List<User> users;
    private LayoutInflater layoutInflater;

    public UsersAdapter(@NonNull Context context, int resource, @NonNull List<User> users) {
        super(context, resource, users);

        this.users = users;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.users, parent, false);
        }

        final User user = getItem(position);

        TextView tvName = convertView.findViewById(R.id.txtViewName);
        TextView tvSurname = convertView.findViewById(R.id.txtViewSurname);

        tvName.setText(user.getName());
        tvSurname.setText(user.getSurname());

        return convertView;
    }
}
