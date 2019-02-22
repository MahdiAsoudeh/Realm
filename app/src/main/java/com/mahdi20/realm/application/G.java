package com.mahdi20.realm.application;


import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

// mahdi20.com
public class G extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();


        context = getApplicationContext();

        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);


    }

}


