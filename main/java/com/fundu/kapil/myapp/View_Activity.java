package com.fundu.kapil.myapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class View_Activity extends AppCompatActivity {
    TextView tv_tag,tv_title,tv_data;
    String tag,title,data,id;
    DatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_);

        tv_tag = (TextView)findViewById(R.id.tv_tag);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_data = (TextView)findViewById(R.id.tv_data);

        mydb = new DatabaseHelper(this);


        getIncomingIntent();
        tv_tag.setText(tag);
        tv_title.setText(title);
        tv_data.setText(data);

    }

    private  void getIncomingIntent(){
        if(getIntent().hasExtra("tag") && getIntent().hasExtra("title") && getIntent().hasExtra("data") && getIntent().hasExtra("id") ){
            tag = getIntent().getStringExtra("tag");
            title = getIntent().getStringExtra("title");
            data = getIntent().getStringExtra("data");
            id = getIntent().getStringExtra("id");
        }
    }

    public void do_btn_share(View view) {
        Intent share_intent = new Intent(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        String shareBody = "\t"+tag+"\t\n\n-----"+title+"-----\n\n"+data+"";
        String shareSub = tag;
        share_intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        share_intent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(share_intent,"Share using..."));
    }
    public void do_btn_update(View view) {
        Intent i = new Intent(View_Activity.this,Update_Activity.class);

        i.putExtra("tag",tag);
        i.putExtra("title",title);
        i.putExtra("data",data);
        i.putExtra("id",id);

        startActivity(i);
    }
    public void do_btn_delete(View view) {

        if (isConn()) {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute("delete", title, data, tag);

            if (backgroundWorker.result_msg == 1) {
                // mydb.deleteData(tag,title,data);
                Toast.makeText(this, "delete successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(View_Activity.this,Home_Activity.class));
            } else {
                Toast.makeText(this, "delete Failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Your Internet Conn is OFF", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isConn(){
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}
