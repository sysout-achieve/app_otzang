//package com.example.msi.ottzzang;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//public class OuterActivity21111 extends AppCompatActivity {
//
//            int outer_times = 0;
//            int restart_i =0;
//            int outerrequest_code = 1;
//            int outermake_code = 5;
//    ArrayList<String> list_item_name1 = new ArrayList<>();
//    ArrayList<String> list_item_size1 = new ArrayList<>();
//    ArrayList<Bitmap> list_item_img1 = new ArrayList<>();
//    outerAdapter adapter = new outerAdapter(this, list_item_img1, list_item_name1, list_item_size1);
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(restart_i == 8){     //OuterActivity 에 5번 들어올 경우 아우터에 관한 광고를 만들어 줌
//            Toast.makeText(OuterActivity21111.this, "아우터에 관심이 많으시네요~ 이 제품들은 어떠신가요?", Toast.LENGTH_SHORT).show();
//            Intent intent_ad = new Intent(OuterActivity21111.this, AdvertisementActivity.class);
//            startActivity(intent_ad);
//            restart_i =0;
//        } else {
//            restart_i=restart_i+1;
//        }
//    }
//
//    private int setSimpleSize(BitmapFactory.Options options, int requestWidth, int requestHeight){
//        int originalWidth = options.outWidth;
//        int originalHeight = options.outHeight;
//        int size = 1;
//
//        while (requestHeight<originalHeight || requestWidth<originalWidth){
//            originalHeight = originalHeight/2;
//            originalWidth = originalWidth/2;
//            size = size*2;
//        }
//        return size;
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_outer);
//
//        TextView example = (TextView) findViewById(R.id.example);
//        ImageButton plus_btn = (ImageButton) findViewById(R.id.plus_btn);
//        ImageView picture = (ImageView) findViewById(R.id.picture);
////        BitmapFactory.Options options = new BitmapFactory.Options();
////        options.inJustDecodeBounds = true;
////        Resources res = this.getResources(); //bitmap으로 이미지 넣을 때
////        Bitmap bitmap = BitmapFactory.decodeFile("",options);
////
////        options.inSampleSize = setSimpleSize(options, 500, 500);
////        options.inJustDecodeBounds = false;
//
//
////        Bitmap bitmap1 = BitmapFactory.decodeResource(res, R.drawable.coat1);
////
////
////        //Arraylist 생성
////
////        list_item_img.add(bitmap);
////        list_item_img.add(bitmap1);
//
//
//        list_item_name1.add("롱패딩");
//        list_item_name1.add("코트1");
//        list_item_name1.add("코트2");
//        list_item_name1.add("개파카");
//        list_item_name1.add("패딩");
//
//        list_item_size1.add("M");
//        list_item_size1.add("L");
//        list_item_size1.add("L");
//        list_item_size1.add("M");
//        list_item_size1.add("M");
////
////        list_item_img1.add(bitmap);
////        list_item_img1.add(bitmap);
////        list_item_img1.add(bitmap);
////        list_item_img1.add(bitmap);
////        list_item_img1.add(bitmap);
//        final ListView listView = (ListView) findViewById(R.id.listview);
//        listView.setAdapter(adapter);
//
//
//
//        adapter.notifyDataSetChanged();
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//               int count = adapter.getCount();
//               int checked = listView.getCheckedItemPosition();
//               if(count > 0){
//                   Intent intent = new Intent(OuterActivity21111.this, Example_modifyActivity.class);
////                   String[] list_item = new String[]{list_item_img1.get(checked).toString(), list_item_size1.get(checked).toString() };
////                   intent.putExtra("list_item", list_item);
//                   startActivityForResult(intent,33);
////                   Toast.makeText(OuterActivity.this, list_item_name1.get(checked), Toast.LENGTH_SHORT ).show();
//               }
//            }
//        });
//
//        plus_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_plus = new Intent(v.getContext(), Example3Activity.class);
//                startActivityForResult(intent_plus, outermake_code);
//            }
//        });
//
//        example.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(v.getContext(), ExampleActivity.class);
//                intent.putExtra("outer_times", outer_times);
//                outer_times=outer_times+1;
//                startActivityForResult(intent,outerrequest_code);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  // Activityforresult를 이용해서 값을 반환받음
//        if(requestCode == outerrequest_code && resultCode == 1){
//            TextView example = (TextView) findViewById(R.id.example);
//            example.setText(data.getStringExtra("item_name"));
//            Toast.makeText(OuterActivity21111.this, "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
//        } else if(requestCode == outermake_code && resultCode == 5){
//
//
//            String[] send = data.getStringArrayExtra("send1");
////            Bitmap receive_b = (Bitmap)data.getExtras().get("bm");
////            list_item_img1.add(receive_b);
//            list_item_name1.add(send[0]);
//            list_item_size1.add(send[1]);
//
//            adapter.notifyDataSetChanged();
//
//          // 여기서부터 다시해라 if ()
//            Toast.makeText(OuterActivity21111.this, "항목이 추가되었습니다.", Toast.LENGTH_SHORT).show();
//
//        } else {
//            Toast.makeText(OuterActivity21111.this, "저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    // listview 사용
//    public class outerAdapter extends BaseAdapter{
//
//        Context context_list;
////        ArrayList list_item_img;
////        ArrayList<String> list_item_name;
////        ArrayList<String> list_item_size;
//
//        public outerAdapter(Context context, ArrayList list_item_img, ArrayList list_item_name, ArrayList list_item_size){ //outer 이미지 값이 없음
//            this.context_list = context;
//            list_item_img1 =  list_item_img;
//            list_item_name1 = list_item_name;
//            list_item_size1 = list_item_size;
//        }
//
//
//
//        @Override
//        public int getCount() {
//            return list_item_name1.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list_item_name1.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//
//
//
//            if(convertView == null){
//                LayoutInflater inflater = LayoutInflater.from(context_list);
//                convertView = inflater.inflate(R.layout.listview_outer, null);
//            }
//            ImageView img_outer = (ImageView) convertView.findViewById(R.id.img_outer);
//            TextView listview_name = (TextView) convertView.findViewById(R.id.listview_name);
//            TextView listview_size = (TextView) convertView.findViewById(R.id.listview_size);
////            ImageView picture = (ImageView) findViewById(R.id.picture);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//
//
//            listview_name.setText(list_item_name1.get(position));
//            listview_size.setText(list_item_size1.get(position));
////            img_outer.setImageBitmap(BitmapFactory.decodeFile(list_item_img1.get(position).toString(),options));
////            list_item_img1.add(0,R.drawable.longpadding);
////            list_item_img1.add(1,R.drawable.coat1);
////            list_item_img1.add(2,R.drawable.coat2);
////            list_item_img1.add(3,R.drawable.parka);
////            list_item_img1.add(4,R.drawable.padding);
////            exercise_list_photo.setImageBitmap(BitmapFactory.decodeFile("" + this.exercise_list_photo.get(position).toString(), options));
//
//            if("롱패딩".equals(list_item_name1.get(position))) {
//                img_outer.setImageResource(R.drawable.longpadding);
//            } else if("코트1".equals(list_item_name1.get(position))){
//                img_outer.setImageResource(R.drawable.coat1);
//            } else if("코트2".equals(list_item_name1.get(position))){
//                img_outer.setImageResource(R.drawable.coat2);
//            } else if("개파카".equals(list_item_name1.get(position))){
//                img_outer.setImageResource((R.drawable.parka));
//            } else if("패딩".equals(list_item_name1.get(position))) {
//                img_outer.setImageResource(R.drawable.padding);
//            }
////            } else {
////                img_outer.setImageResource(R.drawable.padding);
////            }
//            return convertView;
//        }
//    }
//}
