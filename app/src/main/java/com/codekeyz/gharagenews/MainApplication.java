package com.codekeyz.gharagenews;

import android.app.Application;


import com.codekeyz.gharagenews.ui.news.DaggerNewsComponent;
import com.codekeyz.gharagenews.ui.news.NewsComponent;
import com.codekeyz.gharagenews.ui.news.NewsModule;

public class MainApplication extends Application {

    private static NewsComponent newsComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        newsComponent = DaggerNewsComponent.builder()
                .newsModule(new NewsModule(getApplicationContext()))
                .build();
    }

    public static NewsComponent getNewsComponent() {
        return newsComponent;
    }
}
