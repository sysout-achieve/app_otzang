package com.example.msi.ottzzang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class OuterActivity extends AppCompatActivity {
            int checked1;
            int outer_times = 0;
            int restart_i =0;
            int outerrequest_code = 1;
            int outermake_code = 5;

            ArrayList<OuterItem> data = new ArrayList<>();
            ArrayList<Integer> list_num = new ArrayList<>();
            outerAdapter adapter = new outerAdapter(this, R.layout.listview_outer, data);

            String login_id;
            int total_i;

            Handler handler_poing_img;

    ImageView tp1;
    ImageView tp2;
    ImageView tp3;
    ImageView tp4;
    ImageView tp5;
    ImageView tp6;
    ImageView tp7;
    ImageView tp8;
    ImageView tp9;
    ImageView tp10;
    ImageView tp11;
    ImageView tp12;
    ImageView tp13;



    private  void setimg(final ImageView img_e, final int time_e){
        final Handler handler = new Handler();
        Thread th_img_eraser = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time_e);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        img_e.setImageResource(0);
                    }
                });
            }
        });
        th_img_eraser.start();
    }

    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(OuterActivity.this);
        builder.setTitle("포인트 획득하셨습니다.");
        builder.setMessage("10p");
        builder.setNegativeButton("하던 일 하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"포인트는 추가됩니다.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("확인하러 가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"확인!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("login_id",login_id);
                intent.putExtra("check_po", 1);
                startActivity(intent);
            }
        });
        builder.show();
    }

    private void clickpoint(ImageView img_point){
        img_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences point = getSharedPreferences("point",MODE_PRIVATE);
                SharedPreferences.Editor edit_point = point.edit();
                edit_point.putInt(login_id,point.getInt(login_id,0)+10).commit();
                dialog();
            }
        });
    }

    private void openimg(final ImageView i1, final ImageView i2, final int time) {
        handler_poing_img = new Handler();
        Thread th_point = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(time);
                } catch (Exception e) {

                }
                handler_poing_img.post(new Runnable() {
                    @Override
                    public void run() {
                        i1.setImageResource(0);
                        i2.setImageResource(R.drawable.transparent);
                    }
                });
            }
        });
        th_point.start();
    }

    public Bitmap StringToBitMap(String encodedString){ // 스트링으로 받은 이미지를 비트맵으로 다시 변환
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(restart_i == 8){     //OuterActivity 에 8번 들어올 경우 아우터에 관한 광고를 만들어 줌
            Toast.makeText(OuterActivity.this, "아우터에 관심이 많으시네요~ 이 제품들은 어떠신가요?", Toast.LENGTH_SHORT).show();
            Intent intent_ad = new Intent(OuterActivity.this, AdvertisementActivity.class);
            startActivity(intent_ad);
            restart_i =0;
        } else {
            restart_i=restart_i+1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer);

          /*Stetho----------------------------*/
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        /* Constraint animation event*/

        /* 시간이 지나고 날파리가 날아다님
        * 날파리를 잡으면 이벤트 다이얼로그와 함께 포인트를 받는다 */

final ImageView point_img1 = (ImageView) findViewById(R.id.point_img1);
final ImageView point_img2 = (ImageView) findViewById(R.id.point_img2);
        tp1 = (ImageView) findViewById(R.id.tp_img1);
        tp2 = (ImageView) findViewById(R.id.tp_img2);
        tp3 = (ImageView) findViewById(R.id.tp_img3);
        tp4 = (ImageView) findViewById(R.id.tp_img4);
        tp5 = (ImageView) findViewById(R.id.tp_img5);
        tp6 = (ImageView) findViewById(R.id.tp_img6);
        tp7 = (ImageView) findViewById(R.id.tp_img7);
        tp8 = (ImageView) findViewById(R.id.tp_img8);
        tp9 = (ImageView) findViewById(R.id.tp_img9);
        tp10 = (ImageView) findViewById(R.id.tp_img10);
        tp11 = (ImageView) findViewById(R.id.tp_img11);
        tp12 = (ImageView) findViewById(R.id.tp_img12);
        tp13 = (ImageView) findViewById(R.id.tp_img13);

        /* 이미지에 맞춰서 클릭 할 수 있는 이미지 뷰 생성 및 삭제*/
        /* 이미지 생성되면서 x축 ++ */

                openimg(tp1, tp1,5300);
                openimg(tp1, tp2,5600);
                openimg(tp2, tp3,5900);
                openimg(tp3, tp4,6200);
                openimg(tp4, tp5,6500);
        /*이미지가 돌아가는 부분*/
                openimg(tp5, tp6,6800);
                openimg(tp6, tp7,7050);
                openimg(tp7, tp8,7300);
                openimg(tp8, tp9,7600);
                openimg(tp9, tp10,7900);

        /* 이미지 마지막으로 가면서 사라지는 부분*/
                openimg(tp10, tp11,8300);
                openimg(tp11, tp12,8700);
                openimg(tp12, tp13,9000);

                setimg(tp13,9200);
                setimg(point_img2,9200);
        clickpoint(tp1);
        clickpoint(tp2);
        clickpoint(tp3);
        clickpoint(tp4);
        clickpoint(tp5);
        clickpoint(tp6);
        clickpoint(tp7);
        clickpoint(tp8);
        clickpoint(tp9);
        clickpoint(tp10);
        clickpoint(tp11);
        clickpoint(tp12);
        clickpoint(tp13);


        final Animation anim_start= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_start);
        final Animation anim_rot = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_rotate);
        final Animation anim_fin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_fin);

        final Handler handler = new Handler();
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(5000);
                }catch (Exception e){
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        point_img1.setImageResource(R.drawable.point_img);
                        point_img1.startAnimation(anim_start);
                    }
                });

                try{
                    Thread.sleep(1500);
                }catch (Exception e){
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        point_img1.setImageResource(0);
                        point_img2.setImageResource(R.drawable.point_img);
                        point_img2.startAnimation(anim_rot);
                    }
                });
                try{
                    Thread.sleep(1800);
                }catch (Exception e){
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        point_img2.startAnimation(anim_fin);
                    }
                });
            }
        });
        th.start();

        /* OuterActivity */
        Intent intent = getIntent();
        login_id = intent.getStringExtra("login_id");


        TextView example = (TextView) findViewById(R.id.example);
        ImageButton plus_btn = (ImageButton) findViewById(R.id.plus_btn);
        ImageView picture = (ImageView) findViewById(R.id.picture_upda);

        SharedPreferences total_list = getSharedPreferences(login_id+"_outer_total", MODE_PRIVATE);
        SharedPreferences outer_id_img = getSharedPreferences(login_id+"_outer_img", MODE_PRIVATE);
        SharedPreferences outer_id_title = getSharedPreferences(login_id+"_outer_title", MODE_PRIVATE);
        SharedPreferences outer_id_size = getSharedPreferences(login_id+"_outer_size", MODE_PRIVATE);
        SharedPreferences.Editor edit_total_list = total_list.edit();
        edit_total_list.putInt(login_id,1);
        total_i = total_list.getInt(login_id, 1);

        for(int count = 0; count <= total_i-1; count=count+1) {
            String img =outer_id_img.getString(String.valueOf(count), "");
            Bitmap bitmap;
            if(img == ""){
               bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.noimage);
            } else {
                bitmap = StringToBitMap(img);
            }
            Bitmap resized = Bitmap.createScaledBitmap(bitmap,300, 400, true);
            String title = outer_id_title.getString(String.valueOf(count), "no_title");
            String size = outer_id_size.getString(String.valueOf(count),"no_size");

            OuterItem outerItem = new OuterItem(resized, title, size);
            list_num.add(count);
            data.add(outerItem);
        }
        for(int remove_num= total_i-1; remove_num>=0; remove_num=remove_num-1){
            if(data.get(remove_num).getOuter_info()=="no_title" || data.get(remove_num).getOuter_size()=="no_size"){
                list_num.remove(remove_num);
                data.remove(remove_num);
                adapter.notifyDataSetChanged();
            }
        }

        final ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checked1 = listView.getCheckedItemPosition();
                int get_number = list_num.get(checked1);
                Intent intent = new Intent(OuterActivity.this, Example_modifyActivity.class);
                intent.putExtra("get_number", get_number);
                intent.putExtra("login_id", login_id);
                intent.putExtra("profile", data.get(position).getProfile());
//                intent.putExtra("outer_info", data.get(position).getOuter_info());
//                intent.putExtra("outer_size", data.get(position).getOuter_size());
                startActivityForResult(intent, 33);
            }
        });

        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_plus = new Intent(v.getContext(), Example3Activity.class);
                intent_plus.putExtra("login_id", login_id);
                startActivityForResult(intent_plus, outermake_code);
            }
        });

        example.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), ExampleActivity.class);
                intent.putExtra("outer_times", outer_times);
                outer_times=outer_times+1;
                startActivityForResult(intent,outerrequest_code);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {  // Activityforresult를 이용해서 값을 반환받음
        int outercode_modi=33;
        if(requestCode == outerrequest_code && resultCode == 1){ // 예제 버튼 클릭 후 저장했을 때
            TextView example = (TextView) findViewById(R.id.example);
            example.setText(data_intent.getStringExtra("item_name"));
            Toast.makeText(OuterActivity.this, "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        } else if(requestCode == outercode_modi && resultCode == 5) { //outer 수정버튼 클릭 시
            String[] send = data_intent.getStringArrayExtra("send1");
            Bitmap bm = (Bitmap)data_intent.getParcelableExtra("bm");

            OuterItem outerItem = new OuterItem(bm, send[0], send[1]);

            data.set(checked1, outerItem);
            adapter.notifyDataSetChanged();

            Toast.makeText(OuterActivity.this, "항목이 수정되었습니다.", Toast.LENGTH_SHORT).show();
        } else if(requestCode == outercode_modi && resultCode ==55) {  //outer 삭제 버튼 클릭 시
            list_num.remove(checked1);
            data.remove(checked1);
            adapter.notifyDataSetChanged();
            Toast.makeText(OuterActivity.this, "삭제하셨습니다.",Toast.LENGTH_SHORT).show();

        } else if(requestCode == outermake_code && resultCode == 5){ //새로운 항목 추가 버튼 후 저장했을 때
            String[] send = data_intent.getStringArrayExtra("send2");
            Bitmap bm = (Bitmap) data_intent.getParcelableExtra("bm");
            SharedPreferences total_list = getSharedPreferences(login_id+"_outer_total", MODE_PRIVATE);
            total_i = total_list.getInt(login_id,1);
            list_num.add(total_i);
            OuterItem outerItem = new OuterItem(bm, send[0], send[1]);
            data.add(outerItem);
            adapter.notifyDataSetChanged();
            SharedPreferences.Editor edit_total_list = total_list.edit();
            edit_total_list.putInt(login_id, total_i+1);
            edit_total_list.commit();

            Toast.makeText(OuterActivity.this, "항목이 추가되었습니다.", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(OuterActivity.this, "저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // listview 사용
    public class outerAdapter extends BaseAdapter{
        Context context;
        private LayoutInflater inflater;
        public ArrayList<OuterItem> data;
        private int layout;

        public outerAdapter(Context context, int layout, ArrayList<OuterItem> data ){ //outer 이미지 값이 없음
           this.context = context;
          this.data = data;
          this.layout = layout;
        }



        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position).getOuter_info();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layout, parent, false);
            }
            OuterItem outerItem = data.get(position);

            ImageView img_outer = (ImageView) convertView.findViewById(R.id.img_outer);
            img_outer.setImageBitmap(outerItem.getProfile());

            TextView listview_name = (TextView) convertView.findViewById(R.id.listview_name);
            listview_name.setText(outerItem.getOuter_info());

            TextView listview_size = (TextView) convertView.findViewById(R.id.listview_size);
            listview_size.setText(outerItem.getOuter_size());

            return convertView;
        }
    }
}
