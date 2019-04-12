package com.codekeyz.gharagenews.ui.news;

import android.annotation.SuppressLint;
import android.content.Context;

import com.codekeyz.gharagenews.mvp.BasePresenter;
import com.codekeyz.gharagenews.mvp.BaseView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("ViewConstructor")
public class NewsPresenter extends BasePresenter<NewsPresenter.View> {

    private NewsService newsService;
    private Context context;

    @Inject
    public NewsPresenter(NewsService newsService, Context context) {
        this.newsService = newsService;
        this.context = context;
    }

    public void getNews(final int page) {
        if (isViewAvailable()){
            getView().showLoadingIndicator();
        }

        this.newsService.getNews(page).enqueue(new Callback<List<NewsData>>() {
            @Override
            public void onResponse(Call<List<NewsData>> call, Response<List<NewsData>> response) {
                if (isViewAvailable()) {
                    getView().hideLoadingIndicator();
                    getView().onResult(response.body(), page);
                }
            }

            @Override
            public void onFailure(Call<List<NewsData>> call, Throwable t) {
                if (isViewAvailable()) {
                    getView().hideLoadingIndicator();
                    getView().onError(t);
                }
            }
        });
    }


    interface View extends BaseView {
        void onResult(List<NewsData> result, int pageNumber);
        void onError(Throwable t);
    }
}
