/**
 * Contact
 * telegram: @kamolovme
 * email: kamolov-9696@mail.ru
 */

package com.kamolovproducts.anews.data;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;


public class NewsModel {

    @NonNull
    @SerializedName("source")
    private Source source;

    @NonNull
    @SerializedName("content")
    private String content;


    @NonNull
    @SerializedName("author")
    private String author;

    @NonNull
    @SerializedName("title")
    private String newsTitle;

    @NonNull
    @SerializedName("description")
    private String newsDescription;

    @NonNull
    @SerializedName("url")
    private String newsUrl;

    @NonNull
    @SerializedName("urlToImage")
    private String newsImage;

    @NonNull
    @SerializedName("publishedAt")
    private Date newsPublishedDate;


    @NonNull
    public Source getSource() {
        return source;
    }

    public void setSource(@NonNull Source source) {
        this.source = source;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }


    @NonNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull String author) {
        this.author = author;
    }


    @NonNull
    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(@NonNull String newsTitle) {
        this.newsTitle = newsTitle;
    }

    @NonNull
    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(@NonNull String newsDescription) {
        this.newsDescription = newsDescription;
    }

    @NonNull
    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(@NonNull String newsUrl) {
        this.newsUrl = newsUrl;
    }

    @NonNull
    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(@NonNull String newsImage) {
        this.newsImage = newsImage;
    }

    @NonNull
    public String getNewsPublishedDate() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(newsPublishedDate);
    }

    public void setNewsPublishedDate(@NonNull Date newsPublishedDate) {
        this.newsPublishedDate = newsPublishedDate;
    }

    //Added for Child JSON Object
    public static class Source {
        @SerializedName("name")
        private String sourceName;

        public String getSourceName() {
            return sourceName;
        }

        public void setSourceName(String sourceName) {
            this.sourceName = sourceName;
        }
    }
}
