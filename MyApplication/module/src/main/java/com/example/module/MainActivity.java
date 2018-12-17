package com.example.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView textTime, textReg, textCon, textError, textTemp, textState;
    Button button,buttonDB;
    EditText EditTextCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textError = (TextView) findViewById(R.id.textError);
        textTime = (TextView) findViewById(R.id.textTime);
        textState = (TextView) findViewById(R.id.textState);
        textReg = (TextView) findViewById(R.id.textReg);
        textCon = (TextView) findViewById(R.id.textCon);
        textTemp = (TextView) findViewById(R.id.textTemp);
        button  = (Button) findViewById(R.id.buttonRead);
        buttonDB = (Button) findViewById(R.id.buttonDB);
        EditTextCity = (EditText) findViewById(R.id.EditTextCity);

        final RequestQueue queue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://api.apixu.com/v1/forecast.json?key=105568e5da574227b6e110151182811&q="+EditTextCity.getText().toString();
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try{
                                    JSONObject location = response.getJSONObject("location");
                                    String localtime = location.getString("localtime");
                                    String region = location.getString("region");
                                    String country = location.getString("country");
                                    textTime.setText("Time: "+localtime);
                                    textReg.setText("Region: "+region);
                                    textCon.setText("Country: "+country);

                                    JSONObject current = response.getJSONObject("current");
                                    String temp = current.getString("temp_c");
                                    textTemp.setText("Temperature: "+temp);

                                    JSONObject condition = current.getJSONObject("condition");
                                    String text = condition.getString("text");
                                    textState.setText("State: "+text);
                                } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textError.setText("Something went wrong!");
                    }
                });
                queue.add(request);
            }
        });
        buttonDB.setOnClickListener (new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DBActivity.class);
                startActivity(intent);
            }
        });
    }
}