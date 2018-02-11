package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BoardActivity extends AppCompatActivity {
    int call_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);



        final EditText title_board = (EditText) findViewById(R.id.title_board);
        final EditText writer_name = (EditText) findViewById(R.id.writer_name);
        final EditText esti_txt = (EditText) findViewById(R.id.esti_txt);
        final EditText contents_txt = (EditText) findViewById(R.id.contents_txt);
        Button save_btn_board = (Button) findViewById(R.id.save_btn_profile);

        Intent intent = getIntent();
        final String login_id = intent.getStringExtra("login_id");
        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        String writer = name.getString(login_id,"");
        writer_name.setText(writer);







        save_btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences list_i = getSharedPreferences("list_i", MODE_PRIVATE);
                SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
                SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
                SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
                SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);
                SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);
                SharedPreferences.Editor edit_login_id_check = login_id_check.edit();
                SharedPreferences.Editor edit_list_title = list_title.edit();
                SharedPreferences.Editor edit_list_esti = list_esti.edit();
                SharedPreferences.Editor edit_list_content = list_content.edit();
                SharedPreferences.Editor edit_list_writer = list_writer.edit();
                String title = title_board.getText().toString();
                String writer = writer_name.getText().toString();
                String esti = esti_txt.getText().toString();
                String content = contents_txt.getText().toString();
                call_value = list_i.getInt("list_number_i", 11);

                edit_login_id_check.putString(String.valueOf(call_value), login_id);
                edit_list_title.putString(String.valueOf(call_value), title);
                edit_list_esti.putString(String.valueOf(call_value), esti);
                edit_list_content.putString(String.valueOf(call_value), content);
                edit_list_writer.putString(String.valueOf(call_value), writer);
                edit_login_id_check.commit();
                edit_list_title.commit();
                edit_list_esti.commit();
                edit_list_content.commit();
                edit_list_writer.commit();

                String[] send = new String[]{title, writer};
                Intent intent = new Intent();
                intent.putExtra("send", send);
                setResult(10, intent);
                finish();
            }
        });
    }
}
