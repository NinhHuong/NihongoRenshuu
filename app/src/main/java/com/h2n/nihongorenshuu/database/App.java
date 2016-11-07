package com.h2n.nihongorenshuu.database;

import android.app.Application;
import android.content.Context;

/**
 * Created by ninhh on 11/7/2016.
 */

public class App extends Application{
    private static Context context;
    private static DatabaseHelper dbHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DatabaseHelper();
        DatabaseManager.initializeInstance(dbHelper);

    }


    public static Context getContext(){
        return context;
    }

}
