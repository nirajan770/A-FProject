package com.abercrombiefitch.api;

import com.abercrombiefitch.api.model.ResponseData;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by Nirajan on 9/28/2015.
 */
public interface AbercrombieAPI {

    @GET("/Feeds/promotions.json")
    Observable<ResponseData> getPromotions();
}
