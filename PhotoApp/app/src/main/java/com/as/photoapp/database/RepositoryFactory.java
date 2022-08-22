package com.as.photoapp.database;

import android.content.Context;

public class RepositoryFactory {
    //private static Context context;

    public static IRepository getRepository(Context context) {
        return new Repository(context);
    }
}
