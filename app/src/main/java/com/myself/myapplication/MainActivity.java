package com.myself.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends Activity {


    private  TextView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (TextView) findViewById(R.id.listview);

        MyVolley.init(this);
        requestNewsType();

}
    public void requestNewsType() {
        RequestQueue requestQueue = MyVolley.getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://w.huanqiu.com/apps/ygwifi/rss.php?cname=guoji",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.d("zhangyan","responce"+response);
                        InputStream is = new ByteArrayInputStream(response.getBytes());
                        try {
                            SAXParserFactory factory = SAXParserFactory.newInstance();
                            SAXParser saxParser = factory.newSAXParser();

                            SaxItem handle = new SaxItem();
                            saxParser.parse(is, handle);
                            ArrayList<NewsBean> billItems = handle.getBillItems();
                            StringBuilder mstring = new StringBuilder();
                            for (int i=0;i<billItems.size();i++){
                                mstring.append(billItems.get(i).toString());
                            }
                            listview.setText(mstring);
//
                            is.close();



                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }

                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError arg0){
                Log.d("zhangyan","error"+arg0.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                Log.d("zhangyan","jinqumap");
                return params;
            }

        };
        requestQueue.add(stringRequest);
    }

}