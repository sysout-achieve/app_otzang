package com.example.msi.ottzzang;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Example3Activity extends AppCompatActivity {

        private String mCurrentPhotoPath;
        int camera_code = 10;
        int gallery_code = 20;

        int total_i;
        String login_id;

        int mDegree;

    void requirePermission(){//권한 요청 메소드
        String [] permissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.INTERNET};
        ArrayList<String> listPermissionsNeeded = new ArrayList<>();

        for(String permission :permissions){
            if(ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_DENIED){
                listPermissionsNeeded.add(permission);  //권한이 허가가 안됐을 경우 요청할 권한을 모집
            }
        }
        if(!listPermissionsNeeded.isEmpty()){        //권한을 요청함
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1);
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public Bitmap rotateImg(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example3);

        Intent intent = getIntent();
        login_id = intent.getStringExtra("login_id");

        requirePermission();

        //아이디별 아우터 목록 저장하기 위한 Sharedpreferences
        final SharedPreferences total_list = getSharedPreferences(login_id+"_outer_total", MODE_PRIVATE);
        SharedPreferences outer_id_img = getSharedPreferences(login_id+"_outer_img", MODE_PRIVATE);
        SharedPreferences outer_id_title = getSharedPreferences(login_id+"_outer_title", MODE_PRIVATE);
        SharedPreferences outer_id_size = getSharedPreferences(login_id+"_outer_size", MODE_PRIVATE);
        SharedPreferences outer_id_length = getSharedPreferences(login_id+"_outer_length", MODE_PRIVATE);
        SharedPreferences outer_id_width = getSharedPreferences(login_id+"_outer_width", MODE_PRIVATE);
        SharedPreferences outer_id_chest = getSharedPreferences(login_id+"_outer_chest", MODE_PRIVATE);
        SharedPreferences outer_id_sleev = getSharedPreferences(login_id+"_outer_sleev", MODE_PRIVATE);
        SharedPreferences outer_id_phone = getSharedPreferences(login_id+"_outer_phone", MODE_PRIVATE);
        SharedPreferences outer_id_link = getSharedPreferences(login_id+"_outer_link", MODE_PRIVATE);
        final SharedPreferences.Editor edit_total_list =total_list.edit();
        final SharedPreferences.Editor edit_img = outer_id_img.edit();
        final SharedPreferences.Editor edit_title = outer_id_title.edit();
        final SharedPreferences.Editor edit_size = outer_id_size.edit();
        final SharedPreferences.Editor edit_length = outer_id_length.edit();
        final SharedPreferences.Editor edit_width = outer_id_width.edit();
        final SharedPreferences.Editor edit_chest = outer_id_chest.edit();
        final SharedPreferences.Editor edit_sleev = outer_id_sleev.edit();
        final SharedPreferences.Editor edit_phone = outer_id_phone.edit();
        final SharedPreferences.Editor edit_link = outer_id_link.edit();

        //모든 쉐어드 초기화
//        edit_total_list.clear().commit();
//        edit_img .clear().commit();
//        edit_title.clear().commit();
//        edit_size.clear().commit();
//        edit_length.clear().commit();
//        edit_width.clear().commit();
//        edit_chest.clear().commit();
//        edit_sleev.clear().commit();
//        edit_phone.clear().commit();
//        edit_link.clear().commit();

        total_i = total_list.getInt(login_id, 1);

        Button camera_btn = (Button) findViewById(R.id.camera_btn);
        final ImageView picture = (ImageView) findViewById(R.id.picture_upda);
        final EditText length00 = (EditText) findViewById(R.id.length00);
        final EditText item1 = (EditText) findViewById(R.id.item1);
        final EditText length_height = (EditText) findViewById(R.id.length11);
        final EditText length_width = (EditText) findViewById(R.id.length22);
        final EditText length_chest = (EditText) findViewById(R.id.length33);
        final EditText length_sleeve = (EditText) findViewById(R.id.length44);
        TableLayout table = (TableLayout) findViewById(R.id.table);
        final EditText phone_num = (EditText) findViewById(R.id.phone_num);
        final EditText link_text = (EditText) findViewById(R.id.link_text);
        Button call_btn = (Button) findViewById(R.id.call_btn);
        Button link_btn = (Button) findViewById(R.id.link_btn);
        Button save_btn = (Button) findViewById(R.id.save_btn_profile);
        Button image_btn = (Button) findViewById(R.id.image_btn);
        ImageView rot_btn_3 = (ImageView) findViewById(R.id.rot_btn_3);

            Toast.makeText(Example3Activity.this, "제품의 정보를 저장하여 인터넷 쇼핑에 활용해보세요.", Toast.LENGTH_SHORT).show();

        rot_btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) picture.getDrawable()).getBitmap();
                mDegree = mDegree+90;
                picture.setImageBitmap(rotateImg(bitmap, mDegree));
            }
        });

        image_btn.setOnClickListener(new View.OnClickListener() { //이미지 추가 버튼 클릭시
            @Override
            public void onClick(View v) {
                boolean write = ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_GRANTED;

                if (write){
                    pickPickure();
                } else {
                    Toast.makeText(Example3Activity.this, "앨범에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //카메라와 쓰기 권한 체크 및 허가의 경우에만 카메라 버튼을 이용 가능
                boolean camera = ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                boolean write = ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_GRANTED;

                if(camera && write){
                    Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        File photofile = createImageFile();
                        Uri photouri = FileProvider.getUriForFile(Example3Activity.this, "com.example.msi.ottzzang.fileprovider",photofile);
                        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
                        startActivityForResult(intent_camera, camera_code); //카메라와 저장공간 허가 시 requestCode 0 을 가지고 ActivityForResult 생성
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(Example3Activity.this, "카메라와 쓰기에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean phone = ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
                if(phone){
                    String number = phone_num.getText().toString();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+number));
                    startActivity(intent);
                } else {
                    Toast.makeText(Example3Activity.this, "전화에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        link_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = link_text.getText().toString();
                Intent intent_link = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent_link);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {  //저장하기 버튼 눌렀을 때 Activityforresult 반환
            @Override
            public void onClick(View v) {

                String item_name = item1.getText().toString();
                String size = length00.getText().toString();
                String length = length_height.getText().toString();
                String width = length_width.getText().toString();
                String chest = length_chest.getText().toString();
                String sleev = length_sleeve.getText().toString();
                String phone = phone_num.getText().toString();
                String link = link_text.getText().toString();

                if( item_name.equals("") ) {
                    Toast.makeText(Example3Activity.this, "제품명을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if( size.equals("") ){
                    Toast.makeText(Example3Activity.this, "size를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Bitmap bitmap = ((BitmapDrawable) picture.getDrawable()).getBitmap();
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 300, 400, true);

                    //이미지를 쉐어드에 스트링으로 변환하여 저장
                    String img = BitMapToString(resized);
                    edit_img.putString(String.valueOf(total_i), img);
                    edit_img.commit();

                    //항목을 모두 쉐어드에 저장
                    edit_title.putString(String.valueOf(total_i), item_name).commit();
                    edit_size.putString(String.valueOf(total_i), size).commit();
                    edit_length.putString(String.valueOf(total_i), length).commit();
                    edit_width.putString(String.valueOf(total_i), width).commit();
                    edit_chest.putString(String.valueOf(total_i), chest).commit();
                    edit_sleev.putString(String.valueOf(total_i), sleev).commit();
                    edit_phone.putString(String.valueOf(total_i), phone).commit();
                    edit_link.putString(String.valueOf(total_i), link).commit();

                    Intent intent_save = new Intent();
                    String[] send = new String[]{item_name, size};

                    intent_save.putExtra("send2", send);
                    intent_save.putExtra("bm", (Bitmap) resized);
                    setResult(5, intent_save);
                    finish();
                }
            }
        });
    } //onCreate of End.

    private File createImageFile() throws IOException { //https://developer.android.com/training/camera/photobasics.html 라이브러리 이용
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == camera_code){
            ImageView picture = (ImageView) findViewById(R.id.picture_upda);
            picture.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
        if(requestCode == gallery_code && resultCode == RESULT_OK){
            Uri uri = data.getData();
            ImageView picture = (ImageView) findViewById(R.id.picture_upda);
            picture.setImageURI(uri);
        }
    }

    void pickPickure(){
        Intent intent = new Intent(android.content.Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, gallery_code);
    }
}
