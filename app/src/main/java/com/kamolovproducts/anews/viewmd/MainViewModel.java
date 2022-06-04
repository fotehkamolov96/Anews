/**
 * Contact
 * telegram: @kamolovme
 * email: kamolov-9696@mail.ru
 */
package com.kamolovproducts.anews.viewmd;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kamolovproducts.anews.constatns.NewsConstants;
import com.kamolovproducts.anews.data.NewsModel;
import com.kamolovproducts.anews.data.TotalNews;
import com.kamolovproducts.anews.retrofit.ApiClient;
import com.kamolovproducts.anews.retrofit.RestInterface;
import com.kamolovproducts.anews.utils.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private final MutableLiveData<List<NewsModel>> newsLiveData;
    private final List<NewsModel> newsList;
    private String countryCode;
    private String apiKey;

    public MainViewModel() {
        newsLiveData = new MutableLiveData<>();
        newsList = new ArrayList<>();
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        getNews(countryCode, "");
    }

    public MutableLiveData<List<NewsModel>> getNewsLiveData() {
        return newsLiveData;
    }

    private RestInterface getRestInterface() {
        RestInterface[] restInterface = new RestInterface[1];
        restInterface[0] = ApiClient.getClient(Util.API_BASE_URL).create(RestInterface.class);
        return restInterface[0];
    }

    private void getNews(String langCode, String category) {
        isLoading.setValue(true);
        RestInterface restInterface = getRestInterface();
        Call<TotalNews> call;
        newsList.clear();
        newsLiveData.setValue(null);
        if (!category.equals("")) {
            call = restInterface.getTotalNews(langCode, category, apiKey);
        } else {
            call = restInterface.getTotalNews(langCode, apiKey);
        }
        call.enqueue(new Callback<TotalNews>() {
            @Override
            public void onResponse(Call<TotalNews> call, Response<TotalNews> response) {
                if (response.body() != null) {
                    TotalNews totalNews = response.body();
                    fillNewsList(totalNews);
                }
            }

            @Override
            public void onFailure(Call<TotalNews> call, Throwable t) {
                newsLiveData.setValue(null);
                isLoading.setValue(false);
            }
        });
    }


    private void getSearchedNews(String keyword) {
        RestInterface restInterface = getRestInterface();
        Call<TotalNews> call;
        newsList.clear();
        newsLiveData.setValue(null);
        call = restInterface.getSearchedTotalNews(keyword, apiKey);

        call.enqueue(new Callback<TotalNews>() {
            @Override
            public void onResponse(@NonNull Call<TotalNews> call, Response<TotalNews> response) {
                if (response.body() != null) {
                    TotalNews totalNews = response.body();
                    fillNewsList(totalNews);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TotalNews> call, Throwable t) {
                newsLiveData.setValue(null);
            }
        });
    }

    private void fillNewsList(TotalNews totalNews) {
        newsList.addAll(totalNews.getNewsList());
        newsLiveData.setValue(newsList);
        isLoading.setValue(false);
    }

    public void newsCategoryClick(Object category) {
        getNews(countryCode, String.valueOf(category));
    }

    public void searchNews(String keyword) {
        getSearchedNews(keyword);
    }
}
