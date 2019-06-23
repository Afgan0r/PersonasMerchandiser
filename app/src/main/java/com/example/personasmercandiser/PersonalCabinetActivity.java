package com.example.personasmercandiser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PersonalCabinetActivity extends AppCompatActivity {

    ListView jobsList;
    DatabaseHelper db;
    int performerId;
    private String[] jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_cabinet);
        jobsList = findViewById(R.id.JobsList);
        db = new DatabaseHelper(this);
        Intent intent = getIntent();
        performerId = intent.getIntExtra("UserId", 0);
        fillList();
    }

    private void fillList() {
        jobs = db.getJobs(performerId + 1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobs);
        jobsList.setAdapter(adapter);
        jobsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = jobs[position];
                Intent intent = new Intent(PersonalCabinetActivity.this, FullJobInfoActivity.class);
                intent.putExtra("SelectedItem", selectedItem);
                startActivity(intent);
            }
        });
    }
}
