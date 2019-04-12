package com.codekeyz.gharagenews.ui.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codekeyz.gharagenews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsData> mainNewsData;
    private Context context;
    private Picasso picasso;

    @Inject
    public NewsAdapter(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
        mainNewsData = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card_one, parent, false);
        return new NewsViewHolder(view);
    }

    public void addNewsData(List<NewsData> newsDataList) {
        this.mainNewsData = newsDataList;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsData newsData = mainNewsData.get(position);

        this.picasso.load(newsData.getPost_image())
                .into(holder.news_image);

        holder.news_title.setText(newsData.getTitle());
        holder.news_desc.setText(newsData.getPost_detail());
        holder.news_date.setText(newsData.getPost_time());
    }

    @Override
    public int getItemCount() {
        return mainNewsData.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.post_image)
        AppCompatImageView news_image;

        @BindView(R.id.news_title)
        TextView news_title;

        @BindView(R.id.news_desc)
        TextView news_desc;

        @BindView(R.id.news_date)
        TextView news_date;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
