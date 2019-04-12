package com.codekeyz.gharagenews.ui.news;

import com.codekeyz.gharagenews.di.modules.NewsModule;

import dagger.Component;

@Component(modules = {
        NewsModule.class
})
public interface NewsComponent {

    void inject(NewsActivity newsActivity);
}
