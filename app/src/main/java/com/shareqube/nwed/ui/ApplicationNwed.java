package com.shareqube.nwed.ui;

import android.app.Application;

import com.shareqube.nwed.ui.adapter.NwedAdapter;
import com.shareqube.nwed.ui.models.Nwed;

import java.util.List;

/**
 * Created by Jude Ben on 11/3/2015.
 */
public class ApplicationNwed extends Application {

    NwedAdapter adapter ;
     private List<Nwed> nwed;
    @Override
    public void onCreate(){
        adapter= new NwedAdapter(nwed);
        super.onCreate();
    }

    public NwedAdapter getAdapter(){
        return adapter;
    }

    public List<Nwed> getNweds() {
        return nwed;
    }

    public void setBooks(List<Nwed> mResultsist) {
        this.nwed = mResultsist;
    }
}
