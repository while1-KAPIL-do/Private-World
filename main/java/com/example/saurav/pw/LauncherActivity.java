package com.example.saurav.pw;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import io.paperdb.Paper;

public class LauncherActivity extends AppCompatActivity {

    String save_pattern_key = "pattern_code";
    String final_pattern = "";
    PatternLockView mPatternLockView;
    AdView mAdView1,mAdView2,mAdView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        if(save_pattern != null && !save_pattern.equals("null")){
            setContentView(R.layout.pattern_screen);

            //********* BannerAd
            MobileAds.initialize(this,getResources().getString(R.string.app_Id));
            mAdView2 = findViewById(R.id.adview2);
            mAdView3 = findViewById(R.id.adview3);
            AdRequest mAdRequest = new AdRequest.Builder().build();
            mAdView2.loadAd(mAdRequest);
            mAdView3.loadAd(mAdRequest);
            //********* BannerAd

            mPatternLockView = findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    String final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
                    if(final_pattern.equals(save_pattern)) {
                        startActivity(new Intent(LauncherActivity.this,HomeActivity.class));
                        finish();
                    }
                    else
                        Toast.makeText(LauncherActivity.this, "Wrong Pattern !", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCleared() {

                }
            });
        }else{
            setContentView(R.layout.activity_launcher);

            MobileAds.initialize(this,getResources().getString(R.string.app_Id));
            //********* BannerAd
            mAdView1 = findViewById(R.id.adview1);
            AdRequest mAdRequest = new AdRequest.Builder().build();
            mAdView1.loadAd(mAdRequest);
            //********* BannerAd
            mPatternLockView = findViewById(R.id.pattern_lock_view);


            AlertDialog.Builder builder = new AlertDialog.Builder(LauncherActivity.this);
            builder.setTitle("Age Restricted+18 , Are you 18+ ?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Toast.makeText(LauncherActivity.this, "Now , set Pattern to secure !", Toast.LENGTH_SHORT).show();
                    mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onProgress(List<PatternLockView.Dot> progressPattern) {

                        }

                        @Override
                        public void onComplete(List<PatternLockView.Dot> pattern) {
                            final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
                        }

                        @Override
                        public void onCleared() {

                        }
                    });

                    Button btnSetup = findViewById(R.id.btnSetPattern);
                    btnSetup.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Paper.book().write(save_pattern_key,final_pattern);
                            Toast.makeText(LauncherActivity.this, "Pattern Saved", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LauncherActivity.this,HomeActivity.class));
                            finish();
                        }
                    });


                }
            }).setNegativeButton("NO",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    LauncherActivity.super.onBackPressed();
                    finish();
                }
            }).setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();



        }

    }
}

