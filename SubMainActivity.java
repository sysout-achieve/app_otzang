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

import java.util.ArrayList;
import java.util.List;

public class SubMainActivity extends AppCompatActivity {
    int count;
    int checked;
    int call_value = 11;
    int write_code = 101;
    int check_cart = 0;
    int count_cart;
    String login_id;
    int list_count=0;
    int list_count_check=0;

    ArrayList<Integer> list_num = new ArrayList<>();
    ArrayList<String> list_board = new ArrayList<>();
    ArrayList<String> list_writer= new ArrayList<>();
    ArrayList<String> list_cart = new ArrayList<>();


    boardAdapter boardAdapter = new boardAdapter(this, list_num, list_board, list_writer);
    cartAdapter cartAdapter = new cartAdapter(this, list_cart);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submain);


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
        SharedPreferences list_count_save = getSharedPreferences("login_count", MODE_PRIVATE);
        SharedPreferences.Editor edit_list_count = list_count_save.edit();
        edit_list_count.putInt(login_id, list_count_check);


        for( list_count=1 ;list_count <= list_count_check ; list_count=list_count+1 ){
            if( board.contains(login_id+list_count) == true ){
                if(list_cart.contains(board.getString(login_id+list_count,"noitem"))){

                } else {
                    list_cart.add(board.getString(login_id+list_count, ""));
                    list_count = list_count+1;
                    cartAdapter.notifyDataSetChanged();
                }
            }
        }

        list_num.add(1);
        list_num.add(2);
        list_num.add(3);
        list_num.add(4);
        list_num.add(5);
        list_num.add(6);
        list_num.add(7);
        list_num.add(8);
        list_num.add(9);
        list_num.add(10);

//        list_num.add("1");
//        list_num.add("2");
//        list_num.add("3");
//        list_num.add("4");
//        list_num.add("5");
//        list_num.add("6");
//        list_num.add("7");
//        list_num.add("8");
//        list_num.add("9");
//        list_num.add("10");

        list_board.add("맨투맨 편해");
        list_board.add("이 옷 진짜 예뻐요");
        list_board.add("**쇼핑몰 좋아요~");
        list_board.add("삼선 슬리퍼 엄청 편해요");
        list_board.add("츄리닝은 아디다스");
        list_board.add("이 옷이 좋아서 매일 입어요");
        list_board.add("옷 재질이 너무 마음에 들어요");
        list_board.add("가성비 대박 아이템!!");
        list_board.add("여기 옷 사지말아요 후회합니다");
        list_board.add("코딩할 땐 후드티가 짱이야");

        list_writer.add("전지웅");
        list_writer.add("장광국");
        list_writer.add("박지호");
        list_writer.add("이기섭");
        list_writer.add("김우형");
        list_writer.add("남현수");
        list_writer.add("허건");
        list_writer.add("기계인간");
        list_writer.add("노프");
        list_writer.add("수형파트장");

        final ListView listview_cart = (ListView) findViewById(R.id.list_cart);
        listview_cart.setAdapter(cartAdapter);

        final ListView listview = (ListView) findViewById(R.id.list_sub);
//        final boardAdapter adapter = new boardAdapter(this, list_num, list_board, list_writer);
        listview.setAdapter(boardAdapter);


        delete_board_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count = boardAdapter.getCount();

                if(count > 0) {
                    checked = listview.getCheckedItemPosition();
                    if(checked > -1 && checked < count){
                        list_num.remove(checked);
                        list_board.remove(checked);
                        list_writer.remove(checked);

                        listview.clearChoices();
                        boardAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //장바구니 탭에서 삭제 버튼 클릭 시
                count_cart = cartAdapter.getCount();
                if(count_cart >0){
                    check_cart = listview_cart.getCheckedItemPosition();
                    if(check_cart > -1 && check_cart < count_cart){
                        list_cart.remove(check_cart);

                        listview_cart.clearChoices();
                        cartAdapter.notifyDataSetChanged();
                        SharedPreferences.Editor edit_board = board.edit();
                        edit_board.remove(login_id+check_cart);         // 로그인아이디랑 숫자써서 억지로 리스트뷰 우겨넣음 arraylist이용해서 리스트뷰 정렬하는 것 고민해볼것
                        edit_board.commit();
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

                        String[] renew = new String[]{list_board.get(checked), list_writer.get(checked)};
                        intent.putExtra("renew", renew);
                        intent.putExtra("login_id", login_id);
                        intent.putExtra("checked", checked);
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
                startActivity(intent);
            }


        });

    } // onCreate End.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == write_code && resultCode == 10){
            String[] send = data.getStringArrayExtra("send");

            list_num.add(call_value);
            list_board.add(send[0]);
            list_writer.add(send[1]);
            boardAdapter.notifyDataSetChanged();
            call_value= call_value+1;
        } else if(requestCode == 20 && resultCode ==20){
            String[] resend = data.getStringArrayExtra("resend");
            list_board.set(checked, resend[0]);
            list_writer.set(checked, resend[1]);
            boardAdapter.notifyDataSetChanged();
            Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show();

        } else if(requestCode == 20 && resultCode == 22) {
            SharedPreferences board = getSharedPreferences("board", MODE_PRIVATE);
            SharedPreferences.Editor edit_board = board.edit();

            String cart = data.getStringExtra("cart");
            list_cart.add(cart);
            edit_board.putString(login_id+list_count, cart);
            edit_board.commit();
            list_count_check= list_count_check+1;
            list_count = list_count+1;

            cartAdapter.notifyDataSetChanged();
            Toast.makeText(this, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show();
        } else{
                Toast.makeText(this, "저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

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


