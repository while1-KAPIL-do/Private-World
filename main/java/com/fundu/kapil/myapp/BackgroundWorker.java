package com.fundu.kapil.myapp;

import android.content.Context;
import android.os.AsyncTask;
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
 * Created by kapil on 29-Jul-18.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {

    public Context context;
    public int result_msg;
    public String jsonString;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }


    @Override
    protected void onPreExecute() {
        jsonString = "";
        result_msg = 0;
    }

    @Override
    protected String doInBackground(String... voids) {

        String type = voids[0];

        String delete_url = "http://appbykapil.000webhostapp.com/delete.php";
        String insert_url = "http://appbykapil.000webhostapp.com/insert.php";
        String update_url = "http://appbykapil.000webhostapp.com/update.php";
        String view_url = "http://appbykapil.000webhostapp.com/viewbyjson.php";
        String isCopy_url = "http://appbykapil.000webhostapp.com/isCopy.php";


        // 10.0.2.2 is the defalt add for wamp in android or we can put our IP4 here
        if (type.equals("delete")) {
            try {
                String f_title = voids[1];
                String f_data = voids[2];
                String f_tag = voids[3];

                //***** for creating connection *****
                URL url = new URL(delete_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //** Output Stream -> Sending id and password to database
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //** Buffered Writter -> provide buffer space
                //** UTF-8 ->
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //** URLEncode -> sending user_name and user_pass to login.php file
                String post_data = URLEncoder.encode("f_title", "UTF-8") + "=" + URLEncoder.encode(f_title, "UTF-8") + "&" +
                        URLEncoder.encode("f_data", "UTF-8") + "=" + URLEncoder.encode(f_data, "UTF-8") + "&" +
                        URLEncoder.encode("f_tag", "UTF-8") + "=" + URLEncoder.encode(f_tag, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                //**************************** /Output Stream

                //** Input Stream -> Receving output from database
                InputStream inputStream = httpURLConnection.getInputStream();

                //** Buffered Reader -> provide buffer space
                //** iso-8859-1 ->
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                //** Receving result from login.php file
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                //**************************** /Input Stream

                httpURLConnection.disconnect();
                return result;
                //***** connection disconnecting *****


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (type.equals("insert")) {
            try {
                String f_title = voids[1];
                String f_data = voids[2];
                String f_tag = voids[3];

                //***** for creating connection *****
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //** Output Stream -> Sending id and password to database
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //** Buffered Writter -> provide buffer space
                //** UTF-8 ->
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //** URLEncode -> sending user_name and user_pass to login.php file
                String post_data = URLEncoder.encode("f_title", "UTF-8") + "=" + URLEncoder.encode(f_title, "UTF-8") + "&" +
                        URLEncoder.encode("f_data", "UTF-8") + "=" + URLEncoder.encode(f_data, "UTF-8") + "&" +
                        URLEncoder.encode("f_tag", "UTF-8") + "=" + URLEncoder.encode(f_tag, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                //**************************** /Output Stream

                //** Input Stream -> Receving output from database
                InputStream inputStream = httpURLConnection.getInputStream();

                //** Buffered Reader -> provide buffer space
                //** iso-8859-1 ->
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                //** Receving result from login.php file
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                //**************************** /Input Stream

                httpURLConnection.disconnect();
                return result;
                //***** connection disconnecting *****


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (type.equals("update")) {
            try {
                String f_title = voids[1];
                String f_data = voids[2];
                String f_tag = voids[3];
                String f_titleold = voids[1];
                String f_dataold = voids[2];
                String f_tagold = voids[3];

                //***** for creating connection *****
                URL url = new URL(update_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //** Output Stream -> Sending id and password to database
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //** Buffered Writter -> provide buffer space
                //** UTF-8 ->
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //** URLEncode -> sending user_name and user_pass to login.php file
                String post_data = URLEncoder.encode("f_title", "UTF-8") + "=" + URLEncoder.encode(f_title, "UTF-8") + "&" +
                        URLEncoder.encode("f_data", "UTF-8") + "=" + URLEncoder.encode(f_data, "UTF-8") + "&" +
                        URLEncoder.encode("f_tag", "UTF-8") + "=" + URLEncoder.encode(f_tag, "UTF-8") + "&"+
                        URLEncoder.encode("f_titleold", "UTF-8") + "=" + URLEncoder.encode(f_titleold, "UTF-8") + "&" +
                        URLEncoder.encode("f_dataold", "UTF-8") + "=" + URLEncoder.encode(f_dataold, "UTF-8") + "&" +
                        URLEncoder.encode("f_tagold", "UTF-8") + "=" + URLEncoder.encode(f_tagold, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                //**************************** /Output Stream

                //** Input Stream -> Receving output from database
                InputStream inputStream = httpURLConnection.getInputStream();

                //** Buffered Reader -> provide buffer space
                //** iso-8859-1 ->
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                //** Receving result from login.php file
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                //**************************** /Input Stream

                httpURLConnection.disconnect();
                return result;
                //***** connection disconnecting *****

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (type.equals("view")) {
            try {

                //***** for creating connection *****
                URL url = new URL(view_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //** Output Stream -> Sending id and password to database
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //** Buffered Writter -> provide buffer space
                //** UTF-8 ->
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //** URLEncode -> sending user_name and user_pass to login.php file
                String post_data ="";

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                //**************************** /Output Stream

                //** Input Stream -> Receving output from database
                InputStream inputStream = httpURLConnection.getInputStream();

                //** Buffered Reader -> provide buffer space
                //** iso-8859-1 ->
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                //** Receving result from login.php file
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                //**************************** /Input Stream

                httpURLConnection.disconnect();
                return result;
                //***** connection disconnecting *****

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (type.equals("isCopy")) {
            try {
                String f_title = voids[1];
                String f_data = voids[2];
                String f_tag = voids[3];

                //***** for creating connection *****
                URL url = new URL(isCopy_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                //** Output Stream -> Sending id and password to database
                OutputStream outputStream = httpURLConnection.getOutputStream();

                //** Buffered Writter -> provide buffer space
                //** UTF-8 ->
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                //** URLEncode -> sending user_name and user_pass to login.php file
                String post_data = URLEncoder.encode("f_title", "UTF-8") + "=" + URLEncoder.encode(f_title, "UTF-8") + "&" +
                        URLEncoder.encode("f_data", "UTF-8") + "=" + URLEncoder.encode(f_data, "UTF-8") + "&" +
                        URLEncoder.encode("f_tag", "UTF-8") + "=" + URLEncoder.encode(f_tag, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                //**************************** /Output Stream

                //** Input Stream -> Receving output from database
                InputStream inputStream = httpURLConnection.getInputStream();

                //** Buffered Reader -> provide buffer space
                //** iso-8859-1 ->
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                //** Receving result from login.php file
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                //**************************** /Input Stream

                httpURLConnection.disconnect();
                return result;
                //***** connection disconnecting *****


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String aVoid) {

        if(aVoid == null) {
            result_msg = 0;
            jsonString = "0";
        }
        else if(aVoid.length() == 1 )
        {
            result_msg = Integer.parseInt(aVoid);
        }
        else {
            try {
                jsonString = aVoid;
                result_msg = jsonString.length();
            } catch (Exception e) {
                jsonString = "0";
                result_msg = 0;
            }
        }


        Toast.makeText(context,"BackG : "+result_msg+"\n\n", Toast.LENGTH_SHORT).show();

        super.onPostExecute(aVoid);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    //************ my function *************
    public int countT(){
        return result_msg;
    }






}