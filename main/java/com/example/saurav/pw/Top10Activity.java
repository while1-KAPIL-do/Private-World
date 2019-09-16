package com.example.saurav.pw;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Top10Activity extends AppCompatActivity {


    AdView mAdView;

    private static final String TAG ="Navigation --- >>>>";
    private static final int NUM_COLUMNS = 2;
    SwipeRefreshLayout mSwipeRefreshLayout;
    JSONArray cast;

    StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter;
    String[] data_list ;
    // Error
    ImageView error_img;
    TextView error_txt;

    String tag_type;
    ProgressBar progressBar;

    private ArrayList<MyObject> mObj = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10);

        //------------ads

        //********* AdMob ***************
        //********* BannerAd
        MobileAds.initialize(this,getResources().getString(R.string.app_Id));
        mAdView = findViewById(R.id.adview);
        AdRequest mAdRequest = new AdRequest.Builder().build();
        mAdView.loadAd(mAdRequest);
        //********* BannerAd
        //*********/ AdMob ***************


        loadDATA();
    }

    // *************** for Video Ads *******************
    private void loadDATA(){

        progressBar = findViewById(R.id.progress);



        // error views
        error_img =  findViewById(R.id.no_internet_img);
        error_txt =   findViewById(R.id.no_internet_txt);


        // checking internet connection
        if (isConn()) {
            findViewById(R.id.swipe_refresh).setVisibility(View.GONE);
            callData(getResources().getString(R.string.tag_0));
        }else{
            error_txt.setText("No Internet Connection ! \n swipe-up for Refresh ");
            error_img.setImageResource(R.drawable.no_internet);
            findViewById(R.id.swipe_refresh).setVisibility(View.VISIBLE);
        }

        // Swipe Up for Refresh network
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isConn()) {
                    findViewById(R.id.swipe_refresh).setVisibility(View.GONE);
                    callData(getResources().getString(R.string.tag_0));
                }else{
                    error_txt.setText("No Internet Connection ! \n swipe-up for Refresh ");
                    error_img.setImageResource(R.drawable.no_internet);
                    Toast.makeText(Top10Activity.this, "No Internet !", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.swipe_refresh).setVisibility(View.VISIBLE);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        displayMenu();

    }

    private void displayMenu() {
    }

    // taking data from php Server
    void callData(String l_tag) {
        tag_type = l_tag;
        progressBar.setVisibility(View.VISIBLE);
        String server_url = "https://privateapps69.000webhostapp.com/PW/files/fragrancecontroller.php?view=all";

        //String server_url = "http://apptechinteractive.com/blog/index.php/app/alldata";

        // check OUTPUT ->  Example,json

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, server_url, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("------->>>>>>>", "onResponse: " + response);
                        try {
                            cast = response.getJSONArray("fragrances");
                            JSONObject myData ;
                            if (cast.length() == 0){
                                Toast.makeText(Top10Activity.this, "Database is empty !", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }else {
                                progressBar.setVisibility(View.INVISIBLE);

                                // Taking all data From the server

                                data_list = new String[cast.length()];

                                // for (int i = 0; i < cast.length()  ; i++) {  // for reverse order
                                for (int i = cast.length()-1,j=0; i >=0  ; i--,j++) {
                                    myData = cast.getJSONObject(i);
                                    Log.d(TAG, "onResponse: "+i+" \n"+myData);

                                    //String data_id = ""+ myData.getString("id");
                                    String data_imageTitle = "" + myData.getString("site_name");
                                    String data_imageDest = "" ;//+ myData.getString("des");
                                    String data_imageUrl = "https://privateapps69.000webhostapp.com/PW/files/" + myData.getString("site_image");
                                    String data_mainUrl = "" + myData.getString("site_url");
                                    String data_type = "" + myData.getString("site_type");


                                    data_list[j] = ""+data_imageTitle;
                                    Log.d(TAG, "onResponse: \n\n"+j+" : "+data_imageTitle+"\n");

                                    if (data_imageUrl.isEmpty()) {
                                        data_imageUrl = getResources().getString(R.string.no_img_url);
                                    }
                                    if (data_imageTitle.isEmpty()) {
                                        data_imageTitle = "No Title Available";
                                    }
                                    if (data_imageDest.isEmpty()) {
                                        data_imageDest = "No Description Available";
                                    }

                                    if (data_type.equals("web")) {
                                        mObj.add(new MyObject(data_imageUrl, data_imageTitle, data_imageDest, data_mainUrl, data_type));
                                    }
                                }

                                initRecyclerView(mObj);
                                // Apply all data on Staggered Recycler Vie
                            }
                        } catch (JSONException e) {
                            error_txt.setText("Server Error ! \n swipe-up for Refresh ");
                            error_img.setImageResource(R.drawable.server_error);
                            findViewById(R.id.swipe_refresh).setVisibility(View.VISIBLE);

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(Top10Activity.this, "Server Error ...!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Top10Activity.this, "Contact on Admin", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                progressBar.setVisibility(View.INVISIBLE);
                error_txt.setText("Server Not Responding ! \n swipe-up for Refresh ");
                error_img.setImageResource(R.drawable.no_internet);
                findViewById(R.id.swipe_refresh).setVisibility(View.VISIBLE);
                Toast.makeText(Top10Activity.this, "your Internet Connection is to slow... ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        Mysingleton.getInstance(Top10Activity.this).addToRequestque(jsonObjectRequest);
    }

    // Staggered Recycler View
    private void initRecyclerView(ArrayList<MyObject>  myObject){
        Log.d(TAG, "initRecyclerView: initializing staggered recyclerview.");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        staggeredRecyclerViewAdapter =
                new StaggeredRecyclerViewAdapter(this, myObject);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
       // recyclerView.addOnScrollListener(new Top10Activity().ScrollListener());
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }


    // checking internet conn
    private boolean isConn(){
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return (networkInfo!=null && networkInfo.isConnected());
    }

    public void do_share2(View view) {
        Intent share_intent = new Intent(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        String shareBody = "_____  P r i v a t e  W o r l d  v3.0.1_____\n" +
                "\n1. All Sites are working ." +
                "\n2. No need to sign-up on any site." +
                "\n3. Secure with pattern lock." +
                "\n4. History will never save." +
                "\n5. Weekly new sites added." +
                "\n\nhttp://prrivateapps69.blogspot.com/2018/08/download-pw-my-private-world-1.html" +
                "\n\n ( For installing successfully you need to uninstall privies version of this app )";
        String shareSub = String.valueOf(R.string.app_name);
        share_intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        share_intent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(share_intent,"Share with your friends.."));
    }
}
