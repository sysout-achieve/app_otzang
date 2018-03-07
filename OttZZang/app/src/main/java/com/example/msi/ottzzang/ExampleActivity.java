package com.example.msi.ottzzang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExampleActivity extends AppCompatActivity {

   private String mCurrentPhotoPath;

    int camera_code = 10;
    int gallery_code = 20;
    int mDegree =0;
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

    public Bitmap rotateImg(Bitmap bitmap, float degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(),bitmap.getHeight(),matrix,true);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        requirePermission();

        Intent intent = getIntent();
        int create_times = intent.getIntExtra("outer_times", 0);
        if(create_times==0) {
            Toast.makeText(ExampleActivity.this, "제품의 정보를 저장하여 인터넷 쇼핑에 활용해보세요.", Toast.LENGTH_LONG).show();
        }


        Button camera_btn = (Button) findViewById(R.id.camera_btn);
        final ImageView picture = (ImageView) findViewById(R.id.picture1_ex);
        final EditText item1 = (EditText) findViewById(R.id.item_example_ex);
        TableLayout table = (TableLayout) findViewById(R.id.table);
        final EditText phone_num = (EditText) findViewById(R.id.phone_num_ex);
        final EditText link_text = (EditText) findViewById(R.id.link_text_ex);
        Button call_btn = (Button) findViewById(R.id.call_btn);
        Button link_btn = (Button) findViewById(R.id.link_btn);
        Button save_btn = (Button) findViewById(R.id.save_btn_profile);
        Button image_btn = (Button) findViewById(R.id.image_btn);
        ImageView btn = (ImageView) findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDegree = mDegree+90;
                picture.setImageBitmap(rotateImg(BitmapFactory.decodeResource(getResources(),(R.drawable.outer1)),mDegree));
            }
        });

        image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean write = ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_GRANTED;

                if (write){
                    pickPickure();
                } else {
                    Toast.makeText(ExampleActivity.this, "앨범에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {       //카메라와 쓰기 권한 체크 및 허가의 경우에만 카메라 버튼을 이용 가능
                boolean camera = ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
                boolean write = ContextCompat.checkSelfPermission(v.getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_GRANTED;

                if(camera && write){
                    Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    try {
                        File photofile = createImageFile();
                        Uri photouri = FileProvider.getUriForFile(ExampleActivity.this, "com.example.msi.ottzzang.fileprovider",photofile);
                        intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
                        startActivityForResult(intent_camera, camera_code); //카메라와 저장공간 허가 시 requestCode 0 을 가지고 ActivityForResult 생성
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(ExampleActivity.this, "카메라와 쓰기에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ExampleActivity.this, "전화에 대한 권한을 얻지 못하였습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });


//        click_call(call_btn, phone_num);

        link_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = link_text.getText().toString();
                Intent intent_link = new Intent(Intent.ACTION_VIEW,Uri.parse(link));
                startActivity(intent_link);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {  //저장하기 버튼 눌렀을 때 Activityforresult 반환
            @Override
            public void onClick(View v) {

                String item_name = item1.getText().toString();
                Intent intent_save = new Intent();
                intent_save.putExtra("item_name",item_name);
                setResult(1, intent_save);
                finish();
            }
        });
    } //onCreate of End

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
            ImageView picture = (ImageView) findViewById(R.id.picture1_ex);
            picture.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
        }
        if(requestCode == gallery_code && resultCode == RESULT_OK){
            Uri uri = data.getData();
            ImageView picture = (ImageView) findViewById(R.id.picture1_ex);
            picture.setImageURI(uri);
        }
    }


    void pickPickure(){ //이미지를 앨범에서 어플로 intent를 통해 가져오는 메소드
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, gallery_code);
    }
}
