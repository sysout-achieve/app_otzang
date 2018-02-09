package com.example.msi.ottzzang;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;

public class OuterActivity extends AppCompatActivity {
            int checked1;
            int outer_times = 0;
            int restart_i =0;
            int outerrequest_code = 1;
            int outermake_code = 5;
    ArrayList<OuterItem> data = new ArrayList<>();
    outerAdapter adapter = new outerAdapter(this, R.layout.listview_outer, data);

    @Override
    protected void onStart() {
        super.onStart();
        if(restart_i == 8){     //OuterActivity 에 5번 들어올 경우 아우터에 관한 광고를 만들어 줌
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

        TextView example = (TextView) findViewById(R.id.example);
        ImageButton plus_btn = (ImageButton) findViewById(R.id.plus_btn);
        ImageView picture = (ImageView) findViewById(R.id.picture_upda);


        final ListView listView = (ListView) findViewById(R.id.listview);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.longpadding);
        Bitmap resized1 = Bitmap.createScaledBitmap(bitmap1, 300, 400, true);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.parka);
        Bitmap resized2 = Bitmap.createScaledBitmap(bitmap2, 300, 400, true);
        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.coat1);
        Bitmap resized3 = Bitmap.createScaledBitmap(bitmap3, 300, 400, true);
        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.coat2);
        Bitmap resized4 = Bitmap.createScaledBitmap(bitmap4, 300, 400, true);
        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),R.drawable.padding);
        Bitmap resized5 = Bitmap.createScaledBitmap(bitmap5, 300, 400, true);
        OuterItem outerItem1 = new OuterItem(resized1, "롱패딩", "L");
        OuterItem outerItem2 = new OuterItem(resized2, "개파카", "M");
        OuterItem outerItem3 = new OuterItem(resized3, "코트1", "M");
        OuterItem outerItem4 = new OuterItem(resized4, "코트2", "L");
        OuterItem outerItem5 = new OuterItem(resized5, "패딩", "M");

        data.add(outerItem1);
        data.add(outerItem2);
        data.add(outerItem3);
        data.add(outerItem4);
        data.add(outerItem5);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checked1 = listView.getCheckedItemPosition();
                Intent intent = new Intent(getApplicationContext(), Example_modifyActivity.class);
                intent.putExtra("profile", data.get(position).getProfile());
                intent.putExtra("outer_info", data.get(position).getOuter_info());
                intent.putExtra("outer_size", data.get(position).getOuter_size());
                startActivityForResult(intent, 33);
            }
        });

        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_plus = new Intent(v.getContext(), Example3Activity.class);
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
            data.remove(checked1);
            adapter.notifyDataSetChanged();
        } else if(requestCode == outermake_code && resultCode == 5){ //새로운 항목 추가 버튼 후 저장했을 때
            String[] send = data_intent.getStringArrayExtra("send2");
            Bitmap bm = (Bitmap) data_intent.getParcelableExtra("bm");

            OuterItem outerItem = new OuterItem(bm, send[0], send[1]);
            data.add(outerItem);
            adapter.notifyDataSetChanged();
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
