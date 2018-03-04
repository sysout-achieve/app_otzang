package com.example.msi.ottzzang;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BoardActivity extends AppCompatActivity {
    int call_value;
    int gallery_code;

    String spin_cate;
    String spin_es;
ImageView bimg_1;
ImageView bimg_2;
ImageView bimg_3;
    void pickPickure(){
        Intent intent = new Intent(android.content.Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, gallery_code);
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 20 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            bimg_1 = (ImageView) findViewById(R.id.bimg_1);
            bimg_1.setImageURI(uri);

        } else if(requestCode ==21 && resultCode ==RESULT_OK){
            Uri uri = data.getData();
            bimg_2 =(ImageView) findViewById(R.id.bimg_2);
            bimg_2.setImageURI(uri);
        } else if(requestCode ==22 && resultCode ==RESULT_OK){
            Uri uri = data.getData();
            bimg_3 =(ImageView) findViewById(R.id.bimg_3);
            bimg_3.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);



        final EditText title_board = (EditText) findViewById(R.id.title_board);
        final EditText writer_name = (EditText) findViewById(R.id.writer_name);
        final EditText contents_txt = (EditText) findViewById(R.id.contents_txt);
        Button save_btn_board = (Button) findViewById(R.id.save_btn_profile);
        final Spinner spinner = (Spinner) findViewById(R.id.spin_cate);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spin_esti);
        final ImageView imgplus1 = (ImageView) findViewById(R.id.imgplus1);
        final ImageView imgplus2 =(ImageView) findViewById(R.id.imgplus2);
        final ImageView imgplus3 = (ImageView) findViewById(R.id.imgplus3);
        final ImageView bimg1 = (ImageView) findViewById(R.id.bimg_1);
        final ImageView bimg2 = (ImageView) findViewById(R.id.bimg_2);
        final ImageView bimg3 = (ImageView) findViewById(R.id.bimg_3);



        ArrayAdapter spinnerAdapter;
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(spinnerAdapter);

        ArrayAdapter spin_estiAdapter = ArrayAdapter.createFromResource(this, R.array.estimate, android.R.layout.simple_spinner_item);
        spinner1.setAdapter(spin_estiAdapter);

        Intent intent = getIntent();
        final String login_id = intent.getStringExtra("login_id");
        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        String writer = name.getString(login_id,"");
        writer_name.setText(writer);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_cate = spinner.getItemAtPosition(position).toString();
                Toast.makeText(BoardActivity.this, spin_cate+"을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spin_es = spinner1.getItemAtPosition(position).toString();
                Toast.makeText(BoardActivity.this, spin_es+"를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        imgplus1.setOnClickListener(new View.OnClickListener() { //이미지 추가 버튼 클릭시
            @Override
            public void onClick(View v) {
                boolean write = ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                if (write){
                    gallery_code = 20;
                    pickPickure();
                } else {
                    Toast.makeText(BoardActivity.this, "앨범에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgplus2.setOnClickListener(new View.OnClickListener() { //이미지 추가 버튼 클릭시
            @Override
            public void onClick(View v) {
                boolean write = ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                if (write){
                    gallery_code = 21;
                    pickPickure();
                } else {
                    Toast.makeText(BoardActivity.this, "앨범에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imgplus3.setOnClickListener(new View.OnClickListener() { //이미지 추가 버튼 클릭시
            @Override
            public void onClick(View v) {
                boolean write = ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                if (write){
                    gallery_code = 22;
                    pickPickure();
                } else {
                    Toast.makeText(BoardActivity.this, "앨범에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });






        save_btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Bitmap bitmap;
            Bitmap bitmap2;
            Bitmap bitmap3;
            String str_img1;
            String str_img2;
            String str_img3;

            if(bimg1.getDrawable() != null) {
                bitmap = ((BitmapDrawable) bimg1.getDrawable()).getBitmap();
                str_img1 =BitMapToString(bitmap);
            } else {
                str_img1 = null;
            }

            if(bimg2.getDrawable() != null){
                bitmap2 = ((BitmapDrawable) bimg2.getDrawable()).getBitmap();
                str_img2 = BitMapToString(bitmap2);
            } else{
                str_img2 = null;
            }

            if(bimg3.getDrawable() != null){
                bitmap3 = ((BitmapDrawable) bimg3.getDrawable()).getBitmap();
                str_img3 = BitMapToString(bitmap3);
            } else{
                str_img3 = null;
            }



//            Bitmap bitmap1 = ((BitmapDrawable)bimg2.getDrawable()).getBitmap();
//            Bitmap bitmap2 = ((BitmapDrawable)bimg3.getDrawable()).getBitmap();

//            String str_img2 = BitMapToString(bitmap1);
//            String str_img3 = BitMapToString(bitmap2);

                SharedPreferences list_i = getSharedPreferences("list_number_count", MODE_PRIVATE);
                SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
                SharedPreferences list_cate = getSharedPreferences("list_kind", MODE_PRIVATE);
                SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
                SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
                SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);
                SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);
                SharedPreferences img1 = getSharedPreferences("img1", MODE_PRIVATE);
                SharedPreferences img2 = getSharedPreferences("img2", MODE_PRIVATE);
                SharedPreferences img3 = getSharedPreferences("img3", MODE_PRIVATE);

                SharedPreferences.Editor edit_list_i = list_i.edit();
                SharedPreferences.Editor edit_img1 = img1.edit();
                SharedPreferences.Editor edit_img2 = img2.edit();
                SharedPreferences.Editor edit_img3 = img3.edit();
                SharedPreferences.Editor edit_login_id_check = login_id_check.edit();
                SharedPreferences.Editor edit_list_title = list_title.edit();
                SharedPreferences.Editor edit_list_cate = list_cate.edit();
                SharedPreferences.Editor edit_list_esti = list_esti.edit();
                SharedPreferences.Editor edit_list_content = list_content.edit();
                SharedPreferences.Editor edit_list_writer = list_writer.edit();
                String title = title_board.getText().toString();
                String writer = writer_name.getText().toString();
                String content = contents_txt.getText().toString();
                call_value = list_i.getInt("list_number_count", 1);

                edit_img1.putString(String.valueOf(call_value), str_img1).commit();
                edit_img2.putString(String.valueOf(call_value), str_img2).commit();
                edit_img3.putString(String.valueOf(call_value), str_img3).commit();

                edit_login_id_check.putString(String.valueOf(call_value), login_id).commit();
                edit_list_title.putString(String.valueOf(call_value), title).commit();
                edit_list_cate.putString(String.valueOf(call_value), spin_cate).commit();
                edit_list_esti.putString(String.valueOf(call_value), spin_es).commit();
                edit_list_content.putString(String.valueOf(call_value), content).commit();
                edit_list_writer.putString(String.valueOf(call_value), writer).commit();


                edit_list_i.putInt("list_number_count",call_value+1).commit();


                String[] send = new String[]{title, writer};
                Intent intent = new Intent();
                intent.putExtra("send", send);
                setResult(10, intent);
                finish();
            }
        });

    }
}
