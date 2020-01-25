package com.example.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class NewsArticleLoader extends AsyncTaskLoader<List<NewsArticle>> {
    private static final String TAG = "NewsArticleLoader";
    private String mUrl;

    NewsArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.d(TAG, "DEBUG onStartLoading");
        forceLoad();
    }

    @Override
    public List<NewsArticle> loadInBackground() {
        Log.d(TAG, "DEBUG loadInBackground");
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchNewsArticleData(mUrl);
    }
}
