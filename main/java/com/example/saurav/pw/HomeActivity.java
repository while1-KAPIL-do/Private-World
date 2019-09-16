package com.example.saurav.pw;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    AdView mAdView;
    RewardedVideoAd mRewardedVideoAd;


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
    Button btn_version;

    private ArrayList<MyObject> mObj = new ArrayList<>();


    // Scheduled Notification Time in MILISECOUNDS
    // ( 1000 = 1 sec | 1000*60*60 = 1 hr | 1000*60*60*24 = 1 day )
    int SECHDULED_NOTI_TIME = 1000*60*60*12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Notification after 1 hr after opening app
        // ( 1000 = 1 sec | 1000*60*60 = 1 hr | 1000*60*60*24 = 1 day )
        scheduleNotification(getNotification(), SECHDULED_NOTI_TIME);


        //------------ads

        //********* AdMob ***************
         //********* BannerAd
         MobileAds.initialize(this,getResources().getString(R.string.app_Id));
         mAdView = findViewById(R.id.adview);
         AdRequest mAdRequest = new AdRequest.Builder().build();
        mAdView.loadAd(mAdRequest);
         //********* BannerAd
         // ******** video
         mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
         mRewardedVideoAd.getRewardedVideoAdListener();
         loadRewordedVideoAds();
         //********* video

         //*********/ AdMob ***************
    }

    // *************** for Video Ads *******************
    private void loadRewordedVideoAds(){
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(getResources().getString(R.string.rewarded_addId),new AdRequest.Builder().build());
        }

        //-------------------------------


        progressBar = findViewById(R.id.progress);



        // error views
        error_img =  findViewById(R.id.no_internet_img);
        error_txt =   findViewById(R.id.no_internet_txt);

        Animation animation = new AlphaAnimation(1,0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        btn_version = findViewById(R.id.btn_isNewVersion);
        btn_version.startAnimation(animation);


        isNewVersionUpdated();

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
                    Toast.makeText(HomeActivity.this, "No Internet !", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(HomeActivity.this, "Database is empty !", Toast.LENGTH_SHORT).show();
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

                                    if (data_type.equals("top") || data_type.equals("update")) {
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
                            Toast.makeText(HomeActivity.this, "Server Error ...!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(HomeActivity.this, "Contact on Admin", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HomeActivity.this, "your Internet Connection is to slow... ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        Mysingleton.getInstance(HomeActivity.this).addToRequestque(jsonObjectRequest);
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
        recyclerView.addOnScrollListener(new HomeActivity.ScrollListener());
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }

    // Notification
    private Notification getNotification() {

        // scheduled Noti Initializing
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(getResources().getString(R.string.scheduled_notification_title));
        builder.setContentText(getResources().getString(R.string.scheduled_notification_text));
        // set Large icon
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_active_black_24dp));
        // set Small icon
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // On click on Notifi...
        Intent noti_intent = new Intent(this, LauncherActivity.class);
        PendingIntent content_intent = PendingIntent.getActivity(this, 0, noti_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.ic_menu_view,"View",content_intent);
        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(path);
        return builder.build();
    }
    private void scheduleNotification(Notification notification, int delay) {

        // set Alarm for notification
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
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

    public void do_othersites(View view) {

        startActivity(new Intent(HomeActivity.this,Top10Activity.class));

    }

    public void do_share(View view) {
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

    @SuppressLint("IntentReset")
    public void do_complain(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setData(Uri.parse("mailto:"));
        String[] to = { "one02946@gamil.com"};
        i.putExtra(Intent.EXTRA_EMAIL,to);
        i.putExtra(Intent.EXTRA_SUBJECT,"Private World");
        i.setType("message/rfc822");
        Intent chooser = Intent.createChooser(i,"Use Gmail ");
        startActivity(chooser);

    }
    String versionCode="0";

    public void isNewVersionUpdated(){

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            versionCode = ""+packageInfo.versionCode;

            String server_url_for_update = "https://privateapps69.000webhostapp.com/PW/files/AppVersionCode.html";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url_for_update,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "onResponse: -----------------------"+response);
                          if (response.equals(versionCode)){
                              btn_version.setVisibility(View.GONE);
                          }else{
                              btn_version.setVisibility(View.VISIBLE);
                          }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    error.printStackTrace();
                }
            });
            Mysingleton.getInstance(HomeActivity.this).addToRequestque(stringRequest);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void do_isNewVersion(View view) {
        view.clearAnimation();
        Intent k = new Intent(Intent.ACTION_VIEW);
        k.setData(Uri.parse("http://prrivateapps69.blogspot.com/2018/08/download-pw-my-private-world-1.html"));
        startActivity(k);
    }


    // alert dialog on back press

    private class ScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            // staggeredRecyclerViewAdapter.invalidateSpanAssignments();
        }
    }

    @Override
    public void onBackPressed() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }

            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Are you sure want to exit ?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    HomeActivity.super.onBackPressed();
                }
            }).setNegativeButton("NO",null).setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

}
