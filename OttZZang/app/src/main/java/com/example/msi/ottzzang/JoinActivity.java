package com.example.msi.ottzzang;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class JoinActivity extends AppCompatActivity {
        String ID_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        final EditText input_name = (EditText)findViewById(R.id.input_name);
        final EditText input_password = (EditText)findViewById(R.id.input_password);
        final EditText input_email = (EditText)findViewById(R.id.input_email);
        EditText input_check_answer = (EditText)findViewById(R.id.input_check_answer);
        Button check_id_btn = (Button)findViewById(R.id.check_id_btn);
        Button check_em_btn = (Button)findViewById(R.id.check_em_btn);
        Button check_pass_btn = (Button)findViewById(R.id.check_pass_btn);
        Button join_finish_btn = (Button)findViewById(R.id.join_finish_btn);

        final SharedPreferences ID = getSharedPreferences("ID", MODE_PRIVATE);
        final SharedPreferences.Editor edit_ID = ID.edit();
        edit_ID.putString("gungun", "gungun");
        edit_ID.commit();

        check_id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input_id = (EditText)findViewById(R.id.input_id);
                if(ID.getString(input_id.getText().toString(),"noID").equals("noID")){ //ID라는 SharedPreference안에 값이 있는지 확인하기 위한 절차가 필요
                    Toast.makeText(JoinActivity.this, "사용 가능한 ID입니다.", Toast.LENGTH_SHORT).show();
                    ID_check = input_id.getText().toString();
                } else{
                    Toast.makeText(JoinActivity.this, "이미 사용중인 ID 입니다. 다른 ID를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        join_finish_btn.setOnClickListener(new View.OnClickListener() { //빈 항목만 체크함 중복 확인 아직!!!!!
            @Override
            public void onClick(View v) {
                EditText input_id = (EditText)findViewById(R.id.input_id);
//                String correct;
//                correct = input_id.getText().toString();
               if(input_id.getText().toString().equals("") || input_name.getText().toString().equals("") || input_password.getText().toString().equals("") ||input_email.getText().toString().equals("")) {
                   Toast.makeText(JoinActivity.this, "빈 항목을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
               } else if (input_id.getText().toString().equals(ID_check)){
                   EditText input_name = (EditText) findViewById(R.id.input_name);
                   EditText input_password = (EditText) findViewById(R.id.input_password);
                   EditText input_email = (EditText) findViewById(R.id.input_email);
                   EditText input_check_answer = (EditText) findViewById(R.id.input_check_answer);

                   SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
                   SharedPreferences.Editor edit_name = name.edit();
                   SharedPreferences password = getSharedPreferences("password", MODE_PRIVATE);
                   SharedPreferences.Editor edit_passwrod = password.edit();
                   SharedPreferences email = getSharedPreferences("Email", MODE_PRIVATE);
                   SharedPreferences.Editor edit_email = email.edit();

                   edit_ID.putString(input_id.getText().toString(), input_id.getText().toString());
                   edit_name.putString(input_id.getText().toString(), input_name.getText().toString());
                   edit_passwrod.putString(input_id.getText().toString(), input_password.getText().toString());
                   edit_email.putString(input_id.getText().toString(), input_email.getText().toString());
                   edit_ID.commit();
                   edit_name.commit();
                   edit_passwrod.commit();
                   edit_email.commit();
                   Intent intent = new Intent();
                   setResult( 010,intent);
                   finish();
               } else {
                   Toast.makeText(JoinActivity.this, "ID 중복확인을 눌러주세요",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }       // onCreate of End.

}
