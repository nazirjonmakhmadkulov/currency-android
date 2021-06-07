package com.developer.valyutaapp;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Calendar;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;



/**
 * Created by User on 12.06.2018.
 */
public class RequestVygruzka extends AsyncTask<Void, Void, Void> {
    DatabaseAdapter databaseAdapter;
    long userId;
    Context context;
    String message_gb = "";
    private ResponseListener responseListener;
    String progress_message = "";

    boolean debuget = true;

    boolean response_bool;

    public RequestVygruzka(Context context) {
        this.context = context;
        response_bool = false;
    }

    @Override
    protected void onPreExecute() {
        // progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);

        if (debuget) Log.d("sending request", "--9");

        if (responseListener != null) {
            responseListener.responseVygruzkiCome(response_bool, message_gb);
        }

        if (debuget) Log.d("sending request", "--10");
        // progressDialog.dismiss();

        if (debuget) Log.d("sending request", "--10");

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        if (responseListener != null) {
            responseListener.progressMessage(progress_message);
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected Void doInBackground(Void... params) {
        databaseAdapter = new DatabaseAdapter(context);

        String result = "";
        boolean res = false;
        Log.d("sending request", "--1");
        SendXmlRequest sendXmlReq = new SendXmlRequest();

        result = sendXmlReq.sendXmlRequest();

        SimpleXML xmlParserIn = new SimpleXML();

        Document pressDoc = xmlParserIn.getDomElement(result);

        Log.d("sending request", result);

        Log.d("sending request", "--3");
        //Парсим xml
        if (pressDoc != null) {

            Log.d("sending request", "--4");
            String valute = "";
            String value = "";


            NodeList valuteNode = pressDoc.getElementsByTagName("ValCurs");

            if (valuteNode.getLength() > 0) {

                //String date_sts = valuteNode.item(0).getAttributes().item(0).getNodeValue();
                NodeList valuteNodeList = valuteNode.item(0).getChildNodes();

                int cont_ = valuteNodeList.getLength();

                for (int i = 0; i < cont_; i++) {
                    ContentValues cvVstavitValute = new ContentValues();

                    if (valuteNodeList.item(i).hasAttributes()) {//Проверяем атрибутов
                        cvVstavitValute.put("id_val", valuteNodeList.item(i).getAttributes().item(0).getNodeValue());
                    }

                    String parent = "Valute";
                    parceValute(valuteNodeList.item(i).getChildNodes(), parent, cvVstavitValute);
                    databaseAdapter.open();
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String datef = df.format(Calendar.getInstance().getTime());
                    boolean model = databaseAdapter.getDataNULL();
                    Model getDates = databaseAdapter.getDataDate();
                    if (model == true) {
                        DBSimple.insertRow(context, cvVstavitValute);
                        Log.d("sending request", "insertRow");
                    } else {

                        DBSimple.insertRow(context, cvVstavitValute);
                        Log.d("sending request", "insertRowaaaaaa");

                    }
                    databaseAdapter.close();
                }
            }

            Log.d("sending request", "--5");
            message_gb = value;

            if (valute.equals("0")) {
                res = true;

                XPathFactory factory = XPathFactory.newInstance();
                XPath xPath = factory.newXPath();
                XPathExpression xPathExpr = null;

            }
        }
        response_bool = res;
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void parceValute(NodeList nodeList, String parent, ContentValues cv){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String datef = df.format(Calendar.getInstance().getTime());
        String parent_local = parent;

        for(int i = 0; i < nodeList.getLength(); i ++ ){
            Node node  = nodeList.item(i);

            if(node.hasAttributes()){

                if(node.getNodeName().equals("Valute")){
                    cv.put("id_val", node.getAttributes().item(0).getNodeValue());
                }
            }

            if(node.hasChildNodes()) {

                if (node.getChildNodes().getLength() == 1) {

                    if (node.getNodeName().equals("CharCode")) {
                        cv.put("CharCode", node.getTextContent());
                    } else if (node.getNodeName().equals("Nominal")) {
                        cv.put("Nominal", node.getTextContent());
                    } else if (node.getNodeName().equals("Name")) {
                        cv.put("Name", node.getTextContent());
                    } else if (node.getNodeName().equals("Value")) {
                        cv.put("Value", node.getTextContent());
                    }
                    cv.put("_date", datef);
                    cv.put("checked", "2");
                    Log.d("cv" , " = " + cv);
                }
                parent = node.getNodeName();
                parceValute(node.getChildNodes(), parent,cv);

            }

        }

    }

    public void setResponseListener(ResponseListener responseListener){
        this.responseListener = responseListener;
    }

    public interface ResponseListener{
        void responseVygruzkiCome(boolean res, String message);
        void progressMessage(String message);

    }
}