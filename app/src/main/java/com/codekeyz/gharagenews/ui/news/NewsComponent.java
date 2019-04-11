package com.codekeyz.gharagenews.ui.news;

import dagger.Component;

@Component(modules = {
        NewsModule.class
})
public interface NewsComponent {

    void inject(NewsActivity newsActivity);
}
