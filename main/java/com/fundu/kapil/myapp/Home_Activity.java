package com.fundu.kapil.myapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Home_Activity extends AppCompatActivity {

    DatabaseHelper mydb;
    String[] tag_list,title_list,data_list;

    SwipeRefreshLayout mSwipeRefreshLayout;

    private String s1[],s2[],s3[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mydb = new DatabaseHelper(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeUp();
            }
        });

        onSwipeUp();

    }

    public void btn_showAll(View view) {
        Cursor cursor = mydb.getAllData();
        Cursor cursor_count = mydb.getAllData();
        int i=0;

        while(cursor_count.moveToNext()) {i++;}

        String st1[]=new String[i];
        String st2[]=new String[i];
        String st3[]=new String[i];
        i=0;
        while(cursor.moveToNext()){
            st3[i] = cursor.getString(0);
            st2[i] = cursor.getString(1);
            st1[i] = cursor.getString(2);
            i++;
        }
        i=0;

        Bundle b = new Bundle();
        b.putStringArray("string1",st1);
        b.putStringArray("string2",st2);
        b.putStringArray("string3",st3);
        b.putString("myTag","All DATA");
        Intent j = new Intent(Home_Activity.this,Tag_Activity.class);
        j.putExtras(b);
        startActivity(j);

    }
    public void btn_tag1(View view) {
        String tagm = "Trignometry";
        Cursor cursor = mydb.getDataByTag(tagm);
        Cursor cursor_count = mydb.getDataByTag(tagm);
        int i=0;

        while(cursor_count.moveToNext()) {i++;}

        String st1[]=new String[i];
        String st2[]=new String[i];
        String st3[]=new String[i];
        i=0;
        while(cursor.moveToNext()){
            st3[i] = cursor.getString(0);
            st2[i] = cursor.getString(1);
            st1[i] = cursor.getString(2);
            i++;
        }
        i=0;
        Bundle b = new Bundle();
        b.putStringArray("string1",st1);
        b.putStringArray("string2",st2);
        b.putStringArray("string3",st3);
        b.putString("myTag",tagm);
        Intent j = new Intent(Home_Activity.this,Tag_Activity.class);
        j.putExtras(b);
        startActivity(j);

    }
    public void btn_tag2(View view) {
        String tagm = "Diffrential";
        Cursor cursor = mydb.getDataByTag(tagm);
        Cursor cursor_count = mydb.getDataByTag(tagm);
        int i=0;

        while(cursor_count.moveToNext()) {i++;}

        String st1[]=new String[i];
        String st2[]=new String[i];
        String st3[]=new String[i];
        i=0;
        while(cursor.moveToNext()){
            st3[i] = cursor.getString(0);
            st2[i] = cursor.getString(1);
            st1[i] = cursor.getString(2);
            i++;
        }
        i=0;
        Bundle b = new Bundle();
        b.putStringArray("string1",st1);
        b.putStringArray("string2",st2);
        b.putStringArray("string3",st3);
        b.putString("myTag",tagm);
        Intent j = new Intent(Home_Activity.this,Tag_Activity.class);
        j.putExtras(b);
        startActivity(j);

    }
    public void btn_tag3(View view) {
        String tagm = "Intigration";
        Cursor cursor = mydb.getDataByTag(tagm);
        Cursor cursor_count = mydb.getDataByTag(tagm);
        int i=0;

        while(cursor_count.moveToNext()) {i++;}

        String st1[]=new String[i];
        String st2[]=new String[i];
        String st3[]=new String[i];
        i=0;
        while(cursor.moveToNext()){
            st3[i] = cursor.getString(0);
            st2[i] = cursor.getString(1);
            st1[i] = cursor.getString(2);
            i++;
        }
        i=0;
        Bundle b = new Bundle();
        b.putStringArray("string1",st1);
        b.putStringArray("string2",st2);
        b.putStringArray("string3",st3);
        b.putString("myTag",tagm);
        Intent j = new Intent(Home_Activity.this,Tag_Activity.class);
        j.putExtras(b);
        startActivity(j);

    }
    public void btn_tag4(View view) {
        String tagm = "Probability";
        Cursor cursor = mydb.getDataByTag(tagm);
        Cursor cursor_count = mydb.getDataByTag(tagm);
        int i=0;

        while(cursor_count.moveToNext()) {i++;}

        String st1[]=new String[i];
        String st2[]=new String[i];
        String st3[]=new String[i];
        i=0;
        while(cursor.moveToNext()){
            st3[i] = cursor.getString(0);
            st2[i] = cursor.getString(1);
            st1[i] = cursor.getString(2);
            i++;
        }
        i=0;
        Bundle b = new Bundle();
        b.putStringArray("string1",st1);
        b.putStringArray("string2",st2);
        b.putStringArray("string3",st3);
        b.putString("myTag",tagm);
        Intent j = new Intent(Home_Activity.this,Tag_Activity.class);
        j.putExtras(b);
        startActivity(j);

    }

    public void do_btn_add(View view) {
        startActivity(new Intent(Home_Activity.this,Insert_Activity.class));
    }

    //+++++++++++++++++++++++++++++ SQL -> Lite / On Up Swipe +++++++++++++++++++++++++++++

    public void onSwipeUp() {
        if (isConn()) {
            int i=0;
            for(i=0;i<1;i++){
                if(update_data()) break;
            }
            if (i!=1) {
                Toast.makeText(this, "Data updated", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "Swipe again..", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Your Internet conn is OFF", Toast.LENGTH_LONG).show();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }
    public boolean update_data(){
            try {
                if(take_from_SQL()){
                    if(add_into_Lite()) {
                    }else {
                       // Toast.makeText(this, "update_data : add_into_Lite() : ", Toast.LENGTH_LONG).show();
                        return false;
                    }
                    return true;
                }else {
                    //Toast.makeText(this,"update_data : take_from_SQL() :", Toast.LENGTH_LONG).show();
                    return false;
                }
            }catch(Exception e){
                Toast.makeText(this,"update_data : "+e.getMessage(), Toast.LENGTH_LONG).show();
                return false;
            }
    }
    public boolean take_from_SQL(){

        try {
            BackgroundWorker backgroundWorker2 = new BackgroundWorker(this);
            backgroundWorker2.execute("view");
            Toast.makeText(this,"HOME_G : "+backgroundWorker2.result_msg+" | "+backgroundWorker2.countT(), Toast.LENGTH_SHORT).show();

            return false;

//            if(backgroundWorker2.result_msg== 1){
//                return loadIntoListView(s) && putInLite();
//            }
//            else {
//                return false;
//            }

        }
        catch (Exception e){
            return false;
        }
    }
    private boolean loadIntoListView(String json){

        try{
            JSONArray jsonArray = new JSONArray(json);
            tag_list = new String[jsonArray.length()];
            title_list = new String[jsonArray.length()];
            data_list = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);
                tag_list[i] = obj.getString("f_tag");
                title_list[i] = obj.getString("f_title");
                data_list[i] = obj.getString("f_data");

            }
            return true;
        }catch (JSONException e ){
            return false;
        }

    }
    public boolean putInLite(){
        int count=0,j=0;
        for (j=0;j<tag_list.length;j++) {

            count += mydb.insertData(title_list[j], data_list[j], tag_list[j]);

        }
        if(count == j ){
            return true;
        }else{
            return false;
        }
    }
    public boolean add_into_Lite() {
        int count=0,i=1;
        String stitle,sdata,stag;

        mydb.deleteAllData();

        for (i=0  ; i < title_list.length ; i++) {

            stitle = title_list[i];
            sdata = data_list[i];
            stag = tag_list[i];

            count += mydb.insertData(stitle, sdata, stag);
        }
        if (count ==i) {
            return true;
        } else {
            return false;
        }
    }

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    // checking internet connection
    private boolean isConn(){
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }



/*
    //*****************MySQL******************************

    public void insert_Lite_to_SQL(View view) {

        Cursor cursor = mydb.getAllData();
        int count=0,i=0;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Login Status");

            while (cursor.moveToNext()) {

                String mytitle = cursor.getString(1);
                String mydata = cursor.getString(2);
                String mytag = cursor.getString(3);

                String type = "insert";

                BackgroundWorker backgroundWorker2 = new BackgroundWorker(this);
                backgroundWorker2.execute(type, mytitle, mydata, mytag);

                count = count + backgroundWorker2.countT();
                i = i + 1;
            }

            if(count==i){
                alertDialog.setMessage("Success");
            }else {
                alertDialog.setMessage("Fail to insert");
            }

        alertDialog.show();
    }
    public void update_Lite_to_SQL(View view) {

        Cursor cursor = mydb.getAllData();
        int count=0,i=0;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Login Status");

        while (cursor.moveToNext()) {

            String myid = cursor.getString(0);
            String mytitle = cursor.getString(1);
            String mydata = cursor.getString(2);
            String mytag = cursor.getString(3);

            String type = "update";

            BackgroundWorker backgroundWorker2 = new BackgroundWorker(this);
            backgroundWorker2.execute(type, mytitle, mydata, mytag , myid);

            count = count + backgroundWorker2.countT();
            i = i + 1;
        }

        if(count==i){
            alertDialog.setMessage("Success");
        }else {
            alertDialog.setMessage("Fail to update");
        }

        alertDialog.show();

    }

    //*****************SQLite******************************

    public void do_btn_addData(View view) {

        String stitle = ftitle.getText().toString();
        String sformula = formula.getText().toString();
        String stag = tag.getText().toString();

       int isInserted = mydb.insertData(stitle,sformula,stag);
        if(isInserted == 1) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Data Not Inserted",Toast.LENGTH_LONG).show();
        }
    }
    public void do_button_view_data(View view) {
        Cursor cursor = mydb.getAllData();
        if(cursor.getCount() == 0 ){
            // error massage
            showMessage("Error","Nothing Found !");
            //return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        while(cursor.moveToNext()){
            stringBuffer.append("F_Id = "+cursor.getString(0)+"\n");
            stringBuffer.append("title = "+cursor.getString(1)+"\n");
            stringBuffer.append("Formula = "+cursor.getString(2)+"\n");
            stringBuffer.append("Tag = "+cursor.getString(3)+"\n\n");
        }
        // Show all data
        showMessage("Data\n",stringBuffer.toString());

    }
    public  void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void do_button_update(View view) {
        String s_fid = f_id.getText().toString();
        String s_ftitle = ftitle.getText().toString();
        String s_formula = formula.getText().toString();
        String s_tag = tag.getText().toString();
        boolean isUpdated = mydb.updateData(s_fid,s_ftitle,s_formula,s_tag);
        if(isUpdated == true){
            Toast.makeText(this, "Data Updated", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Data is  Updated",Toast.LENGTH_LONG).show();
        }
    }
    public void do_button_delete(View view) {
        //String s_fid = f_id.getText().toString();
      //  Integer deleteRow =
                mydb.deleteAllData();
//        if(deleteRow > 0){
//            Toast.makeText(this, "Data Deleted", Toast.LENGTH_LONG).show();
//        }
//        else{
//            Toast.makeText(this,"Data is not Deleted",Toast.LENGTH_LONG).show();
//        }
    }

 */



}
