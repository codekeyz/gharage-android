package com.codekeyz.gharagenews;

import android.app.Application;

import com.codekeyz.gharagenews.di.components.DaggerNewsComponent;
import com.codekeyz.gharagenews.di.components.NewsComponent;
import com.codekeyz.gharagenews.di.modules.NewsModule;

public class MainApplication extends Application {

    private static NewsComponent newsComponent;

    public static NewsComponent getNewsComponent() {
        return newsComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        newsComponent = DaggerNewsComponent.builder()
                .newsModule(new NewsModule(getApplicationContext()))
                .build();
    }
}
