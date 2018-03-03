package com.example.msi.ottzzang;

import android.graphics.Bitmap;

/**
 * Created by MSI on 2018-02-04.
 */

public class CartItem {
    private Bitmap cart_img;
    private String cart_title;



    public Bitmap getcart_img(){
        return cart_img;
    }
    public String getcart_title(){
        return cart_title;
    }



    public CartItem(Bitmap cart_img, String cart_title){
        this.cart_img = cart_img;
        this.cart_title = cart_title;
    }

}
