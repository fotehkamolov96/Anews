/**
 * Contact
 * telegram: @kamolovme
 * email: kamolov-9696@mail.ru
 */
package com.kamolovproducts.anews.retrofit;

import com.kamolovproducts.anews.data.TotalNews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("v2/top-headlines")
    Call<TotalNews> getTotalNews(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("v2/top-headlines")
    Call<TotalNews> getTotalNews(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);

    @GET("v2/everything")
    Call<TotalNews> getSearchedTotalNews(@Query("q") String country, @Query("apiKey") String apiKey);
}
