package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {
    private static final String TAG = "MainActivity";
    private static final int LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "TEST onStart");
        showProgressBar();
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "DEBUG onCreateLoader");

        String uriString = getText(R.string.source_url).toString();
        String apiKey = getText(R.string.api_key).toString();

        Uri baseUri = Uri.parse(uriString);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("api-key", apiKey);
        uriBuilder.appendQueryParameter("show-fields", "byline");
        uriBuilder.appendQueryParameter("order-by", "newest");

        return new NewsArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> data) {
        hideProgressBar();

        if (data == null || data.size() == 0) {
            if (isNetworkAvailable()) {
                showMessage(R.string.no_articles_found);
            } else {
                showMessage(R.string.no_internet_connection);
            }

            updateUi(new ArrayList<NewsArticle>());
        } else {
            hideMessage();
            updateUi(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {
        updateUi(new ArrayList<NewsArticle>());
    }

    private void updateUi(List<NewsArticle> articles) {
        RecyclerView recyclerView = findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter adapter = new NewsArticleAdapter(this, articles);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private void hideMessage() {
        TextView textView = findViewById(R.id.message_text_view);
        textView.setVisibility(View.INVISIBLE);
    }

    private void showMessage(int messageResId) {
        TextView textView = findViewById(R.id.message_text_view);
        textView.setVisibility(View.VISIBLE);
        textView.setText(messageResId);
    }

    private void hideProgressBar() {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void showProgressBar() {
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }
}
