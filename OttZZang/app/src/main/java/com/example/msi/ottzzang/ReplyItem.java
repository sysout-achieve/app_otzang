package com.example.msi.ottzzang;

import android.graphics.Bitmap;

/**
 * Created by MSI on 2018-02-04.
 */

public class ReplyItem {
    private String replytxt;
    private Bitmap re_pro_img;
    private String re_id;
    private String id_check;

    public String getReplytxt(){
        return replytxt;
    }
    public Bitmap getRe_pro_img(){
        return re_pro_img;
    }
    public String getRe_id(){
        return re_id;
    }
    public String getId_check(){
            return id_check;
    }


    public ReplyItem(String replytxt, Bitmap re_pro_img, String re_id, String id){
        this.replytxt = replytxt;
        this.re_pro_img = re_pro_img;
        this.re_id = re_id;
        id_check = id;
    }

}
