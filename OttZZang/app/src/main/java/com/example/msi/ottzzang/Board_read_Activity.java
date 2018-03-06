package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Board_read_Activity extends AppCompatActivity {
    int cartnum;

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
        setContentView(R.layout.activity_board_read);

        final EditText title_board_read = (EditText) findViewById(R.id.title_board_read);
        final EditText writer_name_read = (EditText) findViewById(R.id.writer_name_read);
        final EditText esti_txt_read = (EditText) findViewById(R.id.esti_txt_read);
        final EditText contents_txt_read = (EditText) findViewById(R.id.contents_txt_read);
        TextView cate_rd = (TextView) findViewById(R.id.cate_rd);
        ImageView img1_rd2 = (ImageView) findViewById(R.id.img1_rd2);


        SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
        SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
        SharedPreferences list_cate = getSharedPreferences("list_kind", MODE_PRIVATE);
        SharedPreferences img1 = getSharedPreferences("img1", MODE_PRIVATE);
        SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
        SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);
        SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);

        Intent intent = getIntent();

        final String login_id = intent.getStringExtra("login_id");

        final int checked = intent.getIntExtra("checked",1);

        String str_img1 = img1.getString(String.valueOf(checked),"");
        if(str_img1!=""){
            Bitmap bitmap = StringToBitMap(str_img1);
            Bitmap resize = Bitmap.createScaledBitmap(bitmap, 600, 700,true);
            img1_rd2.setImageBitmap(resize);
        }
        title_board_read.setText(list_title.getString(String.valueOf(checked),""));
        esti_txt_read.setText(list_esti.getString(String.valueOf(checked),""));
        cate_rd.setText(list_cate.getString(String.valueOf(checked),""));
        contents_txt_read.setText(list_content.getString(String.valueOf(checked),"게시글이 삭제되어 내용을 확인할 수 없습니다."));
        writer_name_read.setText(list_writer.getString(String.valueOf(checked),""));
        final String log_id = login_id_check.getString(String.valueOf(checked),"no_writer");
    }
}
