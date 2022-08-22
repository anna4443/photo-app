package com.as.photoapp.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.as.photoapp.R;
import com.as.photoapp.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends ArrayAdapter<Image> implements Filterable {

    private List<Image> images;
    //private List<Image> imageList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ImagesAdapter(@NonNull Context context, int resource, @NonNull List<Image> images) {
        super(context, resource, images);

        this.images = images;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.images, parent, false);
        }

        final Image image = getItem(position);

        TextView tvUsername = convertView.findViewById(R.id.tvUsername);
        ImageView ivImage = convertView.findViewById(R.id.ivImage);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);

        tvUsername.setText(image.getUser().getUsername());
        ivImage.setImageBitmap(BitmapFactory.decodeFile(image.getFileName()));
        tvDescription.setText(image.getDescription());


        return convertView;
    }
}
