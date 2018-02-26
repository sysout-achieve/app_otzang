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

public class Board_modify_Activity extends AppCompatActivity {
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
        setContentView(R.layout.activity_board_modification);

        final EditText title_board_modi = (EditText) findViewById(R.id.title_board_modi);
        final EditText writer_name_modi = (EditText) findViewById(R.id.writer_name_modi);
        final EditText esti_txt_modi = (EditText) findViewById(R.id.esti_txt_modi);
        final EditText contents_txt_modi = (EditText) findViewById(R.id.contents_txt_modi);
        Button save_btn_modi = (Button) findViewById(R.id.save_btn_profile);
        Button delete = (Button) findViewById(R.id.delete);
        ImageView cart_btn = (ImageView) findViewById(R.id.cart_btn);
        ImageView img1_modi = (ImageView) findViewById(R.id.img1_modi);
        TextView cate_txt_modi = (TextView) findViewById(R.id.cate_txt_modi);


        SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
        SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
        SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
        SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);
        SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);
        SharedPreferences list_cate = getSharedPreferences("list_kind", MODE_PRIVATE);
        SharedPreferences img1 = getSharedPreferences("img1", MODE_PRIVATE);
        SharedPreferences img2 = getSharedPreferences("img2", MODE_PRIVATE);
        SharedPreferences img3 = getSharedPreferences("img3", MODE_PRIVATE);


        Intent intent = getIntent();

        final String login_id = intent.getStringExtra("login_id");
        final int checked = intent.getIntExtra("get_number",1);
        title_board_modi.setText(list_title.getString(String.valueOf(checked),""));
        esti_txt_modi.setText(list_esti.getString(String.valueOf(checked),""));
        contents_txt_modi.setText(list_content.getString(String.valueOf(checked),""));
        writer_name_modi.setText(list_writer.getString(String.valueOf(checked),""));
        cate_txt_modi.setText(list_cate.getString(String.valueOf(checked),""));
        String str_img1 = img1.getString(String.valueOf(checked),"");
        if(str_img1!=""){
            Bitmap bitmap = StringToBitMap(str_img1);
            Bitmap resize = Bitmap.createScaledBitmap(bitmap, 600, 700,true);
            img1_modi.setImageBitmap(resize);
        }



        final String log_id = login_id_check.getString(String.valueOf(checked),"no_writer");

        //로그인 아이디가 장바구니에 저장했는지 확인

        SharedPreferences cart_on = getSharedPreferences(String.valueOf(checked),MODE_PRIVATE);
        SharedPreferences.Editor edit_cart_on = cart_on.edit();
        cartnum = cart_on.getInt(login_id, 0);
        if(cartnum == 0){
            cart_btn.setImageResource(R.drawable.like);
        } else if (cartnum == 1){
            cart_btn.setImageResource(R.drawable.like_clicked);
        }


        save_btn_modi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_id.equals(log_id)){
                    SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
                    SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
                    SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
                    SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);

                    SharedPreferences.Editor edit_list_title = list_title.edit();
                    SharedPreferences.Editor edit_list_esti = list_esti.edit();
                    SharedPreferences.Editor edit_list_content = list_content.edit();
                    SharedPreferences.Editor edit_list_writer = list_writer.edit();


                    String title = title_board_modi.getText().toString();
                    String writer = writer_name_modi.getText().toString();
                    String esti = esti_txt_modi.getText().toString();
                    String content = contents_txt_modi.getText().toString();

                    edit_list_title.putString(String.valueOf(checked), title);
                    edit_list_esti.putString(String.valueOf(checked), esti);
                    edit_list_content.putString(String.valueOf(checked), content);
                    edit_list_writer.putString(String.valueOf(checked), writer);
                    edit_list_title.commit();
                    edit_list_esti.commit();
                    edit_list_content.commit();
                    edit_list_writer.commit();
                    String[] resend = new String[]{title, writer};
                    Intent intent = new Intent();
                    intent.putExtra("resend", resend);
                    setResult(20, intent);
                    finish();
                } else {
                    Toast.makeText(Board_modify_Activity.this, "수정할 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences list_i = getSharedPreferences("list_number_count", MODE_PRIVATE);
                SharedPreferences list_name = getSharedPreferences("list_name", MODE_PRIVATE);
                SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
                SharedPreferences list_review = getSharedPreferences("list_review", MODE_PRIVATE);
                SharedPreferences list_write = getSharedPreferences("list_write", MODE_PRIVATE);

                SharedPreferences.Editor edit_list_i = list_i.edit();
                SharedPreferences.Editor edit_list_name = list_name.edit();
                SharedPreferences.Editor edit_list_esti = list_esti.edit();
                SharedPreferences.Editor edit_list_review = list_review.edit();
                SharedPreferences.Editor edit_list_write = list_write.edit();

                edit_list_name.remove(String.valueOf(checked));
                edit_list_esti.remove(String.valueOf(checked));
                edit_list_review.remove(String.valueOf(checked));
                edit_list_write.remove(String.valueOf(checked));

                edit_list_name.commit();
                edit_list_esti.commit();
                edit_list_review.commit();
                edit_list_write.commit();

                Intent intent = new Intent();
                intent.putExtra("check", checked);
                setResult(40, intent);
                finish();
            }
        });

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(cartnum == 0){   //찜목록에 저장 확인하는 조건문
                    // 아이디 별로 찜목록에 저장된 게시글인지 확인하고 하트색깔로 구분
                    ImageView cart_btn = (ImageView) findViewById(R.id.cart_btn);
                    cart_btn.setImageResource(R.drawable.like_clicked);
                    cartnum = 1;
                    SharedPreferences cart_on = getSharedPreferences(String.valueOf(checked),MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_on = cart_on.edit();
                    edit_cart_on.putInt(login_id,cartnum);
                    edit_cart_on.commit();
                    /*찜 한 횟수 표시*/
                    SharedPreferences cart_like = getSharedPreferences("cart_like",MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_like = cart_like.edit();

                    int i = cart_like.getInt(String.valueOf(checked),0);
                    i = i+1;
                    edit_cart_like.putInt(String.valueOf(checked),i).commit();

                    //찜목록 Sharedpreference 저장(각 ID에 따라 다르게 저장해야함)
                    String title = title_board_modi.getText().toString();
                    SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);

                    SharedPreferences.Editor edit_list_cart = list_id.edit();

                    edit_list_cart.putString(String.valueOf(checked), title);

                    edit_list_cart.commit();

                    //찜목록에 저장하는 intent
                    String cart = new String(title);
                    Intent intent = new Intent();
                    intent.putExtra("cart", cart);
                    intent.putExtra("checked",checked);
                    setResult(22, intent);
                    Toast.makeText(Board_modify_Activity.this, "찜 목록에 담았습니다.", Toast.LENGTH_SHORT).show();
                } else if (cartnum == 1){
                    ImageView cart_btn = (ImageView) findViewById(R.id.cart_btn);
                    cart_btn.setImageResource(R.drawable.like);
                    cartnum = 0;
                    SharedPreferences cart_on = getSharedPreferences(String.valueOf(checked),MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_on = cart_on.edit();
                    edit_cart_on.putInt(login_id,cartnum);
                    edit_cart_on.commit();
                    /* 찜 눌렀을 때 찜 한 횟수 표시*/
                    SharedPreferences cart_like = getSharedPreferences("cart_like",MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_like = cart_like.edit();

                    int i = cart_like.getInt(String.valueOf(checked),0);
                    i = i-1;
                    edit_cart_like.putInt(String.valueOf(checked),i).commit();

                    //찜목록 Sharedpreference에서 제거(각 ID에 따라 다르게 저장해야함)
                    SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);
                    SharedPreferences.Editor edit_list_cart = list_id.edit();
                    edit_list_cart.remove(String.valueOf(checked));
                    edit_list_cart.commit();

                    Intent intent = new Intent();
                    intent.putExtra("checked",checked);
                    setResult(23, intent);
                    Toast.makeText(Board_modify_Activity.this, "찜 목록에서 제거했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
