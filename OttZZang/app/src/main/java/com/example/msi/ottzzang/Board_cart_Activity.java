package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Board_cart_Activity extends AppCompatActivity {
    int cartnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_cart);

        final EditText title_board_rd = (EditText) findViewById(R.id.title_board_rd);
        final EditText writer_name_rd = (EditText) findViewById(R.id.writer_name_rd);
        EditText esti_txt_rd = (EditText) findViewById(R.id.esti_txt_rd);
        EditText contents_txt_rd = (EditText) findViewById(R.id.contents_txt_rd);
        Button save_btn_modi = (Button) findViewById(R.id.save_btn_profile);
        ImageView cart_img = (ImageView) findViewById(R.id.cart_img);

        Intent intent = getIntent();
        final int get_number = intent.getIntExtra("get_number",1);
        final String login_id = intent.getStringExtra("login_id");
        SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
        SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
        SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
        SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);
        SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);

        SharedPreferences cart_on = getSharedPreferences(String.valueOf(get_number),MODE_PRIVATE);
        cartnum = cart_on.getInt(login_id, 0);
        if(cartnum == 0){
            cart_img.setImageResource(R.drawable.like);
        } else if (cartnum == 1){
            cart_img.setImageResource(R.drawable.like_clicked);
        }

        title_board_rd.setText(list_title.getString(String.valueOf(get_number),""));
        esti_txt_rd.setText(list_esti.getString(String.valueOf(get_number),""));
        contents_txt_rd.setText(list_content.getString(String.valueOf(get_number),""));
        writer_name_rd.setText(list_writer.getString(String.valueOf(get_number),""));
        final String log_id = login_id_check.getString(String.valueOf(get_number),"no_writer");
//        String[] renew = intent.getStringArrayExtra("renew");
//        title_board_modi.setText(renew[0]);
//        writer_name_modi.setText(renew[1]);


        cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cartnum == 0){   //찜목록에 저장 확인하는 조건문
                    // 아이디 별로 찜목록에 저장된 게시글인지 확인하고 하트색깔로 구분
                    ImageView cart_img = (ImageView) findViewById(R.id.cart_img);
                    cart_img.setImageResource(R.drawable.like_clicked);
                    cartnum = 1;
                    SharedPreferences cart_on = getSharedPreferences(String.valueOf(cartnum),MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_on = cart_on.edit();
                    edit_cart_on.putInt(login_id,cartnum);
                    edit_cart_on.commit();

                    //찜목록 Sharedpreference 저장(각 ID에 따라 다르게 저장해야함)
                    String title = title_board_rd.getText().toString();
                    SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);

                    SharedPreferences.Editor edit_list_cart = list_id.edit();

                    edit_list_cart.putString(String.valueOf(get_number), title);

                    edit_list_cart.commit();

                    //찜목록에 저장하는 intent
                    String cart = new String(title);
                    Intent intent = new Intent();
                    intent.putExtra("cart", cart);
                    intent.putExtra("checked",get_number);
                    setResult(22, intent);
                    Toast.makeText(Board_cart_Activity.this, "찜 목록에 담았습니다.", Toast.LENGTH_SHORT).show();
                } else if (cartnum == 1){
                    ImageView cart_img = (ImageView) findViewById(R.id.cart_img);
                    cart_img.setImageResource(R.drawable.like);
                    cartnum = 0;
                    SharedPreferences cart_on = getSharedPreferences(String.valueOf(get_number),MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_on = cart_on.edit();
                    edit_cart_on.putInt(login_id,cartnum);
                    edit_cart_on.commit();

                    //찜목록 Sharedpreference에서 제거(각 ID에 따라 다르게 저장해야함)
                    SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);
                    SharedPreferences.Editor edit_list_cart = list_id.edit();
                    edit_list_cart.remove(String.valueOf(get_number));
                    edit_list_cart.commit();

                    Intent intent = new Intent();
                    intent.putExtra("checked",get_number);
                    setResult(23, intent);
                    Toast.makeText(Board_cart_Activity.this, "찜 목록에서 제거했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
