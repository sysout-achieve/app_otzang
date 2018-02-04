package com.example.msi.ottzzang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

        final EditText id = (EditText) findViewById(R.id.id);//
        final EditText password = (EditText) findViewById(R.id.password);
        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button join_btn = (Button) findViewById(R.id.join_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals("1234")) {
                    String name1 = id.getText().toString();


                    Intent intent = IntentBuilder.build(v.getContext(), SubMainActivity.class);
                    //Intent intent = new Intent(v.getContext(), SubMainActivity.class);
                    intent.putExtra("name1", name1);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(MainActivity.this, "아이디 혹은 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
