package com.developer.valyutaapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;


/**
 * Created by User on 12.06.2018.
 */

public class SendXmlRequest {

    final String CON = "Connection";
    private String urlServerIn = "";
    Setting setting;
    private boolean Debuger = true;
    Context context;
    private String userInf = Build.MODEL + " / (SDK-" + Build.VERSION.SDK + " | Android-" + Build.VERSION.RELEASE + ")";
    public BufferedReader sendXmlRequestPull(String SOAPRequestXML, String urlServ){


        urlServerIn = "http://"+urlServ;
        String respData = "";
        BufferedReader reader = null;
        setting = new Setting(context);

        HttpParams httpParameters = new BasicHttpParams();

        //Устанавливаем тайм аут
        int timeoutConnection = 14 * 13000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

        // Создаем новый HttpClient с начальными установками
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpPost httppost = new HttpPost();

        try {
            URI url = new URI(urlServerIn);

            httppost.setURI(url);
            httppost.addHeader("Accept", "text/xml");
            httppost.addHeader("Content-Type", "application/xml");
            httppost.addHeader("User-Agent", userInf);

        }catch(URISyntaxException e){

            if(Debuger) Log.d(CON, "URISyntaxException: " + e.getMessage());
        }

        StringEntity se = new StringEntity(SOAPRequestXML, HTTP.UTF_8);
        se.setContentType("text/xml");

        httppost.setEntity(se);

        HttpResponse response;

        try{

            response = httpclient.execute(httppost);

            if(Debuger) Log.d(CON, "response.getStatusLine().getStatusCode() - " + response.getStatusLine().getStatusCode());
            //Смотрим если статус положительный
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));

                if(Debuger) Log.d(CON, "finished");

                if(Debuger) Log.d(CON, "line 105");

                if(Debuger) Log.d(CON, "line 107");
                //if(Debuger) Log.d(CON, "response: " + respData);
            }
        }catch(ClientProtocolException e){
            if(Debuger) Log.d(CON, "ClientProtocolException: " + e.getMessage());
        }catch(IOException e){
            if(Debuger) Log.d(CON, "IOException: " + e.getMessage());
        }

        return reader;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String sendXmlRequest(){

        @SuppressLint({"NewApi", "LocalSuppress"}) DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String datef = df.format(Calendar.getInstance().getTime());
        urlServerIn = "http://nbt.tj/ru/kurs/export_xml.php?date=" + datef + "&export=xmlout";

        String respData = "";

        HttpParams httpParameters = new BasicHttpParams();

        //Устанавливаем тайм аут
        int timeoutConnection = 14 * 13000;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

        // Создаем новый HttpClient с начальными установками
        HttpClient httpclient = new DefaultHttpClient(httpParameters);

        HttpPost httppost = new HttpPost();

        try {
            URI url = new URI(urlServerIn);

            httppost.setURI(url);
            httppost.addHeader("Accept", "text/xml");
            httppost.addHeader("Content-Type", "application/xml");
            httppost.addHeader("User-Agent", userInf);

        }catch(URISyntaxException e){

            if(Debuger) Log.d(CON, "URISyntaxException: " + e.getMessage());
        }

        try{
            StringEntity se = new StringEntity(HTTP.UTF_8);
            se.setContentType("text/xml");

            httppost.setEntity(se);

        }catch(UnsupportedEncodingException e){

            if(Debuger) Log.d(CON, "UnsupportedEncodingException: " + e.getMessage());
        }

        HttpResponse response;

        try{

            response = httpclient.execute(httppost);

            if(Debuger) Log.d(CON, "response.getStatusLine().getStatusCode() - " + response.getStatusLine().getStatusCode());
            //Смотрим если статус положительный
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
                StringBuilder sb = new StringBuilder();
                String line = null;
                if(Debuger) Log.d(CON, "finished");

                while ((line = reader.readLine()) != null){
                    if(Debuger) Log.d(CON, "line 102");
                    sb.append(line + System.getProperty("line.separator"));
                    if(Debuger) Log.d(CON, "line 103");
                    if(Debuger) Log.d(CON, "line 0 d");
                }
                if(Debuger) Log.d(CON, "line 105");
                respData = sb.toString();
                if(Debuger) Log.d(CON, "line 107");
                //if(Debuger) Log.d(CON, "response: " + respData);
            }
        }catch(ClientProtocolException e){
            if(Debuger) Log.d(CON, "ClientProtocolException: " + e.getMessage());
        }catch(IOException e){
            if(Debuger) Log.d(CON, "IOException: " + e.getMessage());
        }

        return respData;
    }
    public static boolean isNetworkAvailable(Activity con) {
        ConnectivityManager cm = (ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;

    }
    public static boolean isNetworkAvailable(Application application) {
        // TODO Auto-generated method stub
        ConnectivityManager cm = (ConnectivityManager)application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
