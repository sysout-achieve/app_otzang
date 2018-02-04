package com.example.msi.ottzzang;

import android.content.Context;
import android.content.Intent;
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
    ArrayList<Integer> list_num = new ArrayList<>();
    ArrayList<String> list_board = new ArrayList<>();
    ArrayList<String> list_writer= new ArrayList<>();
    boardAdapter boardAdapter = new boardAdapter(this, list_num, list_board, list_writer);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submain);


        Intent intent = getIntent();
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

        TabHost.TabSpec spec3 = tabs.newTabSpec("장바구니");
        spec3.setIndicator("장바구니");
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

        list_board.add("내 옷이 짱1");
        list_board.add("내 옷이 짱2");
        list_board.add("내 옷이 짱3");
        list_board.add("내 옷이 짱4");
        list_board.add("내 옷이 짱5");
        list_board.add("내 옷이 짱6");
        list_board.add("내 옷이 짱7");
        list_board.add("내 옷이 짱8");
        list_board.add("내 옷이 짱9");
        list_board.add("내 옷이 짱10");

        list_writer.add("작성1");
        list_writer.add("작성2");
        list_writer.add("작성3");
        list_writer.add("작성4");
        list_writer.add("작성5");
        list_writer.add("작성6");
        list_writer.add("작성7");
        list_writer.add("작성8");
        list_writer.add("작성9");
        list_writer.add("작성10");

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
                        startActivityForResult(intent, 20 );
                    }

                }
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //삭제 버튼에 대한 처리는 이루어지지 않았음
            }
        });

        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_write = new Intent(v.getContext(), BoardActivity.class);
                startActivityForResult(intent_write, write_code);  //새로운 화면을 띄우는 것이 목적이 아님, 정보를 처리하여 원래의 액티비티에 정보를 처리하는 것이 목적

            }
        });

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(v.getContext(), ProfileActivity.class);
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

        } else {
            Toast.makeText(this, "저장되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public class boardAdapter extends BaseAdapter{
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

}
