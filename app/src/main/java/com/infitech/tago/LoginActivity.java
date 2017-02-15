package com.infitech.tago;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        UsernameEt=(EditText)findViewById(R.id.etUserName);
        PasswordEt=(EditText)findViewById(R.id.etPassword);
    }
    public void OnLogin(View view){
        String username=UsernameEt.getText().toString();
        String password=PasswordEt.getText().toString();
        String type="Login";
        BackgroundWorker backgroundWorker=new BackgroundWorker(this);
       backgroundWorker.execute(type,username,password);
        //new BackgroundWorker().execute(type, username,password);

    }
    public void OpenReg(View view){
        startActivity(new Intent(this, Register.class));
    }
}

