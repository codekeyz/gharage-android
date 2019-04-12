package com.codekeyz.gharagenews.ui.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.codekeyz.gharagenews.MainApplication;
import com.codekeyz.gharagenews.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsActivity extends AppCompatActivity implements NewsPresenter.View {

    @Inject
    NewsPresenter newsPresenter;

    @Inject
    NewsAdapter newsAdapter;

    @BindView(R.id.parentNewsLayout)
    CoordinatorLayout parentLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    @BindView(R.id.newslist)
    RecyclerView recyclerView;

    private int CURRENT_PAGE = 1, NEXT_PAGE = 1;
    private Map<String, List<NewsData>> newsDataMap;
    private boolean isloading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        MainApplication.getNewsComponent().inject(this);

        if (savedInstanceState != null) {
            this.CURRENT_PAGE = savedInstanceState.getInt("CURRENT_PAGE", CURRENT_PAGE);
            this.NEXT_PAGE = savedInstanceState.getInt("NEXT_PAGE", NEXT_PAGE);
        }
        newsDataMap = new HashMap<>();

        init();
    }

    private void init() {

        // Setup Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Setup RecyclerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newsAdapter);

        // Setup LoadMoreListener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView rv, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItems = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isloading && totalItems <= (lastVisibleItem + 5)){
                    newsPresenter.getNews(NEXT_PAGE);
                }
            }
        });

        // Set view for NewsPresenter
        this.newsPresenter.takeView(this);

        this.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newsPresenter.getNews(CURRENT_PAGE);
            }
        });

        Toast.makeText(this, String.valueOf(CURRENT_PAGE), Toast.LENGTH_SHORT).show();

        this.newsPresenter.getNews(CURRENT_PAGE);

    }

    @Override
    public void showLoadingIndicator() {
        this.isloading = true;
        this.swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoadingIndicator() {
        this.isloading = false;
        this.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(this.parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onResult(List<NewsData> result, int pageNumber) {
        List<NewsData> list = new ArrayList<>();
        this.newsDataMap.put(String.valueOf(pageNumber), result);
        for (List<NewsData> data: newsDataMap.values()){
            list.addAll(data);
        }
        this.newsAdapter.addNewsData(list);
        this.CURRENT_PAGE = pageNumber;
        this.NEXT_PAGE = pageNumber + 1;
    }

    @Override
    public void onError(Throwable t) {
        this.showMessage(t.getLocalizedMessage());
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CURRENT_PAGE", CURRENT_PAGE);
        outState.putInt("NEXT_PAGE", NEXT_PAGE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.CURRENT_PAGE =  savedInstanceState.getInt("CURRENT_PAGE", CURRENT_PAGE);
        this.NEXT_PAGE = savedInstanceState.getInt("NEXT_PAGE", NEXT_PAGE);
    }
}
