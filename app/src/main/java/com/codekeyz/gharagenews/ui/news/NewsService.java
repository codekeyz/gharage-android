package com.codekeyz.gharagenews.ui.news;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("/")
    Call<List<NewsData>> getNews(@Query("page") int pageNumber);
}
