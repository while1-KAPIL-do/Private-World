package com.fundu.kapil.myapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class Insert_Activity extends AppCompatActivity {

    DatabaseHelper mydb;
    Spinner spinner;
    EditText editText_title;
    TextView editText_data;

    //***************** camera to text ************************
    SurfaceView surfaceView;
    CameraSource cameraSource;
    final int requestCameraPermissionId = 1001;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Toast.makeText(this, "onReq", Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case requestCameraPermissionId: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "T 1", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    try {
                        cameraSource.start(surfaceView.getHolder());
                        Toast.makeText(this, "try T 2", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "exp T 2", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(this, "T 3", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "T 4", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void cameraToText() {

        surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast.makeText(this, "Cannot Detect", Toast.LENGTH_SHORT).show();

//            if(((Insert_Activity)getActivity()).hasLowStorage()){
//                Toast.makeText(this, "Low Storage", Toast.LENGTH_SHORT).show();
//            }

        } else {
            Toast.makeText(this, "p 2", Toast.LENGTH_SHORT).show();
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Insert_Activity.this,new String[] {Manifest.permission.CAMERA},
                                    requestCameraPermissionId);
                            return;
                        }
                        cameraSource.start(surfaceView.getHolder());
                    }catch (IOException e ){
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> item = detections.getDetectedItems();
                    if(item.size() != 0){
                        editText_data.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i=0 ;i<item.size();++i){
                                    TextBlock ite = item.valueAt(i);
                                    stringBuilder.append(ite.getValue());
                                    stringBuilder.append("\n");
                                }
                                editText_data.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });
        }
    }

    //***************** / camera to text ************************


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_);

        mydb = new DatabaseHelper(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        editText_title = (EditText) findViewById(R.id.editText_title);
        editText_data = (TextView) findViewById(R.id.editText_data);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tag_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        cameraToText();

    }


    public void inset_btn(View view) {

        int a;
        String tag = spinner.getSelectedItem().toString();
        String title = editText_title.getText().toString();
        String data = editText_data.getText().toString();

        // check internet Connection
        if(! isConn()) {
            Toast.makeText(this, "Your Internet Conn is OFF", Toast.LENGTH_SHORT).show();
        }else{

            // check data is Empty
            if( title.isEmpty() && data.isEmpty() ){
                Toast.makeText(this, "please fill the data first !", Toast.LENGTH_SHORT).show();
            }else {

                //check data is copy
//                BackgroundWorker backgroundWorker1 = new BackgroundWorker(this);
//                backgroundWorker1.execute("isCopy", title, data, tag);
//                if(backgroundWorker1.countT()!=1){
//                    Toast.makeText(this, "These Data is already inserted !", Toast.LENGTH_LONG).show();
//                }else {
//                    //.....
//                }

                BackgroundWorker backgroundWorker2 = new BackgroundWorker(this);
                backgroundWorker2.execute("insert", title, data, tag);
                a = backgroundWorker2.countT();
                // temp Toast--
                Toast.makeText(this, ": " + a + " | " + backgroundWorker2.result_msg + " :", Toast.LENGTH_LONG).show();

                int isInserted = mydb.insertData(title, data, tag);
                if (backgroundWorker2.result_msg == 1) {
                    // int isInserted = mydb.insertData(title, data, tag);
                    Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Insert_Activity.this, Home_Activity.class));
                } else {
                    Toast.makeText(this, "Data Not Inserted\ntry again..", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(Insert_Activity.this, "tag = "+tag+"\ntitle = "+title+"\ndata = "+data, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean isConn(){
        ConnectivityManager connectivityManager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }


}
