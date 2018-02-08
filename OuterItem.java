package com.example.msi.ottzzang;

import android.graphics.Bitmap;

/**
 * Created by MSI on 2018-02-04.
 */

public class OuterItem {
    private Bitmap profile;
    private String outer_info;
    private String outer_size;

    public Bitmap getProfile(){
        return profile;
    }
    public String getOuter_info(){
        return outer_info;
    }
    public String getOuter_size(){
        return outer_size;
    }

    public OuterItem(Bitmap profile, String outer_info, String outer_size){
        this.profile = profile;
        this.outer_info = outer_info;
        this.outer_size = outer_size;
    }

}
