package com.shareqube.nwed.ui.models;

/**
 * Created by Jude Ben on 11/3/2015.
 */
public class DetailsNwed {


    private VolumeInfo volumeInfo;
    private int index;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        //notifyPropertyChanged();
    }


    public VolumeInfo getVolumeInfo() {
        return volumeInfo;

    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;

        //notifyPropertyChanged(BR.volumeInfo);
    }
}
