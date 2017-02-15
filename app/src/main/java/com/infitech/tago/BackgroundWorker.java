package com.infitech.tago;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

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
/**
 * Created by Vivek on 1/28/2017.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String>{
    Context context;
    AlertDialog alertDialog;
    public String LoggedInName;
   // BackgroundWorker(){}
   BackgroundWorker(Context ctx){
        context=ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type=params[0];
        String login_url="http://192.168.0.115/Tago/login.php";
        String register_url="http://192.168.0.115/Tago/signup.php";
        if(type.equals("Login")){
            try {
                String user_name=params[1];
                String password=params[2];
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();;
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    result+=line;
                    Log.i("result",result);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        else if(type.equals("register")){
            try {
                String name=params[1];
                String username=params[2];
                String password=params[3];
                URL url=new URL(register_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();;
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    result+=line;
                    Log.i("result",result);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login status");
    }

    @Override
    protected void onPostExecute(String result) {
        //alertDialog.setMessage(result);
        //alertDialog.show();
       // String a="lg";#4a91df
        if(result.contains("Login success")) {
            LoggedInName=result.substring(14);
            LoggedInName=LoggedInName.replaceAll("\t$","");
            Intent intent = new Intent(context, UploadActivity.class);
            intent.putExtra("LoggedInName",LoggedInName);
            context.startActivity(intent);
           /* Intent intent = new Intent(context, UploadActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("LoggedInName","1999");
            context.startActivity(intent);*/

        }
        if(result.contains("Incorrect Password")){
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context,result, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        if(result.contains("username doesn't match")){
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context,result, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
        if(result.contains("Welcome")){
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context,result, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
        if(result.contains("exists")){
            alertDialog.setMessage(result);
            alertDialog.show();
        }


    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
