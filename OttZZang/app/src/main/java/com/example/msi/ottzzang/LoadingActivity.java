package com.example.msi.ottzzang;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingActivity extends AppCompatActivity {

    private int progressStatus = 0;
    TextView per_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final TextView txt_loading = (TextView) findViewById(R.id.txt_loading);
        per_loading = (TextView) findViewById(R.id.per_loading);

        progressStatus = 0;

        final Handler handler = new Handler();

        Thread th_pro = new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100){
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            if (progressStatus == 1) {
                                txt_loading.setText("데이터 초기화...");
                            } else if(progressStatus == 50){
                                txt_loading.setText("업데이트 확인 중...");
                            }
                            per_loading.setText(progressStatus+" %");
                        }
                    });
                   try{
                       if(progressStatus == 23 || progressStatus == 57 || progressStatus == 99){
                           Thread.sleep(500);
                       }
                       Thread.sleep(50);
                   } catch (Exception e){

                   }
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            }
        });
        th_pro.start();
    }


}

