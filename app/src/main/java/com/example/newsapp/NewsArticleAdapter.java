package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsArticleAdapter extends RecyclerView.Adapter<NewsArticleAdapter.ViewHolder> {
    private List<NewsArticle> mNewsArticleList;
    private Context mContext;

    NewsArticleAdapter(Context mContext, List<NewsArticle> mNewsArticleList) {
        this.mContext = mContext;
        this.mNewsArticleList = mNewsArticleList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mPublicationDateTextView;
        TextView mAuthorTextView;
        TextView mTitleTextView;
        TextView mSectionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mPublicationDateTextView = itemView.findViewById(R.id.date_text_view);
            mAuthorTextView = itemView.findViewById(R.id.author_text_view);
            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mSectionTextView = itemView.findViewById(R.id.section_text_view);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()
        ).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewsArticle current = mNewsArticleList.get(position);

        Date date = current.getPublicationDate();

        if (date == null) {
            holder.mPublicationDateTextView.setVisibility(View.GONE);
        } else {
            holder.mPublicationDateTextView.setText(formatDate(date));
        }

        String author = current.getAuthor();

        if (author == null) {
            holder.mAuthorTextView.setVisibility(View.GONE);
        } else {
            holder.mAuthorTextView.setText(String.format(mContext.getText(R.string.by).toString(), author));
        }

        holder.mTitleTextView.setText(current.getTitle());
        holder.mSectionTextView.setText(current.getSection());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, current.getUri());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNewsArticleList.size();
    }

    private String formatDate(Date date) {
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT, Locale.getDefault());
        return dateFormat.format(date);
    }
}
