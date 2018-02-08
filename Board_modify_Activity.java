package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Board_modify_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_modification);

        final EditText title_board_modi = (EditText) findViewById(R.id.title_board_modi);
        final EditText writer_name_modi = (EditText) findViewById(R.id.writer_name_modi);
        Button save_btn_modi = (Button) findViewById(R.id.save_btn_profile);
        ImageView cart_btn = (ImageView) findViewById(R.id.cart_btn);
        SharedPreferences Checked_board = getSharedPreferences("Checked_board", MODE_PRIVATE);
        SharedPreferences.Editor edit_check_board = Checked_board.edit();
        int checked;



        Intent intent = getIntent();
        String[] renew = intent.getStringArrayExtra("renew");
        String login_id = intent.getStringExtra("login_id");
        checked = intent.getIntExtra("checked",0);
        title_board_modi.setText(renew[0]);
        writer_name_modi.setText(renew[1]);



        save_btn_modi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = title_board_modi.getText().toString();
                String writer = writer_name_modi.getText().toString();

                String[] resend = new String[]{title, writer};
                Intent intent = new Intent();
                intent.putExtra("resend", resend);
                setResult(20, intent);
                finish();
            }
        });

        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = title_board_modi.getText().toString();
                String cart = new String(title);
                Intent intent = new Intent();
                intent.putExtra("cart", cart);
                setResult(22, intent);
                finish();
            }
        });

    }
}
