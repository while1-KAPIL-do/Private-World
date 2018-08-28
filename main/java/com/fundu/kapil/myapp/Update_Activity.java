package com.fundu.kapil.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Update_Activity extends AppCompatActivity {

    EditText tv_title,tv_data;
    String tag,title,data,id;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_);

        spinner = (Spinner)findViewById(R.id.spinner);
        tv_title = (EditText)findViewById(R.id.tv_title);
        tv_data = (EditText)findViewById(R.id.tv_data);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.tag_list,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        getIncomingIntent();

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

    public void do_btn_save(View view) {

        String tag2 = spinner.getSelectedItem().toString();
        String title2 = tv_title.getText().toString();
        String data2 = tv_data.getText().toString();

        try {
            BackgroundWorker backgroundWorker2 = new BackgroundWorker(this);
            backgroundWorker2.execute("update", title2, data2, tag2, title, data, tag);

            if (backgroundWorker2.result_msg == 1) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
            }
        }catch (RuntimeException e){
            Toast.makeText(this, "exaption", Toast.LENGTH_SHORT).show();

        }

    }

}
