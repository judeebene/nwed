package com.shareqube.nwed.ui.api;


import com.shareqube.nwed.ui.models.SearchResults;

import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.Callback;

/**
 * Created by Jude Ben on 11/2/2015.
 */
public interface NwedService {
    @GET("/books/v1/volumes")
    void search(@Query("q") String query, Callback<SearchResults> callback);
}

