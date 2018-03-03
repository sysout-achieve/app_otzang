package com.example.msi.ottzzang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class SubMainActivity extends AppCompatActivity {
    int count;
    int checked;
    int call_value; // 게시판 총 갯수 저장하는 변수, 1씩 증가하면서 arraylist list_num저장
    int write_code = 101;
    int check_cart;
    int count_cart;
    int ad = 0;
    int main_ad_num = 0;
    String login_id;

    String log_id;
    BoardItem boardItem;
    CartItem cart_item;
    int list_count;
    int remove_num;
    int list_cart_count;
    int list_cart_total; // 찜목록 총 갯수 저장하는 변수, 1씩 증가하면서 arraylist list_cart_num 저장
    int check_list;
    // board listview Item
    ListView listview;

    /*리스트 뷰 갱신을 위한 레이아웃 아이템 변수*/
    SharedPreferences list_i;
    SharedPreferences list_name;
    SharedPreferences list_esti;
    SharedPreferences list_review;
    SharedPreferences list_write;
    SharedPreferences list_img;
    SharedPreferences list_kind;
    SharedPreferences list_renum;
    SharedPreferences img1;
    SharedPreferences img2;
    SharedPreferences img3;
    SharedPreferences cart_like;
    SharedPreferences total_list_cart;
    /*---------------------------------------*/
    ArrayList<BoardItem> b_item = new ArrayList<>();

    ArrayList<Integer> list_num = new ArrayList<>();

    // cart listview Item
    ArrayList<CartItem> cartItems = new ArrayList<>();

    ArrayList<Integer> list_cart_num = new ArrayList<>();
    ArrayList<String> list_cart = new ArrayList<>();
    ArrayList<Bitmap> list_cart_img = new ArrayList<>();

    boardAdapter boardAdapter = new boardAdapter(this, R.layout.listview_submain1, b_item, list_num);
    cartAdapter cartAdapter = new cartAdapter(this, R.layout.listview_submain_cart, cartItems);

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
    protected void onResume() {
        super.onResume();

        list_num.clear();
        b_item.clear();
        list_cart.clear();
        list_cart_num.clear();

        call_value = list_i.getInt("list_number_count", 1);    //게시글 총 갯수 저장하는 변수

        for(list_count=call_value-1; list_count > 0; list_count=list_count-1){
            String img = img1.getString(String.valueOf(list_count),"");
            Bitmap bitmap;
            if(img == ""){
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
            } else{
                bitmap = StringToBitMap(img);
            }
            Bitmap resized = Bitmap.createScaledBitmap(bitmap,200, 250, true);
            String name = list_name.getString(String.valueOf(list_count), "no_title");
            String review = list_review.getString(String.valueOf(list_count), "no_review");
            String write = list_write.getString(String.valueOf(list_count), "no_writer");
            String kind = list_kind.getString(String.valueOf(list_count), "no_kind");
            int renum = list_renum.getInt(String.valueOf(list_count), 0);
            int heartnum = cart_like.getInt(String.valueOf(list_count),0);
            list_num.add(list_count);
            boardItem = new BoardItem(resized, name, write, kind, renum, heartnum );
            b_item.add(boardItem);
        }

        for( remove_num=call_value-1; remove_num > 0; remove_num=remove_num-1){
            if(b_item.get(remove_num-1).getBod_title() == "no_title" && b_item.get(remove_num-1).getBod_writer() == "no_writer") {
                list_num.remove(remove_num-1);
                b_item.remove(remove_num-1);
                boardAdapter.notifyDataSetChanged();
            }
        }

        list_cart_total = total_list_cart.getInt("list_cart_total", 1);


        //리스트뷰 찜 목록!!!!!!!!!!!!!!
        cartItems.clear();
        list_cart_num.clear();
        SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);
        for(list_cart_count=1; list_cart_count <= call_value; list_cart_count=list_cart_count+1){
            String img = img1.getString(String.valueOf(list_cart_count),"");
            Bitmap bitmap;
            if(img == ""){
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
            } else{
                bitmap = StringToBitMap(img);
            }
            Bitmap resized = Bitmap.createScaledBitmap(bitmap,200, 250, true);
            String name = list_id.getString(String.valueOf(list_cart_count),"no_cart");
            cart_item = new CartItem(resized, name);
            cartItems.add(cart_item);
            list_cart_num.add(list_cart_count);

        }
        for( int remove_cart = call_value-1 ;   remove_cart >= 0 ; remove_cart=remove_cart-1){
            if(cartItems.get(remove_cart).getcart_title() == "no_cart"){
                list_cart_num.remove(remove_cart);
                cartItems.remove(remove_cart);
                cartAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submain);

         /*Stetho----------------------------*/
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        /*Submain*/
        final Intent intent = getIntent();
        login_id = intent.getStringExtra("login_id");
        String str = intent.getStringExtra("name1");
        Toast.makeText(this, str + "님 반갑습니다.", Toast.LENGTH_SHORT).show();

        TabHost tabs = (TabHost) findViewById(R.id.tabs);
        tabs.setup();
        TabHost.TabSpec spec1 = tabs.newTabSpec("옷장");
        spec1.setIndicator("옷장");
        spec1.setContent(R.id.옷장);
        tabs.addTab(spec1);

        TabHost.TabSpec spec2 = tabs.newTabSpec("게시판");
        spec2.setIndicator("게시판");
        spec2.setContent(R.id.게시판);
        tabs.addTab(spec2);

        TabHost.TabSpec spec3 = tabs.newTabSpec("찜 목록");
        spec3.setIndicator("찜 목록");
        spec3.setContent(R.id.장바구니);
        tabs.addTab(spec3);


        //옷장 Tab
        ImageView outer = (ImageView) findViewById(R.id.outer);
        ImageView top = (ImageView) findViewById(R.id.top);
        ImageView bottom = (ImageView) findViewById(R.id.bottom);
        final ImageView profile_btn = (ImageView) findViewById(R.id.profile_btn);
        final ImageView main_ad = (ImageView) findViewById(R.id.main_ad);
        main_ad.setImageResource(R.drawable.main_ad1);
        final Handler handler_admain = new Handler();
        Thread thread_mainad = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {

                    }
                    handler_admain.post(new Runnable() {
                        @Override
                        public void run() {
                            if (main_ad_num == 0) {
                                main_ad.setImageResource(R.drawable.main_ad1);
                                main_ad_num = 1;
                            } else if (main_ad_num == 1) {
                                main_ad.setImageResource(R.drawable.main_ad2);
                                main_ad_num = 2;
                            } else if (main_ad_num == 2) {
                                main_ad.setImageResource(R.drawable.main_ad3);
                                main_ad_num = 3;
                            } else {
                                main_ad.setImageResource(R.drawable.main_ad4);
                                main_ad_num = 0;
                            }
                        }
                    });
                }
            }
        });
        thread_mainad.start();

        //board Tab
        final ImageView write_btn = (ImageView) findViewById(R.id.write_btn);
        //cart Tab
        final ImageView ad_img = (ImageView) findViewById(R.id.ad_imgv);
        ad_img.setImageResource(R.drawable.ad_img1);

        final Handler handler_ad = new Handler();
        Thread thread_ad = new Thread(new Runnable() { //찜목록 오른쪽 아래에 광고 배너 sub_thread
            @Override
            public void run() {
               while(true) {
                   try {
                       Thread.sleep(2000);
                   } catch (Exception e) {

                   }
                   handler_ad.post(new Runnable() {
                       @Override
                       public void run() {
                           if (ad == 0) {
                               ad_img.setImageResource(R.drawable.ad_img2);
                               ad = 1;
                           } else if(ad ==1){
                               ad_img.setImageResource(R.drawable.ad_img1);
                               ad = 2;
                           } else {
                               ad_img.setImageResource(R.drawable.ad_img3);
                               ad = 0;
                           }
                       }
                   });
               }
            }
        });
        thread_ad.start();



//        ImageView delete_btn = (ImageView) findViewById(R.id.delete_btn);
//        ImageView read_btn = (ImageView) findViewById(R.id.read_btn);
        final SharedPreferences board = getSharedPreferences("board", MODE_PRIVATE);
//        SharedPreferences list_count_save = getSharedPreferences("login_count", MODE_PRIVATE);
//        SharedPreferences.Editor edit_list_count = list_count_save.edit();
//


        //리스트뷰 게시판
        String list = "list";
       list_i = getSharedPreferences("list_number_count", MODE_PRIVATE);
         list_name = getSharedPreferences("list_name", MODE_PRIVATE);
         list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
         list_review = getSharedPreferences("list_review", MODE_PRIVATE);
         list_write = getSharedPreferences("list_write", MODE_PRIVATE);
         list_img = getSharedPreferences("list_img", MODE_PRIVATE);
         list_kind = getSharedPreferences("list_kind", MODE_PRIVATE);
         list_renum = getSharedPreferences("list_renum", MODE_PRIVATE);
        SharedPreferences list_heartnum = getSharedPreferences("list_heartnum", MODE_PRIVATE);
         img1 = getSharedPreferences("img1", MODE_PRIVATE);
         img2 = getSharedPreferences("img2", MODE_PRIVATE);
         img3 = getSharedPreferences("img3", MODE_PRIVATE);
         cart_like = getSharedPreferences("cart_like",MODE_PRIVATE);
        SharedPreferences.Editor edit_cart_like = cart_like.edit();
        SharedPreferences point = getSharedPreferences("point",MODE_PRIVATE);
        SharedPreferences.Editor edit_point = point.edit();


        SharedPreferences cart_on = getSharedPreferences("cart_on",MODE_PRIVATE);
        total_list_cart = getSharedPreferences("list_cart_total",MODE_PRIVATE); //찜목록 저장 갯수와 번호순으로 저장

        SharedPreferences.Editor edit_img1 = img1.edit();
        SharedPreferences.Editor edit_img2 = img2.edit();
        SharedPreferences.Editor edit_img3 = img3.edit();
        SharedPreferences.Editor edit_total_cart = total_list_cart.edit();
        SharedPreferences.Editor edit_cart_on = cart_on.edit();
        SharedPreferences.Editor edit_list_i = list_i.edit();
        SharedPreferences.Editor edit_list_name = list_name.edit();
        SharedPreferences.Editor edit_list_esti = list_esti.edit();
        SharedPreferences.Editor edit_list_review = list_review.edit();
        SharedPreferences.Editor edit_list_write = list_write.edit();
        SharedPreferences.Editor edit_list_img = list_img.edit();
        SharedPreferences.Editor edit_list_kind = list_kind.edit();


        //SharedPreferences 초기화
//        edit_point.clear().commit();
//        edit_cart_like.clear().commit();
//        edit_img1.clear().commit();
//        edit_img2.clear().commit();
//        edit_img3.clear().commit();
//        edit_list_img.clear().commit();
//        edit_list_kind.clear().commit();
//        edit_total_cart.clear();
//        edit_cart_on.clear();
//        edit_list_i.clear();
//        edit_list_name.clear();
//        edit_list_esti.clear();
//        edit_list_review.clear();
//        edit_list_write.clear();
//        edit_total_cart.commit();
//        edit_cart_on.commit();
//        edit_list_i.commit();
//        edit_list_name.commit();
//        edit_list_esti.commit();
//        edit_list_review.commit();
//        edit_list_write.commit();




        final ListView listview_cart = (ListView) findViewById(R.id.list_cart);
        listview_cart.setAdapter(cartAdapter);

        listview = (ListView) findViewById(R.id.list_sub);
        listview.setAdapter(boardAdapter);

        call_value = list_i.getInt("list_number_count", 1);    //게시글 총 갯수 저장하는 변수
//
//        for(list_count=call_value-1; list_count > 0; list_count=list_count-1){
//            String img = img1.getString(String.valueOf(list_count),"");
//            Bitmap bitmap;
//            if(img == ""){
//                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.noimage);
//            } else{
//                bitmap = StringToBitMap(img);
//            }
//            Bitmap resized = Bitmap.createScaledBitmap(bitmap,200, 250, true);
//            String name = list_name.getString(String.valueOf(list_count), "no_title");
//            String review = list_review.getString(String.valueOf(list_count), "no_review");
//            String write = list_write.getString(String.valueOf(list_count), "no_writer");
//            String kind = list_kind.getString(String.valueOf(list_count), "no_kind");
//            int renum = list_renum.getInt(String.valueOf(list_count), 0);
//            int heartnum = cart_like.getInt(String.valueOf(list_count),0);
//            list_num.add(list_count);
//            boardItem = new BoardItem(resized, name, write, kind, renum, heartnum, null, null,null );
//            b_item.add(boardItem);
//            }
//
//        for(int remove_num=0; remove_num > call_value-1; remove_num=remove_num+1){
//            if(b_item.get(remove_num).getBod_title() == "no_title" && b_item.get(remove_num).getBod_writer() == "no_writer") {
//                list_num.remove(remove_num);
//                b_item.remove(remove_num);
//                boardAdapter.notifyDataSetChanged();
//            }
//        }
//
//        list_cart_total = total_list_cart.getInt("list_cart_total", 1);

//        //리스트뷰 찜 목록!!!!!!!!!!!!!!
//        SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);
//        for(list_cart_count=1; list_cart_count <= call_value; list_cart_count=list_cart_count+1){
//            list_cart_num.add(list_cart_count);
//            list_cart.add(list_id.getString(String.valueOf(list_cart_count),"no_cart"));
//        }
//        for( int remove_cart = call_value-1 ;   remove_cart >= 0 ; remove_cart=remove_cart-1){
//            if(list_cart.get(remove_cart).toString()=="no_cart"){
//                list_cart_num.remove(remove_cart);
//                list_cart.remove(remove_cart);
//                cartAdapter.notifyDataSetChanged();
//            }
//        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                check_list = listview.getCheckedItemPosition();
                int get_number = list_num.get(check_list);
                SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);
                log_id = login_id_check.getString(String.valueOf(get_number), "no_writer");
                SharedPreferences img1 = getSharedPreferences("img1", MODE_PRIVATE);
                SharedPreferences img2 = getSharedPreferences("img2", MODE_PRIVATE);
                SharedPreferences img3 = getSharedPreferences("img3", MODE_PRIVATE);
                String str_img1 =img1.getString(String.valueOf(get_number), "");
                Bitmap bitmap;
//                if(str_img1 == ""){
                    bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.noimage);
//                } else {
//                    bitmap = StringToBitMap(str_img1);
//                }
                Bitmap resize = Bitmap.createScaledBitmap(bitmap,10, 15, true);

//                String str_img2 =img2.getString(String.valueOf(get_number), "");
//                Bitmap bitmap1;
//                if(str_img2 == ""){
//                    bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.noimage);
//                } else {
//                    bitmap1 = StringToBitMap(str_img2);
//                }
//                String str_img3 =img3.getString(String.valueOf(get_number), "");
//                Bitmap bitmap2;
//                if(str_img3 == ""){
//                    bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.noimage);
//                } else {
//                    bitmap2 = StringToBitMap(str_img3);
//                }

//                    BoardItem boardItem = b_item.get(check_list);
//                    boardItem.setImg1(bitmap);
//                    boardAdapter.notifyDataSetChanged();

                    /* 작성자 아이디 체크해서 다른 액티비티로 들어가서 화면 확인 */
                if(log_id.equals(login_id)){
                    Intent intent = new Intent(SubMainActivity.this, Board_modify_Activity.class);
                    intent.putExtra("get_number", get_number);
                    intent.putExtra("login_id", login_id);
                    startActivityForResult(intent, 20);
                } else {
                    Intent intent = new Intent(SubMainActivity.this, Board_cart_Activity.class);
                    intent.putExtra("get_number", get_number);
                    intent.putExtra("login_id", login_id);
                    startActivityForResult(intent, 20);
                }
            }
        });

        listview_cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                check_cart= listview_cart.getCheckedItemPosition();
                int cartnum = list_cart_num.get(check_cart);
                Intent intent = new Intent(SubMainActivity.this, Board_cart_Activity.class);
                intent.putExtra("get_number", cartnum);
                intent.putExtra("login_id", login_id);
                startActivity(intent);
            }
        });

        main_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_link = new Intent(Intent.ACTION_VIEW, Uri.parse("http://store.musinsa.com/app/"));
                startActivity(intent_link);
            }
        });

//        delete_board_btn.setOnClickListener(new View.OnClickListener() { //게시판 목록 삭제
//            @Override
//            public void onClick(View v) {
//
//                count = boardAdapter.getCount();
//
//                if(count > 0) {
//                    checked = listview.getCheckedItemPosition();
//                    if(checked > -1 && checked < count){
//                        SharedPreferences login_id_check = getSharedPreferences("login_id_check", MODE_PRIVATE);
//                        int list_delete_number = list_num.get(checked);
//                        log_id = login_id_check.getString(String.valueOf(list_delete_number), "no_writer");
//
//                        if(login_id.equals(log_id)) {
//
//                            list_num.remove(checked);
//                            list_board.remove(checked);
//                            list_writer.remove(checked);
//
//                            SharedPreferences list_i = getSharedPreferences("list_number_count", MODE_PRIVATE);
//                            SharedPreferences list_name = getSharedPreferences("list_name", MODE_PRIVATE);
//                            SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
//                            SharedPreferences list_review = getSharedPreferences("list_review", MODE_PRIVATE);
//                            SharedPreferences list_write = getSharedPreferences("list_write", MODE_PRIVATE);
//
//                            SharedPreferences.Editor edit_list_i = list_i.edit();
//                            SharedPreferences.Editor edit_list_name = list_name.edit();
//                            SharedPreferences.Editor edit_list_esti = list_esti.edit();
//                            SharedPreferences.Editor edit_list_review = list_review.edit();
//                            SharedPreferences.Editor edit_list_write = list_write.edit();
//
//                            edit_list_name.remove(String.valueOf(list_delete_number));
//                            edit_list_esti.remove(String.valueOf(list_delete_number));
//                            edit_list_review.remove(String.valueOf(list_delete_number));
//                            edit_list_write.remove(String.valueOf(list_delete_number));
//
//                            edit_list_name.commit();
//                            edit_list_esti.commit();
//                            edit_list_review.commit();
//                            edit_list_write.commit();
//
//                            listview.clearChoices();
//                            boardAdapter.notifyDataSetChanged();
//                            Toast.makeText(SubMainActivity.this, list_delete_number + "번 항목을 삭제하셨습니다.", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(SubMainActivity.this, "삭제 권한이 없습니다.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//        });



//        read_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { //장바구니에서 읽기 버튼 클릭 이벤트
//                count_cart = cartAdapter.getCount();
//                check_cart = listview_cart.getCheckedItemPosition();
//                if(check_cart > -1 && check_cart < count_cart){
//                    int i = list_cart_num.get(check_cart);
//                    Intent intent = new Intent(SubMainActivity.this, Board_read_Activity.class);
//                    intent.putExtra("checked", i);
//                    intent.putExtra("login_id", login_id);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        delete_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) { //찜목록 탭에서 삭제 버튼 클릭 시
//                count_cart = cartAdapter.getCount();
//                if(count_cart >0){
//                    check_cart = listview_cart.getCheckedItemPosition();
//                    if(check_cart > -1 && check_cart < count_cart){
//                        //찜목록에서 바로 찜 삭제하면 하트 지워짐
//                        int i = list_cart_num.get(check_cart);
//                        SharedPreferences cart_on = getSharedPreferences(String.valueOf(i),MODE_PRIVATE);
//                        SharedPreferences.Editor edit_cart_on = cart_on.edit();
//                        edit_cart_on.putInt(login_id, 0);
//                        edit_cart_on.commit();
//                        SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);
//                        SharedPreferences.Editor edit_list_cart = list_id.edit();
//                        edit_list_cart.remove(String.valueOf(i));
//                        edit_list_cart.commit();
//
//                        list_cart_num.remove(check_cart);
//                        list_cart.remove(check_cart);
//
//                        listview_cart.clearChoices();
//                        cartAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });

//        modify_btn.setOnClickListener(new View.OnClickListener() { // 읽기 버튼 클릭 이벤트 확정시 주석도 제거
//            @Override
//            public void onClick(View v) {
//
//                count = boardAdapter.getCount();
//
//                if (count > 0){
//                    checked = listview.getCheckedItemPosition();
//                    if (checked > -1 && checked < count) {
//                        Intent intent = new Intent(SubMainActivity.this, Board_modify_Activity.class);
//                        int get_list_num = list_num.get(checked);
//
//                        intent.putExtra("login_id", login_id);
//                        intent.putExtra("checked", get_list_num);
//                        startActivityForResult(intent, 20 );
//                    }
//                }
//            }
//        });


        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_write = new Intent(v.getContext(), BoardActivity.class);
                intent_write.putExtra("login_id", login_id);
                startActivityForResult(intent_write, write_code);   //새로운 화면을 띄우는 것이 목적이 아님, 정보를 처리하여 원래의 액티비티에 정보를 처리하는 것이 목적
            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(v.getContext(), ProfileActivity.class);
                intent_profile.putExtra("login_id", login_id);
                startActivity(intent_profile); //startActivityForResult를 해야할 지 더 고민해봐야함
            }
        });

        outer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubMainActivity.this, OuterActivity.class);
                intent.putExtra("login_id", login_id);
                startActivity(intent);
            }
        });
    } // onCreate End.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == write_code && resultCode == 10){                              // 게시판에서 저장을 하게 될 경우

            Toast.makeText(this, "저장되었습니다.",Toast.LENGTH_SHORT).show();

        } else if(requestCode == 20 && resultCode ==20){                                //게시판에서 수정을 선택할 경우


            Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();

        } else if(requestCode == 20 && resultCode == 22) {                              //게시글에서 찜 확인 후 게시판으로 나왔을 경우

        } else if(requestCode == 20 && resultCode == 23) {

        } else if(requestCode == 20 && resultCode == 40) {                                  // 게시판에서 삭제 버튼 클릭시
            int check = data.getIntExtra("check",0);

            Toast.makeText(SubMainActivity.this, check + "번 항목을 삭제하셨습니다.", Toast.LENGTH_SHORT).show();

        } else {

        }
        boardAdapter.notifyDataSetChanged();
    } // onActivityResult of End.
}// SubMainActivity Class End.

    class boardAdapter extends BaseAdapter{
        Context context_board;
        private LayoutInflater inflater;
        public ArrayList<BoardItem> b_item;
        private int layout;

        ArrayList<Integer> list_num;
//        ArrayList<String> list_board;
//        ArrayList<String> list_writer;

        public boardAdapter(Context context, int layout, ArrayList<BoardItem> b_item,ArrayList<Integer> list_num){
            this.context_board = context;
            this.b_item = b_item;
            this.list_num = list_num;
            this.layout = layout;
        }

        @Override
        public int getCount() {
            return b_item.size();
        }

        @Override
        public Object getItem(int position) {
            return b_item.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(context_board);
                convertView = inflater.inflate(R.layout.listview_submain1,null);
            }
            BoardItem boardItem = b_item.get(position);

            TextView text_num = (TextView) convertView.findViewById(R.id.text_num);
            ImageView bod_img = (ImageView) convertView.findViewById(R.id.bod_img);
            TextView text_name = (TextView) convertView.findViewById(R.id.text_name);
            TextView kind_txt =(TextView) convertView.findViewById(R.id.kind_txt);
            TextView text_write = (TextView) convertView.findViewById(R.id.text_writer);
            TextView re_num = (TextView) convertView.findViewById(R.id.re_num);
            TextView heart_num = (TextView) convertView.findViewById(R.id.heart_num);
            ImageView img1 = (ImageView) convertView.findViewById(R.id.bimg_1);
            ImageView img2 = (ImageView) convertView.findViewById(R.id.bimg_2);
            ImageView img3 = (ImageView) convertView.findViewById(R.id.bimg_3);

            bod_img.setImageBitmap(boardItem.getBod_img());
//
//            if(boardItem.getImg1()!=null){
//                img1.setImageBitmap(boardItem.getImg1());
//            }

            text_num.setText(list_num.get(position).toString());
            text_name.setText(boardItem.getBod_title());
            text_write.setText(boardItem.getBod_writer());
            kind_txt.setText(boardItem.getBod_cate());

            heart_num.setText(boardItem.getHeart_num().toString());
            re_num.setText(boardItem.getRe_num().toString());

            return convertView;
        }
    }

    class cartAdapter extends BaseAdapter{
        Context context_cart;
        ArrayList<CartItem> cartItems;
        int layout;

        public cartAdapter(Context context,int layout, ArrayList<CartItem> cartItems){
            this.context_cart = context;
            this.cartItems = cartItems;
            this.layout = layout;

        }
        @Override
        public int getCount() {
            return cartItems.size();
        }

        @Override
        public Object getItem(int position) {
            return cartItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(context_cart);
                convertView = inflater.inflate(R.layout.listview_submain_cart,null);
            }
            CartItem cartItem = cartItems.get(position);

            TextView text_name = (TextView) convertView.findViewById(R.id.text_name);
            ImageView img_cart = (ImageView) convertView.findViewById(R.id.img_cart);

            img_cart.setImageBitmap(cartItem.getcart_img());
            text_name.setText(cartItem.getcart_title());

            return convertView;
        }
    }
