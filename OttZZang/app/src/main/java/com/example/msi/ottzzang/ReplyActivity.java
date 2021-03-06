package com.example.msi.ottzzang;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class ReplyActivity extends AppCompatActivity {

    ArrayList<ReplyItem> replyItems = new ArrayList<>();
    ArrayList<Integer> re_i = new ArrayList<>();

    replyAdapter replyAdapter = new replyAdapter(this, R.layout.listview_reply, replyItems);

    String login_id;
    String idee; // 댓글의 아이디 저장하는 변수
    int check;
    int total_i;

    int check_position_listnum;
    int posi;
    int delete_authority;
    int bad_reply;

    EditText re_txt;
    ImageView pro_img;
    TextView id_txt;
    SharedPreferences txt;

    SharedPreferences img;
    SharedPreferences re_id;
    SharedPreferences id_check;
    SharedPreferences total;
    SharedPreferences size;
    SharedPreferences point;
    SharedPreferences user_autho;


    SharedPreferences.Editor edit_user_autho;
    SharedPreferences.Editor edit_point;
    SharedPreferences.Editor edit_size;
    SharedPreferences.Editor edit_txt;
    SharedPreferences.Editor edit_img;
    SharedPreferences.Editor edit_re_id;

    public Bitmap StringToBitMap(String encodedString){ // 스트링으로 받은 이미지를 비트맵으로 다시 변환
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void dialog_delete_autho(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ReplyActivity.this);
        builder.setTitle("포인트를 사용하여 댓글을 삭제하시겠습니까?");
        builder.setMessage("100 point 차감");
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit_txt.remove(""+check_position_listnum).commit();
                edit_img.remove(""+check_position_listnum).commit();
                edit_re_id.remove(""+check_position_listnum).commit();
                re_i.remove(posi);

                replyItems.remove(posi);
                int size_item = replyItems.size();
                edit_size.putInt(check+"size",size_item).commit();

                int user_point = point.getInt(login_id,0)-100;
                edit_point.putInt(login_id,user_point).commit();
                Toast.makeText(ReplyActivity.this, "포인트를 차감하여 삭제하였습니다.",Toast.LENGTH_SHORT).show();

                dialog_nowrite();
                replyAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }
    private void dialog_nowrite(){      // 더 이상 이 게시글에 댓글을 달 수 없게 함
        AlertDialog.Builder builder = new AlertDialog.Builder(ReplyActivity.this);
        builder.setTitle("신고하기");
        builder.setMessage("댓글작성자가 더 이상 이 게시물에 댓글을 작성할 수 없게 할까요?");
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int x =user_autho.getInt("total_autho",1);
                edit_user_autho.putString(""+user_autho.getInt(""+x,1),idee).commit();
                x= x+1;
                edit_user_autho.putInt("total_autho",x).commit();
                Toast.makeText(ReplyActivity.this,"작성자는 더 이상 이 게시물에 글을 작성할 수 없습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
    private void dialog_delete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ReplyActivity.this);
        builder.setTitle("삭제하시겠습니까?");
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edit_txt.remove(""+check_position_listnum).commit();
                edit_img.remove(""+check_position_listnum).commit();
                edit_re_id.remove(""+check_position_listnum).commit();
                re_i.remove(posi);

                replyItems.remove(posi);
                int size_item = replyItems.size();
                edit_size.putInt(check+"size",size_item).commit();

                replyAdapter.notifyDataSetChanged();
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
            /*Stetho----------------------------*/
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();




        /*ReplyActivity----------------------------*/
        Button reply_btn = (Button) findViewById(R.id.reply_save_btn);
        re_txt = (EditText) findViewById(R.id.re_write_txt);
        pro_img = (ImageView) findViewById(R.id.profile_img);
        id_txt = (TextView) findViewById(R.id.id_txt);

        Intent intent = getIntent();
        login_id = intent.getStringExtra("login_id");
        check = intent.getIntExtra("checked",0);
        delete_authority = intent.getIntExtra("delete_authority",0);

        id_check = getSharedPreferences(check+"id_check",MODE_PRIVATE);
        final SharedPreferences.Editor edit_id_check = id_check.edit();
        txt = getSharedPreferences(check+"_reply",MODE_PRIVATE);
        edit_txt = txt.edit();
        img = getSharedPreferences(check+"_reimg",MODE_PRIVATE);
        edit_img = img.edit();
        re_id = getSharedPreferences(check+"_reid",MODE_PRIVATE);
        edit_re_id = re_id.edit();
        total = getSharedPreferences("total",MODE_PRIVATE);
        final SharedPreferences.Editor edit_total = total.edit();
        size = getSharedPreferences("size",MODE_PRIVATE);
        edit_size = size.edit();
        total_i = total.getInt(check+"total",0);
        point = getSharedPreferences("point", MODE_PRIVATE);
        edit_point = point.edit();
        user_autho = getSharedPreferences(check+"autho",MODE_PRIVATE);
        edit_user_autho = user_autho.edit();

        /*Shared 초기화*/
//        edit_id_check.clear().commit();
//        edit_img.clear().commit();
//        edit_re_id.clear().commit();
//        edit_total.clear().commit();
//        edit_txt.clear().commit();
//        edit_size.clear().commit();
//        edit_user_autho.clear().commit();

         /*작성 권한 체크*/
        bad_reply =0;
        for(int ch =1 ; ch<= user_autho.getInt("total_autho",1); ch=ch+1){
            String idc=user_autho.getString(""+ch,"empty");
            if( login_id.equals(idc)){
                Toast.makeText(ReplyActivity.this, "당신은 더 이상 이 게시글에 댓글을 작성할 수 없습니다.", Toast.LENGTH_SHORT).show();
                bad_reply=1;
            }
        }
        if(delete_authority==1){
            Toast.makeText(ReplyActivity.this, "당신의 게시물입니다. 포인트를 이용해서 악성 댓글을 지울 수 있습니다.", Toast.LENGTH_SHORT).show();
        }

        for(int Li =0; Li <= total_i-1; Li = Li+1){
            String txt_rep = txt.getString(""+Li, "no_text");

            Bitmap bitmap;
            String bit = img.getString(""+Li,"");
            if(bit ==""){
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.profile_btn);
            } else {
                bitmap = StringToBitMap(bit);
            }
            String id_rep = re_id.getString(""+Li,"no_id");
            String id = id_check.getString(""+Li,"no_ID");
            ReplyItem replyItem = new ReplyItem(txt_rep, bitmap, id_rep, id);
            re_i.add(Li);
            replyItems.add(replyItem);
        }
        for(int remove_num = total_i-1; remove_num >=0; remove_num= remove_num-1){
            if(replyItems.get(remove_num).getReplytxt()=="no_text" && replyItems.get(remove_num).getRe_id()=="no_id"){
                re_i.remove(remove_num);
                replyItems.remove(remove_num);
            }
        }

       /*Shared 공간*/
        SharedPreferences profileimg = getSharedPreferences("profileimg",MODE_PRIVATE);
        final SharedPreferences.Editor edit_profileimg = profileimg.edit();
        SharedPreferences name = getSharedPreferences("name", MODE_PRIVATE);
        final SharedPreferences.Editor edit_name = name.edit();

        /* replyActivity에서 프로필이미지와 이름을 띄워줌*/
        Bitmap bitmap;
        String strimg = profileimg.getString(login_id,"");
        if (strimg == ""){
            pro_img.setImageResource(R.drawable.profile_btn);
        } else {
            bitmap = StringToBitMap(strimg);
            pro_img.setImageBitmap(bitmap);
        }
        id_txt.setText(name.getString(login_id,""));

        final ListView listView_re = (ListView) findViewById(R.id.listview_reply);
        listView_re.setAdapter(replyAdapter);



        listView_re.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posi = position;
                check_position_listnum = re_i.get(position);
                idee = replyItems.get(position).getId_check();

                if(login_id.equals(idee)){
                   dialog_delete();
                } else if (delete_authority ==1){
                    if(point.getInt(login_id,0)>=100){
                        dialog_delete_autho();
                    }
                } else {
                    Toast.makeText(ReplyActivity.this, "삭제 권한이 없습니다.",Toast.LENGTH_SHORT).show();
                }

                replyAdapter.notifyDataSetChanged();
                return true;

            }
        });

        reply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bad_reply ==1) {
                    Toast.makeText(ReplyActivity.this, "작성자의 요청으로 게시글에 댓글을 작성할 수 없습니다", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (re_txt.getText().toString().equals("")) {
                        Toast.makeText(ReplyActivity.this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        String retxt = re_txt.getText().toString();
                        Bitmap bitmap = ((BitmapDrawable) pro_img.getDrawable()).getBitmap();
                        String str_bitmap = BitMapToString(bitmap);
                        String reply_name = id_txt.getText().toString();

                        edit_txt.putString("" + total_i, retxt).commit();
                        edit_img.putString("" + total_i, str_bitmap).commit();
                        edit_re_id.putString("" + total_i, reply_name).commit();
                        edit_id_check.putString("" + total_i, login_id).commit();
                        ReplyItem reItem = new ReplyItem(retxt, bitmap, reply_name, login_id);
                        replyItems.add(reItem);
                        re_i.add(total_i);
                        total_i = total_i + 1;

                        int size_item = replyItems.size();
                        edit_size.putInt(check + "size", size_item).commit();
                        edit_total.putInt(check + "total", total_i).commit();

                        re_txt.setText("");
                        Toast.makeText(ReplyActivity.this, "댓글이 저장되었습니다.", Toast.LENGTH_SHORT).show();
                        replyAdapter.notifyDataSetChanged();
                    }


                }
            }
        });
    }


    public class replyAdapter extends BaseAdapter{
        Context context;
        private LayoutInflater inflater;
        public ArrayList<ReplyItem> replyItems;
        private int layout;

        public replyAdapter(Context context, int layout, ArrayList<ReplyItem> replyItems){
            this.context = context;
            this.replyItems = replyItems;
            this.layout = layout;
        }


        @Override
        public int getCount() {
            return replyItems.size();
        }

        @Override
        public Object getItem(int position) {
            return replyItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                this.inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layout, parent, false);
            }
            ReplyItem replyItem = replyItems.get(position);

            TextView retxt = (TextView) convertView.findViewById(R.id.list_re_txt);
            retxt.setText(replyItem.getReplytxt());

            ImageView reimg = (ImageView) convertView.findViewById(R.id.list_re_img);
            reimg.setImageBitmap(replyItem.getRe_pro_img());

            TextView re_id = (TextView) convertView.findViewById(R.id.list_id);
            re_id.setText(replyItem.getRe_id());

            return convertView;
        }
    }
}

