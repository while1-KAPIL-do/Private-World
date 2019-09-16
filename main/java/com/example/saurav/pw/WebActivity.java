package com.example.saurav.pw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web);

        //********* AdMob ***************

        //********* InterstitialAd
        AdRequest mAdRequest2 = new AdRequest.Builder().build();
        mInterstitialAd = new InterstitialAd(WebActivity.this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_addId));
        mInterstitialAd.loadAd(mAdRequest2);
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                displayInterAd();
            }
        });
        //********* / InterstitialAd

        //*********/ AdMob ***************


        //********* webview ***************
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        Intent i = getIntent();
        String url = i.getStringExtra("url");

        webView = (WebView) findViewById(R.id.webview);
        //
        webView.setWebChromeClient(new MyChrome(){
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                setTitle("Loading...");
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                setTitle(view.getTitle());
            }
        });

        webView.loadUrl(url);

        //********* webview ***************
    }

    public void displayInterAd(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }



    // for Full Screen

    private class MyChrome extends WebChromeClient{

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullScreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome(){ }

        public  Bitmap getDefaultVideoPoster(){
            if(mCustomView == null){
                return  null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(),2130837573);
        }

        public void onHideCustomView(){
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View parentView,WebChromeClient.CustomViewCallback paramCustomViewCallback){
            if (this.mCustomView != null){
                onHideCustomView();
                return;
            }
            this.mCustomView= parentView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation =  getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1,-1));
            getWindow().getDecorView().setSystemUiVisibility(3846);

        }
    }




    public void btnShare(View view) {
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

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){ webView.goBack();}
        else{super.onBackPressed(); }
    }
    public void btnHome(View view) {
        super.onBackPressed();
        finish();
    }
}



//
