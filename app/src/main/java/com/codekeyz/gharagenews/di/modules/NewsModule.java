package com.codekeyz.gharagenews.di.modules;

import android.content.Context;
import android.util.Log;

import com.codekeyz.gharagenews.ui.news.NewsService;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

@Module(includes = NetworkModule.class)
public class NewsModule {

    private static final String TAG = NewsModule.class.getSimpleName();
    private Context context;

    public NewsModule(Context context) {
        this.context = context;
    }

    @Provides
    public NewsService newsService(Retrofit retrofit) {
        return retrofit.create(NewsService.class);
    }

    @Provides
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return interceptor;
    }

    @Provides
    @Named("baseURL")
    public String baseUrl() {
        return "https://gharage-news.herokuapp.com/";
    }

    @Provides
    public Context context() {
        return context;
    }

    @Provides
    public File cacheFile(Context context) {
        return new File(context.getCacheDir(), "okhttp_cache");
    }

    @Provides
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); // 10MB Cache
    }
}
