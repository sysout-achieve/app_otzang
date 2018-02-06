package com.example.msi.ottzzang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Board_cart_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_cart);

        final EditText title_board_modi = (EditText) findViewById(R.id.title_board_modi);
        final EditText writer_name_modi = (EditText) findViewById(R.id.writer_name_modi);
        Button save_btn_modi = (Button) findViewById(R.id.save_btn_modi);

        Intent intent = getIntent();
        String[] renew = intent.getStringArrayExtra("renew");
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

    }
}
