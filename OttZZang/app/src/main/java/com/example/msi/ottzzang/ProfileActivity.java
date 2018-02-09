package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

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

        Intent intent = getIntent();
        final String login_id = intent.getStringExtra("login_id");

        TextView id_txt = (TextView) findViewById(R.id.id_txt);
        TextView email_txt = (TextView) findViewById(R.id.email_txt);
        final EditText name_txt = (EditText) findViewById(R.id.name_txt);
        final EditText person_height = (EditText) findViewById(R.id.person_height);
        final EditText person_weight = (EditText) findViewById(R.id.person_weight);
        Button save_btn_profile = (Button) findViewById(R.id.save_btn_profile);

        id_txt.setText(ID.getString(login_id,"")); //intent로 로그인한 아이디값을 받아야하네
        email_txt.setText(email.getString(login_id,""));
        name_txt.setText(name.getString(login_id,""));
        person_height.setText(height.getString(login_id,""));
        person_weight.setText(weight.getString(login_id,""));

        save_btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_name = name_txt.getText().toString();
                String height = person_height.getText().toString();
                String weight = person_weight.getText().toString();

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
