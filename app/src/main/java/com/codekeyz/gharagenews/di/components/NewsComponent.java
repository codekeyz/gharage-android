package com.codekeyz.gharagenews.di.components;

import com.codekeyz.gharagenews.di.modules.NewsModule;
import com.codekeyz.gharagenews.ui.news.NewsActivity;

import dagger.Component;

@Component(modules = {
        NewsModule.class
})
public interface NewsComponent {

    void inject(NewsActivity newsActivity);
}
