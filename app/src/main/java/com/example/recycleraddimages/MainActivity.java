package com.example.recycleraddimages;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 100;
    private static final int RESULT_LOAD_IMAGE = 101;
    ArrayList<String> arrayList;
    ImageRecyclerViewAdapter imageAdapter;
    Button add;
    RecyclerView imageRecyclerView;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.addImage);
        imageRecyclerView = findViewById(R.id.imageRecyclerView);
        context = MainActivity.this;
        arrayList = new ArrayList<>();

       // arrayList.add("https://www.google.com/imgres?imgurl=https%3A%2F%2Fimg.etimg.com%2Fthumb%2Fheight-480%2Cwidth-640%2Cimgsize-126503%2Cmsid-61166425%2F.jpg&imgrefurl=https%3A%2F%2Fm.economictimes.com%2Fnews%2Fsports%2Fwhy-after-13-years-mahendra-singh-dhoni-is-still-not-out%2Farticleshow%2F61166379.cms&docid=OvyeWOxHCXxAnM&tbnid=dFBakaZ4Ob-wBM%3A&vet=10ahUKEwjXuqOljubhAhXFPI8KHbGWB9QQMwhpKAEwAQ..i&w=640&h=480&bih=633&biw=1366&q=dhoni%20photos&ved=0ahUKEwjXuqOljubhAhXFPI8KHbGWB9QQMwhpKAEwAQ&iact=mrc&uact=8");
        //arrayList.add("https://www.google.com/imgres?imgurl=https%3A%2F%2Fimages.in.com%2Fuploads%2F2019%2F03%2Fdhoni-2.jpg%3Fver%3D0.2&imgrefurl=https%3A%2F%2Fwww.in.com%2Fsports%2Fcricket%2Fms-dhoni-in-roar-of-the-lion-heres-why-thala-doesnt-give-interviews-anymore-347551.htm&docid=1y9fWZa9m3qEsM&tbnid=qqEgqwQ3jq8BkM%3A&vet=10ahUKEwjXuqOljubhAhXFPI8KHbGWB9QQMwhqKAIwAg..i&w=1280&h=720&bih=633&biw=1366&q=dhoni%20photos&ved=0ahUKEwjXuqOljubhAhXFPI8KHbGWB9QQMwhqKAIwAg&iact=mrc&uact=8");
        //arrayList.add("https://www.google.com/imgres?imgurl=https%3A%2F%2Fimg.etimg.com%2Fthumb%2Fmsid-65041695%2Cwidth-300%2Cimgsize-226571%2Cresizemode-4%2Fdhoni-bccl.jpg&imgrefurl=https%3A%2F%2Feconomictimes.indiatimes.com%2Fnews%2Fsports%2Fmahendra-singh-dhoni-seeks-match-ball-sets-speculation-swirling-on-future%2Farticleshow%2F65041672.cms&docid=2wUQd8aFX51TaM&tbnid=3mMPL231dI7JNM%3A&vet=10ahUKEwjXuqOljubhAhXFPI8KHbGWB9QQMwhxKAgwCA..i&w=300&h=225&bih=633&biw=1366&q=dhoni%20photos&ved=0ahUKEwjXuqOljubhAhXFPI8KHbGWB9QQMwhxKAgwCA&iact=mrc&uact=8");
        ViewGroup.LayoutParams params = imageRecyclerView.getLayoutParams();
        params.height = 500;
        imageRecyclerView.setLayoutParams(params);
        imageAdapter = new ImageRecyclerViewAdapter(arrayList, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        imageRecyclerView.setLayoutManager(linearLayoutManager);
        imageRecyclerView.setAdapter(imageAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();

            }
        });
    }

    private void captureImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            dialogShowPhoto();

        }

    }


    public void dialogShowPhoto() {
        String takephoto = "Take Photo";
        String chooseFromLibrary = "Choose from Gallery";
        String cancel = "cancel";
        String addPhoto = "add photo";
        final CharSequence[] items = {takephoto, chooseFromLibrary, cancel};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle(addPhoto);
        final String finalTakephoto = takephoto;
        final String finalChooseFromLibrary = chooseFromLibrary;
        final String finalCancel = cancel;
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(finalTakephoto)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals(finalChooseFromLibrary)) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, RESULT_LOAD_IMAGE);
                } else if (items[item].equals(finalCancel)) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // selectedImage.setImageBitmap(photo);
            Matrix mat = new Matrix();
            mat.postRotate(Integer.parseInt("270"));
            Bitmap bMapRotate = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), mat, true);

            arrayList.add(getPath(context, data.getData()));
            imageAdapter.notifyDataSetChanged();


        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageURI = data.getData();
            arrayList.add(getPath(context, data.getData()));
            imageAdapter.notifyDataSetChanged();


        }


    }
    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

}
