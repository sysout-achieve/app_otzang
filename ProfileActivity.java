package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;

public class ProfileActivity extends AppCompatActivity {
    String str_proimg;
    ImageView profile_image;

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

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private  void setimg(final ImageView img_e, final int time_e){
        final Handler handler = new Handler();
        Thread th_img_eraser = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time_e);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        img_e.setImageResource(0);
                    }
                });
            }
        });
        th_img_eraser.start();
    }

    void pickPickure(){ //이미지를 앨범에서 어플로 intent를 통해 가져오는 메소드
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, 20);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode ==20 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            profile_image.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences ID = getSharedPreferences("ID", MODE_PRIVATE);
        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        final SharedPreferences.Editor edit_name = name.edit();
        SharedPreferences email = getSharedPreferences("Email", MODE_PRIVATE);
        SharedPreferences height = getSharedPreferences("height", MODE_PRIVATE);
        final SharedPreferences.Editor edit_height = height.edit();
        SharedPreferences weight = getSharedPreferences("weight", MODE_PRIVATE);
        final SharedPreferences.Editor edit_weight = weight.edit();
        SharedPreferences point = getSharedPreferences("point",MODE_PRIVATE);
        SharedPreferences profileimg = getSharedPreferences("profileimg",MODE_PRIVATE);
        final SharedPreferences.Editor edit_profileimg = profileimg.edit();

        Intent intent = getIntent();
        final String login_id = intent.getStringExtra("login_id");
        int check_po = intent.getIntExtra("check_po",0);

        TextView id_txt = (TextView) findViewById(R.id.id_txt);
        TextView email_txt = (TextView) findViewById(R.id.email_txt);
        final EditText name_txt = (EditText) findViewById(R.id.name_txt);
        final EditText person_height = (EditText) findViewById(R.id.person_height);
        final EditText person_weight = (EditText) findViewById(R.id.person_weight);
        final TextView point_n = (TextView) findViewById(R.id.point_n);
        Button save_btn_profile = (Button) findViewById(R.id.save_btn_profile);
        final ImageView point_10 = (ImageView) findViewById(R.id.point_10);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        Button picture_btn = (Button) findViewById(R.id.picture_btn);
        ImageView logout = (ImageView) findViewById(R.id.logout);

        str_proimg = profileimg.getString(login_id,"");
        if(str_proimg == ""){
            profile_image.setImageBitmap(null);
        } else{
            Bitmap bitmap = StringToBitMap(str_proimg);
            profile_image.setImageBitmap(bitmap);
        }
        id_txt.setText(ID.getString(login_id,"")); //intent로 로그인한 아이디값을 받아야하네
        email_txt.setText(email.getString(login_id,""));
        name_txt.setText(name.getString(login_id,""));
        person_height.setText(height.getString(login_id,""));
        person_weight.setText(weight.getString(login_id,""));
        point_n.setText(point.getInt(login_id,0)+ "p");

        picture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPickure();
            }
        });

        /* 포인트 올라가는 이벤트 */
        if(check_po == 1) {
            final Handler handler = new Handler();
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_point);
            point_10.startAnimation(animation);
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_point_fin);
                            point_10.startAnimation(animation1);
                            setimg(point_10, 1500);
                        }
                    });
                }
            });
            th.start();
        } else {
            setimg(point_10,0);
        }

        /*------------------------*/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                SubMainActivity subMainActivity = (SubMainActivity) SubMainActivity.submain_activity;
                subMainActivity.finish();
                finish();
            }
        });

        point_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "포인트를 이용해서 자신이 작성한 게시글의 댓글을 관리하세요", Toast.LENGTH_LONG).show();

            }
        });

        save_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap;
                String str_proimg;
                if(profile_image.getDrawable() != null){
                    bitmap = ((BitmapDrawable)profile_image.getDrawable()).getBitmap();
                    str_proimg = BitMapToString(bitmap);
                } else {
                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.profile_btn);
                    str_proimg = BitMapToString(bitmap);
                }
                String new_name = name_txt.getText().toString();
                String height = person_height.getText().toString();
                String weight = person_weight.getText().toString();

                edit_profileimg.putString(login_id, str_proimg).commit();
                edit_name.putString(login_id, new_name);
                edit_height.putString(login_id, height);
                edit_weight.putString(login_id, weight);
                edit_name.commit();
                edit_height.commit();
                edit_weight.commit();

                Toast.makeText(ProfileActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
