package com.example.msi.ottzzang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdvertisementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);

        final TextView link_ad = (TextView) findViewById(R.id.link_ad);
        TextView text_suggest = (TextView) findViewById(R.id.text_suggest);
        ImageView img_suggest = (ImageView) findViewById(R.id.img_suggest);


        text_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = link_ad.getText().toString();
                Intent intent_link = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent_link);
            }
        });

        img_suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = link_ad.getText().toString();
                Intent intent_link = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent_link);
            }
        });
        link_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = link_ad.getText().toString();
                Intent intent_link = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent_link);
            }
        });

    }
}

