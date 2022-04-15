package org.tensorflow.demo.sc;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DBTask extends AsyncTask<String, Void, String> {
        String sendMsg;
        ProgressDialog loading;
        URL register_url;
        Context context;

        public void DBTaskset(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
         //  loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected String doInBackground(String... strings) {

            String receiveMsg=null;
            String str;
            HttpURLConnection connect=null;
            URL url = null;//연결
            try {
                url = new URL("http://172.16.42.39:49151/phone_time/phone_time.jsp");
            } catch (MalformedURLException e) {

            }
            try {
                connect =  (HttpURLConnection)url.openConnection();
                //connect.setRequestProperty("Accept-Charset","EUC-KR");

                if(strings.length==2)sendMsg =  "type=" + strings[0] + "&column="+strings[1];
                if(strings.length==3) sendMsg =  "type=" + strings[0] + "&column="+strings[1]+"&table="+strings[2];
                if(strings.length==4) sendMsg =  "type=" + strings[0] + "&column="+strings[1]+"&table="+strings[2];
                if(strings[0].equals("4")) sendMsg ="type=" + strings[0] + "&column="+strings[1];
                connect.setRequestProperty("Context_Type", "application/x-www-form-urlendcoded;charset=UTF-8");
                //connect.setRequestProperty ("Content-Length", Integer.toString (sendMsg.length()));
                connect.setDefaultUseCaches(false);
                connect.setRequestMethod("POST");//데이터 전송
                connect.setDoOutput(true);
                connect.setDoInput(true);
                Log.d("sendMsg:",sendMsg+"\n");
                //connect.setConnectTimeout(15000);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                // connect.connect();
                //if(connect!=null)  E5.setText(connect.getURL().toString()+"------");
                OutputStreamWriter osw= new OutputStreamWriter(connect.getOutputStream());

                //sendMsg =  "type=0&item=" + strings[2] + "&column=" + strings[3] + "&table=" + strings[1];

                //register_url = new URL(sendMsg);
                osw.write(sendMsg);
                osw.flush();
                osw.close();

                //E5.setText("----a");
                //BufferedReader in = new BufferedReader(new InputStreamReader(register_url.openStream()));
                InputStreamReader tmp = new InputStreamReader(connect.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                //StringBuffer buffer = new StringBuffer();

                receiveMsg=reader.readLine();
                while ((str = reader.readLine()) != null) {
                    receiveMsg+=" "+str;
                }
                tmp.close();
                reader.close();
            } catch (UnsupportedEncodingException e) {
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connect!=null) connect.disconnect();
            }
            return receiveMsg;
        }
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
          //  loading.dismiss();
        }

}//타입,테이블,검색하고 싶은것, 조건문