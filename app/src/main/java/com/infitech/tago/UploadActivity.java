package com.infitech.tago;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
//import java.util.jar.Manifest;

import android.content.Intent;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadActivity extends AppCompatActivity {
    private Button button;
    String LoggedInName,res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        button=(Button) findViewById(R.id.button);

        LoggedInName=getIntent().getExtras().getString("LoggedInName");
        Log.i("login7", LoggedInName);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
                return;
            }
        }
        enable_button();

    }
    private void enable_button(){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new MaterialFilePicker()
                        .withActivity(UploadActivity.this)
                        .withRequestCode(10)
                        .start();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        if(requestCode==100 && (grantResults[0]==PackageManager.PERMISSION_GRANTED)) {
            enable_button();
        }
        else{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
    }

}
    ProgressDialog progress;
    @Override
protected void onActivityResult(int requestCode, int resultCode,final Intent data){
        if(requestCode==10 && resultCode==RESULT_OK){
            progress = new ProgressDialog(UploadActivity.this);
            progress.setTitle("Copying");
            progress.setMessage("Please wait... :)");
            progress.show();
            Thread t=new Thread(new Runnable(){
                @Override
                public void run(){
                    File f =new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    String content_type=getMimeType(f.getPath());
                    OkHttpClient client=new OkHttpClient();
                    String file_path=f.getAbsolutePath();
                    RequestBody file_body=RequestBody.create(MediaType.parse(content_type),f);

                    RequestBody request_body=new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("type",content_type)
                            .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1),file_body)
                            .addFormDataPart("name",LoggedInName)
                            .build();

                    Request request=new Request.Builder()
                            .url("http://192.168.0.115/Tago/upload.php")
                            .post(request_body)
                            .build();
                    try{

                        Response response=client.newCall(request).execute();
                        res=response.body().string();
                        Log.d("TAG", res);
                        if(!response.isSuccessful()){
                            throw new IOException("Error: "+response);


                        }

                        progress.dismiss();

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
            t.start();

              /*  Context context = getApplicationContext();
                CharSequence text = "Copied!!!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                */


        }
    }
private String getMimeType(String path){
    String extension= MimeTypeMap.getFileExtensionFromUrl(path);
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
}
}