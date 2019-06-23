package com.example.personasmercandiser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personasmercandiser.Fragments.Fragment2;

public class FullJobInfoActivity extends AppCompatActivity {

    TextView shopName, shopAddress, date, time, note;
    ImageView image;
    DatabaseHelper db;
    int jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_job_info);
        shopName = findViewById(R.id.ShopName);
        shopAddress = findViewById(R.id.ShopAddress);
        time = findViewById(R.id.Time);
        date = findViewById(R.id.Date);
        note = findViewById(R.id.Note);
        image = findViewById(R.id.JobPhoto);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        String jobInf = intent.getStringExtra("SelectedItem");
        jobId = db.getJobIdByInf(jobInf);
        image.post(new Runnable() {
            public void run() {
                fillFields();
            }
        });
    }

    private void fillFields() {
        String[] shopInf = db.getJobFullInfo(jobId);
        shopName.setText(shopInf[0]);
        shopAddress.setText(shopInf[1]);
        time.setText(shopInf[2]);
        date.setText(shopInf[3]);
        note.setText(shopInf[4]);

        image.setImageBitmap(Fragment2.getImage(shopInf[5], image));
    }

}
