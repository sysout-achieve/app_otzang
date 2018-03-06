package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Viewimg_Activity extends AppCompatActivity {
    int checked;


    public Bitmap StringToBitMap(String encodedString){ // 스트링으로 받은 이미지를 비트맵으로 다시 변환
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimg);
        SharedPreferences img1 = getSharedPreferences("img1", MODE_PRIVATE);
        SharedPreferences img2 = getSharedPreferences("img2", MODE_PRIVATE);
        SharedPreferences img3 = getSharedPreferences("img3", MODE_PRIVATE);
        ImageView viewimg1 = (ImageView) findViewById(R.id.view1);
        ImageView viewimg2 = (ImageView) findViewById(R.id.view2);
        ImageView viewimg3 = (ImageView) findViewById(R.id.view3);

        Intent intent = getIntent();
        checked = intent.getIntExtra("checked",0);

        String str_img1 = img1.getString(String.valueOf(checked),"");
        if(checked==0){
            viewimg1.setImageResource(R.drawable.noimage);
        } else{
            if(str_img1!=""){
                Bitmap bitmap = StringToBitMap(str_img1);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, 1000, 1300,true);
                viewimg1.setImageBitmap(resize);
            }
            String str_img2 = img2.getString(String.valueOf(checked),"");
            if(str_img2!="" && str_img2 !=null){
                Bitmap bitmap = StringToBitMap(str_img2);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, 1000, 1300,true);
                viewimg2.setImageBitmap(resize);
            }
            String str_img3 = img3.getString(String.valueOf(checked),"");
            if(str_img3!="" && str_img3 !=null){
                Bitmap bitmap = StringToBitMap(str_img3);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, 1000, 1300,true);
                viewimg3.setImageBitmap(resize);
            }

        }

    }
}

