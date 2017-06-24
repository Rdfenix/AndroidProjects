package com.apps.fourtech.mapatig;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

//IGNORAR ESSA CLASSE

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        //NECESSÁRIO, POIS O PROJETO POSSUI MAIS DE 35.000 MÉTODOS(IMPORTS)
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
