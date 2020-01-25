package com.example.newsapp;

import android.net.Uri;
import java.util.Date;

class NewsArticle {
    private String mAuthor;
    private Date mPublicationDate;
    private String mSection;
    private String mTitle;
    private Uri mUri;

    NewsArticle(String mSection, String mTitle, Uri mUri, String mAuthor, Date mPublicationDate) {
        this.mAuthor = mAuthor;
        this.mPublicationDate = mPublicationDate;
        this.mSection = mSection;
        this.mTitle = mTitle;
        this.mUri = mUri;
    }

    NewsArticle(String mSection, String mTitle, Uri mUri, String mAuthor) {
        this.mAuthor = mAuthor;
        this.mSection = mSection;
        this.mTitle = mTitle;
        this.mUri = mUri;
    }

    NewsArticle(String mSection, String mTitle, Uri mUri, Date mPublicationDate) {
        this.mPublicationDate = mPublicationDate;
        this.mSection = mSection;
        this.mTitle = mTitle;
        this.mUri = mUri;
    }

    NewsArticle(String mSection, String mTitle, Uri mUri) {
        this.mSection = mSection;
        this.mTitle = mTitle;
        this.mUri = mUri;
    }

    String getAuthor() {
        return mAuthor;
    }

    Date getPublicationDate() {
        return mPublicationDate;
    }

    String getSection() {
        return mSection;
    }

    String getTitle() {
        return mTitle;
    }

    Uri getUri() {
        return mUri;
    }
}
