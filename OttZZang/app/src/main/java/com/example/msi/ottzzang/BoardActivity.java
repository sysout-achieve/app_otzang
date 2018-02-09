package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BoardActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);



        final EditText title_board = (EditText) findViewById(R.id.title_board);
        final EditText writer_name = (EditText) findViewById(R.id.writer_name);
        Button save_btn_board = (Button) findViewById(R.id.save_btn_profile);

        Intent intent = getIntent();
        String login_id = intent.getStringExtra("login_id");
        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        String writer = name.getString(login_id,"");
        writer_name.setText(writer);


        save_btn_board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_board.getText().toString();
                String writer = writer_name.getText().toString();

                String[] send = new String[]{title, writer};
                Intent intent = new Intent();
                intent.putExtra("send", send);
                setResult(10, intent);
                finish();
            }
        });

    }
}
