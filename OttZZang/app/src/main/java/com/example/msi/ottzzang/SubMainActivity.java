package com.example.msi.ottzzang;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    int check_cart = 0;
    int count_cart;
    String login_id;
    int list_count = 1;
    int list_cart_count;
    int cart_number = 1;
    int list_cart_total; // 찜목록 총 갯수 저장하는 변수, 1씩 증가하면서 arraylist list_cart_num 저장

    // board listview Item
    ArrayList<Integer> list_num = new ArrayList<>();
    ArrayList<String> list_board = new ArrayList<>();
    ArrayList<String> list_writer= new ArrayList<>();

    // cart listview Item
    ArrayList<Integer> list_cart_num = new ArrayList<>();
    ArrayList<String> list_cart = new ArrayList<>();

    boardAdapter boardAdapter = new boardAdapter(this, list_num, list_board, list_writer);
    cartAdapter cartAdapter = new cartAdapter(this, list_cart);

//    public void make_sp(){
//        SharedPreferences list_i = getSharedPreferences("list_i", MODE_PRIVATE);
//        SharedPreferences list_name = getSharedPreferences("list_name", MODE_PRIVATE);
//        SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
//        SharedPreferences list_review = getSharedPreferences("list_review", MODE_PRIVATE);
//        SharedPreferences list_write = getSharedPreferences("list_write", MODE_PRIVATE);
//
//        SharedPreferences.Editor edit_list_i = list_i.edit();
//        SharedPreferences.Editor edit_list_name = list_name.edit();
//        SharedPreferences.Editor edit_list_esti = list_esti.edit();
//        SharedPreferences.Editor edit_list_review = list_review.edit();
//        SharedPreferences.Editor edit_list_write = list_write.edit();
//    }
//
//    public void remove_sp(){
//
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }

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
        Intent intent = getIntent();
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

        ImageView outer = (ImageView) findViewById(R.id.outer);
        ImageView top = (ImageView) findViewById(R.id.top);
        ImageView bottom = (ImageView) findViewById(R.id.bottom);
        final ImageView profile_btn = (ImageView) findViewById(R.id.profile_btn);
        final ImageView write_btn = (ImageView) findViewById(R.id.write_btn);
        ImageView delete_btn = (ImageView) findViewById(R.id.delete_btn);
        Button modify_btn = (Button) findViewById(R.id.modify_btn);
        Button delete_board_btn = (Button) findViewById(R.id.delete_board_btn);
        final SharedPreferences board = getSharedPreferences("board", MODE_PRIVATE);
//        SharedPreferences list_count_save = getSharedPreferences("login_count", MODE_PRIVATE);
//        SharedPreferences.Editor edit_list_count = list_count_save.edit();
//


        //리스트뷰 게시판
        String list = "list";
        SharedPreferences list_i = getSharedPreferences("list_number_count", MODE_PRIVATE);
        final SharedPreferences list_name = getSharedPreferences("list_name", MODE_PRIVATE);
        SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
        SharedPreferences list_review = getSharedPreferences("list_review", MODE_PRIVATE);
        SharedPreferences list_write = getSharedPreferences("list_write", MODE_PRIVATE);
        SharedPreferences cart_on = getSharedPreferences("cart_on",MODE_PRIVATE);
        SharedPreferences total_list_cart = getSharedPreferences("list_cart_total",MODE_PRIVATE); //찜목록 저장 갯수와 번호순으로 저장

        SharedPreferences.Editor edit_total_cart = total_list_cart.edit();
        SharedPreferences.Editor edit_cart_on = cart_on.edit();
        SharedPreferences.Editor edit_list_i = list_i.edit();
        SharedPreferences.Editor edit_list_name = list_name.edit();
        SharedPreferences.Editor edit_list_esti = list_esti.edit();
        SharedPreferences.Editor edit_list_review = list_review.edit();
        SharedPreferences.Editor edit_list_write = list_write.edit();

        //SharedPreferences 초기화
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

        final ListView listview = (ListView) findViewById(R.id.list_sub);
//        final boardAdapter adapter = new boardAdapter(this, list_num, list_board, list_writer);
        listview.setAdapter(boardAdapter);

        call_value = list_i.getInt("list_number_count", 1);    //게시글 총 갯수 저장하는 변수

        for(list_count=1; list_count <= call_value; list_count=list_count+1){
            list_num.add(list_count);
            list_board.add(list_name.getString(String.valueOf(list_count),"no_content"));
            list_writer.add(list_write.getString(String.valueOf(list_count),"no_writer"));
            }

        for(int remove_num=call_value-1; remove_num >= 0; remove_num=remove_num-1){
            if(list_board.get(remove_num).toString() == "no_content" && list_writer.get(remove_num).toString() == "no_writer") {
                list_num.remove(remove_num);
                list_board.remove(remove_num);
                list_writer.remove(remove_num);
                boardAdapter.notifyDataSetChanged();
            }
        }//일단 저장이 되는데 조금 더 보완을 생각해봅시다!!!!!!


        list_cart_total = total_list_cart.getInt("list_cart_total", 1);

        //리스트뷰 찜 목록!!!!!!!!!!!!!!
        SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);
        for(list_cart_count=1; list_cart_count <= call_value; list_cart_count=list_cart_count+1){
            list_cart_num.add(list_cart_count);
            list_cart.add(list_id.getString(String.valueOf(list_cart_count),"no_cart"));
        }
        for( int remove_cart = call_value-1 ;   remove_cart >= 0 ; remove_cart=remove_cart-1){
            if(list_cart.get(remove_cart).toString()=="no_cart"){
                list_cart_num.remove(remove_cart);
                list_cart.remove(remove_cart);
                cartAdapter.notifyDataSetChanged();
            }
        }


        delete_board_btn.setOnClickListener(new View.OnClickListener() { //게시판 목록 삭제
            @Override
            public void onClick(View v) {

                count = boardAdapter.getCount();

                if(count > 0) {
                    checked = listview.getCheckedItemPosition();
                    if(checked > -1 && checked < count){
                        int list_delete_number = list_num.get(checked);
                        list_num.remove(checked);
                        list_board.remove(checked);
                        list_writer.remove(checked);

                        SharedPreferences list_i = getSharedPreferences("list_number_count", MODE_PRIVATE);
                        SharedPreferences list_name = getSharedPreferences("list_name", MODE_PRIVATE);
                        SharedPreferences list_esti = getSharedPreferences("list_esti", MODE_PRIVATE);
                        SharedPreferences list_review = getSharedPreferences("list_review", MODE_PRIVATE);
                        SharedPreferences list_write = getSharedPreferences("list_write", MODE_PRIVATE);

                        SharedPreferences.Editor edit_list_i = list_i.edit();
                        SharedPreferences.Editor edit_list_name = list_name.edit();
                        SharedPreferences.Editor edit_list_esti = list_esti.edit();
                        SharedPreferences.Editor edit_list_review = list_review.edit();
                        SharedPreferences.Editor edit_list_write = list_write.edit();

                        edit_list_name.remove(String.valueOf(list_delete_number));
                        edit_list_esti.remove(String.valueOf(list_delete_number));
                        edit_list_review.remove(String.valueOf(list_delete_number));
                        edit_list_write.remove(String.valueOf(list_delete_number));

                        edit_list_name.commit();
                        edit_list_esti.commit();
                        edit_list_review.commit();
                        edit_list_write.commit();

                        listview.clearChoices();
                        boardAdapter.notifyDataSetChanged();
                        Toast.makeText(SubMainActivity.this, list_delete_number+"번 항목을 삭제하셨습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //찜목록 탭에서 삭제 버튼 클릭 시
                count_cart = cartAdapter.getCount();
                if(count_cart >0){
                    check_cart = listview_cart.getCheckedItemPosition();
                    if(check_cart > -1 && check_cart < count_cart){
                        //찜목록에서 바로 찜 삭제하면 하트 지워짐
                        int i = list_cart_num.get(check_cart);
                        SharedPreferences cart_on = getSharedPreferences(String.valueOf(i),MODE_PRIVATE);
                        SharedPreferences.Editor edit_cart_on = cart_on.edit();
                        edit_cart_on.putInt(login_id, 0);
                        edit_cart_on.commit();
                        SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);
                        SharedPreferences.Editor edit_list_cart = list_id.edit();
                        edit_list_cart.remove(String.valueOf(i));
                        edit_list_cart.commit();

                        list_cart_num.remove(check_cart);
                        list_cart.remove(check_cart);

                        listview_cart.clearChoices();
                        cartAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = boardAdapter.getCount();

                if (count > 0){
                    checked = listview.getCheckedItemPosition();
                    if (checked > -1 && checked < count) {
                        Intent intent = new Intent(SubMainActivity.this, Board_modify_Activity.class);
                        int get_list_num = list_num.get(checked);

                        intent.putExtra("login_id", login_id);
                        intent.putExtra("checked", get_list_num);
                        startActivityForResult(intent, 20 );
                    }
                }
            }
        });

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
        if(requestCode == write_code && resultCode == 10){
            String[] send = data.getStringArrayExtra("send");
            SharedPreferences list_i = getSharedPreferences("list_number_count", MODE_PRIVATE);
            call_value = list_i.getInt("list_number_count",1);
            list_num.add(call_value);
            list_board.add(send[0]);
            list_writer.add(send[1]);
            boardAdapter.notifyDataSetChanged();

            SharedPreferences.Editor edit_list_i = list_i.edit();
            edit_list_i.putInt("list_number_count", call_value+1);
            edit_list_i.commit();
            Toast.makeText(this, "저장되었습니다.",Toast.LENGTH_SHORT).show();
        } else if(requestCode == 20 && resultCode ==20){
            String[] resend = data.getStringArrayExtra("resend");
            list_board.set(checked, resend[0]);
            list_writer.set(checked, resend[1]);
            boardAdapter.notifyDataSetChanged();

            Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();

        } else if(requestCode == 20 && resultCode == 22) {
            String cart = data.getStringExtra("cart");
            int cart_array = data.getIntExtra("checked", 0);

            SharedPreferences total_list_cart = getSharedPreferences("list_cart_total",MODE_PRIVATE);
            SharedPreferences.Editor edit_total_cart = total_list_cart.edit();
            list_cart_total = total_list_cart.getInt("list_cart_total", 1);
            list_cart_num.add(cart_array);
            list_cart.add(cart);

            edit_total_cart.putInt("list_cart_total",list_cart_total+1);
            cartAdapter.notifyDataSetChanged();
        } else if(requestCode == 20 && resultCode == 23) {
            SharedPreferences total_list_cart = getSharedPreferences("list_cart_total",MODE_PRIVATE);
            SharedPreferences list_id = getSharedPreferences(login_id+"_cart", MODE_PRIVATE);
            SharedPreferences.Editor edit_list_cart = list_id.edit();
            int cart_array = data.getIntExtra("checked", 0);
            edit_list_cart.remove(String.valueOf(cart_array));

            //항목 속 찜 버튼 해제 시
            list_cart.clear();
            list_cart_num.clear();
            cartAdapter.notifyDataSetChanged();


            for(list_cart_count=1; list_cart_count <= call_value; list_cart_count=list_cart_count+1){
                list_cart_num.add(list_cart_count);
                list_cart.add(list_id.getString(String.valueOf(list_cart_count),"no_cart"));
            }
            for( int remove_cart = call_value-1 ;   remove_cart >= 0 ; remove_cart=remove_cart-1){
                if(list_cart.get(remove_cart).toString()=="no_cart"){
                    list_cart_num.remove(remove_cart);
                    list_cart.remove(remove_cart);
                    cartAdapter.notifyDataSetChanged();
                }
            }

        } else {
                Toast.makeText(this, "저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        } // onActivityResult of End.
    }// SubMainActivity Class End.

    class boardAdapter extends BaseAdapter{
        Context context_board;
        ArrayList<Integer> list_num;
        ArrayList<String> list_board;
        ArrayList<String> list_writer;

        public boardAdapter(Context context, ArrayList<Integer> list_num, ArrayList<String> list_board, ArrayList<String> list_writer){
            this.context_board = context;
            this.list_num = list_num;
            this.list_board = list_board;
            this.list_writer = list_writer;
        }

        @Override
        public int getCount() {
            return list_num.size();
        }

        @Override
        public Object getItem(int position) {
            return list_num.get(position);
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
            TextView text_num = (TextView) convertView.findViewById(R.id.text_num);
            TextView text_name = (TextView) convertView.findViewById(R.id.text_name);
            TextView text_write = (TextView) convertView.findViewById(R.id.text_writer);
            text_num.setText(list_num.get(position).toString());
            text_name.setText(list_board.get(position));
            text_write.setText(list_writer.get(position));

        return convertView;
        }
    }
    class cartAdapter extends BaseAdapter{
        Context context_cart;
        ArrayList<String> list_cart;

        public cartAdapter(Context context,ArrayList<String> list_cart){
            this.context_cart = context;
            this.list_cart = list_cart;
        }
        @Override
        public int getCount() {
            return list_cart.size();
        }

        @Override
        public Object getItem(int position) {
            return list_cart.get(position);
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
            TextView text_name = (TextView) convertView.findViewById(R.id.text_name);
            text_name.setText(list_cart.get(position));

            return convertView;
        }
    }