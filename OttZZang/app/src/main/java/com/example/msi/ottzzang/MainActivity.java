package com.example.msi.ottzzang;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    void requirePermission(){//권한 요청 메소드
        String [] permissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.READ_CALENDAR,Manifest.permission.WRITE_CALENDAR,Manifest.permission.INTERNET};
        ArrayList<String> listPermissionsNeeded = new ArrayList<>();

        for(String permission :permissions){
            if(ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_DENIED){
                listPermissionsNeeded.add(permission);  //권한이 허가가 안됐을 경우 요청할 권한을 모집
            }
        }
        if(!listPermissionsNeeded.isEmpty()){        //권한을 요청함
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requirePermission(); //메소드 실행

        final SharedPreferences ID = getSharedPreferences("ID", MODE_PRIVATE);
//        SharedPreferences.Editor ID_editor = ID.edit();
        final SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
//        SharedPreferences.Editor edit_name = name.edit();
        final SharedPreferences password = getSharedPreferences("password", MODE_PRIVATE);
//        SharedPreferences.Editor edit_passwrod = password.edit();
        SharedPreferences email = getSharedPreferences("Email", MODE_PRIVATE);
//        SharedPreferences.Editor edit_email = email.edit();

        final EditText id = (EditText) findViewById(R.id.id);//
        final EditText password_txt = (EditText) findViewById(R.id.password);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button join_btn = (Button) findViewById(R.id.join_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ch_ID = id.getText().toString();
                String ch_password = password_txt.getText().toString();

                if(ID.getString(ch_ID,"noid").equals("noid")) {
                    Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                } else if( password.getString(ch_ID,"nopass").equals(ch_password)) {
                    String name1 = name.getString(ch_ID, "");

                    Intent intent = IntentBuilder.build(v.getContext(), SubMainActivity.class);
                    intent.putExtra("login_id", ch_ID);
                    intent.putExtra("name1", name1);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivityForResult(intent,010);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 010 && resultCode == 010){
            Toast.makeText(MainActivity.this, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
