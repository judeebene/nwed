package com.shareqube.nwed.ui.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jude Ben on 11/3/2015.
 */
public class SearchResults {
    public int totalItems;
    @SerializedName("items")
    public List<Nwed> books;


    public List<Nwed> getNweds() {
        return books;
    }

    public void setBooks(List<Nwed> mResultsist) {
        this.books = mResultsist;
    }

}
