package com.example.myapplication1;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText user_field;
    private Button  main_btn;
    private TextView  result_info;
    private TextView result_info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_field=findViewById(R.id.ss);
                main_btn=findViewById(R.id.wer);
        result_info=findViewById(R.id.TextView1);
                result_info1=findViewById(R.id.TextView2);
                main_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(user_field.getText().toString().trim().equals(""))
                            Toast.makeText(MainActivity.this, R.string.no_user_input, Toast.LENGTH_LONG).show();
                        else{
                            String city= user_field.getText().toString();

                            String  url= "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=36b0844d27c1832c9f05337c318e5d8e&units=metric";
new GetURLData().execute(url);
                        }
                    }
                });
    }
    private  class GetURLData extends AsyncTask<String,String,String>{

        protected void onPreExecute(){
            super.onPreExecute();
            result_info.setText("Ожидайте...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL ulr = new URL(strings[0]);
                connection =(HttpURLConnection) ulr.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line =reader.readLine()) !=null)
                    buffer.append(line).append("\n");
                return  buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection !=null)
                    connection.disconnect();
                try {
                    if (reader != null)

                        reader.close();
                } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            return  null;
            }
            @SuppressLint("SetTextI18n")
            @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
                try {
                    JSONObject obj = new JSONObject(result);
                    result_info.setText("Температура "+ obj.getJSONObject("main").getInt("temp")+("°C"));
                    result_info1.setText("Как ощущается "+obj.getJSONObject("main").getInt("feels_like")+("°C"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }






        }
    }
