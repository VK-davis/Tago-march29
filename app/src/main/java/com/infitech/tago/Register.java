package com.infitech.tago;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    EditText name,username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.et_name);
        username=(EditText)findViewById(R.id.et_username);
        password=(EditText)findViewById(R.id.et_password);
    }
    public void OnReg(View view){
        String str_name=name.getText().toString();
        String str_username=username.getText().toString();
        String str_password = password.getText().toString();
        String type="register";
        BackgroundWorker backgroundWorker=new BackgroundWorker(this);
        backgroundWorker.execute(type,str_name,str_username,str_password);

    }
}
