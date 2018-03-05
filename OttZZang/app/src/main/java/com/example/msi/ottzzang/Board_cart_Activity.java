package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Board_cart_Activity extends AppCompatActivity {
    int cartnum;
    int i;          // 찜목록 누른 횟수 파악
    int re;         // 리플 갯수 확인
    int get_number;

    TextView reply_num;
    TextView get_heart;

    SharedPreferences size;
    SharedPreferences cart_like;
    SharedPreferences get_reply_num;

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
    protected void onResume() {
        super.onResume();
        re = size.getInt(get_number+"size", 0);
        reply_num.setText(""+re);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_cart);

        final EditText title_board_rd = (EditText) findViewById(R.id.title_board_rd);
        final EditText writer_name_rd = (EditText) findViewById(R.id.writer_name_rd);
        EditText esti_txt_rd = (EditText) findViewById(R.id.esti_txt_rd);
        EditText contents_txt_rd = (EditText) findViewById(R.id.contents_txt_rd);
        ImageView img1_rd = (ImageView) findViewById(R.id.img1_rd);
        TextView cate_txt_rd = (TextView) findViewById(R.id.txt_cate_rd);
        ImageView cart_img = (ImageView) findViewById(R.id.cart_img);
        ImageView more_img = (ImageView) findViewById(R.id.more_img);
        get_heart = (TextView) findViewById(R.id.get_heart_rd);
        ImageView reply_btn = (ImageView) findViewById(R.id.reply_btn_rd);
        reply_num = (TextView) findViewById(R.id.get_re_rd);

        Intent intent = getIntent();
        get_number = intent.getIntExtra("get_number",1);
        final String login_id = intent.getStringExtra("login_id");
        SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
        SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
        SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
        SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);
        SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);
        SharedPreferences img1 = getSharedPreferences("img1", MODE_PRIVATE);
        SharedPreferences list_cate = getSharedPreferences("list_kind", MODE_PRIVATE);
        cart_like = getSharedPreferences("cart_like", MODE_PRIVATE);
        get_reply_num = getSharedPreferences("total",MODE_PRIVATE);
        size = getSharedPreferences("size", MODE_PRIVATE);


        SharedPreferences cart_on = getSharedPreferences(String.valueOf(get_number),MODE_PRIVATE);
        cartnum = cart_on.getInt(login_id, 0);
        if(cartnum == 0){
            cart_img.setImageResource(R.drawable.like);
        } else if (cartnum == 1){
            cart_img.setImageResource(R.drawable.like_clicked);
        }

        String str_img1 = img1.getString(String.valueOf(get_number),"");
        if(str_img1!=""){
            Bitmap bitmap = StringToBitMap(str_img1);
            Bitmap resize = Bitmap.createScaledBitmap(bitmap, 600, 700,true);
            img1_rd.setImageBitmap(resize);
        }

        i = cart_like.getInt(String.valueOf(get_number),0);

        get_heart.setText(""+(cart_like.getInt(String.valueOf(get_number),0)));
        cate_txt_rd.setText(list_cate.getString(String.valueOf(get_number),""));
        title_board_rd.setText(list_title.getString(String.valueOf(get_number),""));
        esti_txt_rd.setText(list_esti.getString(String.valueOf(get_number),""));
        contents_txt_rd.setText(list_content.getString(String.valueOf(get_number),"게시글이 삭제되어 내용을 확인할 수 없습니다."));
        writer_name_rd.setText(list_writer.getString(String.valueOf(get_number),""));
        final String log_id = login_id_check.getString(String.valueOf(get_number),"no_writer");
//        String[] renew = intent.getStringArrayExtra("renew");
//        title_board_modi.setText(renew[0]);
//        writer_name_modi.setText(renew[1]);

        more_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Viewimg_Activity.class);
                intent.putExtra("checked", get_number);
                startActivity(intent);
            }
        });

        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReplyActivity.class);
                intent.putExtra("checked", get_number);
                intent.putExtra("login_id", login_id);
                startActivity(intent);
            }
        });

        cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cartnum == 0){   //찜목록에 저장 확인하는 조건문
                    // 아이디 별로 찜목록에 저장된 게시글인지 확인하고 하트색깔로 구분
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_heart);
                    final ImageView cart_btn = (ImageView) findViewById(R.id.cart_img);
                    cart_btn.setAnimation(animation);
                    cart_btn.setImageResource(R.drawable.like_clicked);

                    cartnum = 1;
                    SharedPreferences cart_on = getSharedPreferences(String.valueOf(get_number),MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_on = cart_on.edit();
                    edit_cart_on.putInt(login_id,cartnum);
                    edit_cart_on.commit();
                    /*찜 한 횟수 표시*/
                    SharedPreferences cart_like = getSharedPreferences("cart_like",MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_like = cart_like.edit();

                    i = cart_like.getInt(String.valueOf(get_number),0);
                    i = i+1;
                    edit_cart_like.putInt(String.valueOf(get_number),i).commit();
                    get_heart.setText(""+i);

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

                      /* 찜 눌렀을 때 찜 한 횟수 표시*/
                    SharedPreferences cart_like = getSharedPreferences("cart_like",MODE_PRIVATE);
                    SharedPreferences.Editor edit_cart_like = cart_like.edit();

                    i = cart_like.getInt(String.valueOf(get_number),0);
                    i = i-1;
                    edit_cart_like.putInt(String.valueOf(get_number),i).commit();
                    get_heart.setText(""+i);
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
