package com.example.msi.ottzzang;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Board_modify_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_modification);

        final EditText title_board_modi = (EditText) findViewById(R.id.title_board_modi);
        final EditText writer_name_modi = (EditText) findViewById(R.id.writer_name_modi);
        final EditText esti_txt_modi = (EditText) findViewById(R.id.esti_txt_modi);
        final EditText contents_txt_modi = (EditText) findViewById(R.id.contents_txt_modi);
        Button save_btn_modi = (Button) findViewById(R.id.save_btn_profile);
        ImageView cart_btn = (ImageView) findViewById(R.id.cart_btn);



//        SharedPreferences Checked_board = getSharedPreferences("Checked_board", MODE_PRIVATE);
//        SharedPreferences.Editor edit_check_board = Checked_board.edit();
        SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
        SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
        SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
        SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);
        SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);

        Intent intent = getIntent();
//        String[] renew = intent.getStringArrayExtra("renew");

        final String login_id = intent.getStringExtra("login_id");
        final int checked = intent.getIntExtra("checked",11);
        title_board_modi.setText(list_title.getString(String.valueOf(checked),""));
        esti_txt_modi.setText(list_esti.getString(String.valueOf(checked),""));
        contents_txt_modi.setText(list_content.getString(String.valueOf(checked),""));
        writer_name_modi.setText(list_writer.getString(String.valueOf(checked),""));
        final String log_id = login_id_check.getString(String.valueOf(checked),"no_writer");


//        title_board_modi.setText(renew[0]);
//        writer_name_modi.setText(renew[1]);



        save_btn_modi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_id.equals(log_id)){
                    SharedPreferences list_title = getSharedPreferences("list_name", MODE_PRIVATE);
                    SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
                    SharedPreferences list_content = getSharedPreferences("list_review", MODE_PRIVATE);
                    SharedPreferences list_writer = getSharedPreferences("list_write", MODE_PRIVATE);
                    SharedPreferences.Editor edit_list_title = list_title.edit();
                    SharedPreferences.Editor edit_list_esti = list_esti.edit();
                    SharedPreferences.Editor edit_list_content = list_content.edit();
                    SharedPreferences.Editor edit_list_writer = list_writer.edit();

                    String title = title_board_modi.getText().toString();
                    String writer = writer_name_modi.getText().toString();
                    String esti = esti_txt_modi.getText().toString();
                    String content = contents_txt_modi.getText().toString();

                    edit_list_title.putString(String.valueOf(checked), title);
                    edit_list_esti.putString(String.valueOf(checked), esti);
                    edit_list_content.putString(String.valueOf(checked), content);
                    edit_list_writer.putString(String.valueOf(checked), writer);
                    edit_list_title.commit();
                    edit_list_esti.commit();
                    edit_list_content.commit();
                    edit_list_writer.commit();
                    String[] resend = new String[]{title, writer};
                    Intent intent = new Intent();
                    intent.putExtra("resend", resend);
                    setResult(20, intent);
                    finish();
                } else {
                    Toast.makeText(Board_modify_Activity.this, "수정할 권한이 없습니다.", Toast.LENGTH_SHORT).show();
                }
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
