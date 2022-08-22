package com.as.photoapp.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.as.photoapp.R;
import com.as.photoapp.adapters.ImagesAdapter;
import com.as.photoapp.database.DatabaseHelper;
import com.as.photoapp.database.IRepository;
import com.as.photoapp.database.RepositoryFactory;
import com.as.photoapp.model.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {

    private SearchView svDescription;
    private ListView lvImages;
    private DatabaseHelper databaseHelper;
    private ImagesAdapter imagesAdapter;
    private IRepository repository;

    private List<Image> images = new ArrayList<>();
    private List<Image> copiedList = new ArrayList<>();

    public PhotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        svDescription = view.findViewById(R.id.svDescription);
        lvImages = view.findViewById(R.id.lvImages);

        repository = RepositoryFactory.getRepository(getActivity());

        initDatabase();
        readDataInListView();

        addListenerToSearchView();

        return view;
    }

    private void addListenerToSearchView() {

        svDescription.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                imagesAdapter = new ImagesAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<Image>());

                for (Image image : copiedList){

                    if(image.getDescription().contains(newText)){

                        imagesAdapter.add(image);
                    }
                }

                lvImages.setAdapter(imagesAdapter);

                return true;
            }
        });
    }

    private void initDatabase() {
        databaseHelper = new DatabaseHelper(getActivity());
    }

    private void readDataInListView() {

        images = repository.getImages();

        imagesAdapter = new ImagesAdapter(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<Image>());

        for (Image image : images) {

            imagesAdapter.add(image);

            copiedList.add(image);
        }

        lvImages.setAdapter(imagesAdapter);
    }
}
