package com.example.msi.ottzzang;

import android.content.Intent;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by MSI on 2018-02-04.
 */

public class BoardItem {
    private Bitmap bod_img;
    private String bod_title;
    private String bod_cate;
    private String bod_writer;
    private Integer re_num;
    private Integer heart_num;

    public Bitmap getBod_img(){
        return bod_img;
    }
    public String getBod_title(){
        return bod_title;
    }
    public String getBod_writer(){
        return bod_writer;
    }
    public String getBod_cate(){
        return bod_cate;
    }
    public Integer getRe_num(){
        return re_num;
    }
    public Integer getHeart_num(){
        return heart_num;
    }

    public BoardItem(Bitmap bod_img, String bod_title, String bod_writer,String bod_cate, Integer re_num, Integer heart_num){
        this.bod_img = bod_img;
        this.bod_title = bod_title;
        this.bod_writer = bod_writer;
        this.bod_cate = bod_cate;
        this.re_num = re_num;
        this.heart_num = heart_num;

    }

}
