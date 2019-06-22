package com.example.personasmercandiser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.personasmercandiser.Fragments.Fragment1;
import com.example.personasmercandiser.Fragments.Fragment2;

public class WorkActivity extends AppCompatActivity {

    private int jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        DatabaseHelper db = new DatabaseHelper(this);

        Intent intent = getIntent();
        jobId = db.getJobId();

        // ViewPagerAdapter helps me to use TabLayout
        TabLayout tabLayout = findViewById(R.id.tablayout_id);
        ViewPager viewPager = findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putInt("JobId", jobId);
        Fragment fragment1 = new Fragment1();
        fragment1.setArguments(bundle);

        bundle.clear();
        bundle.putInt("JobId", jobId);
        Fragment fragment2 = new Fragment2();
        fragment2.setArguments(bundle);

        adapter.AddFragment(fragment1, "Товары");
        adapter.AddFragment(fragment2, "Фото");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false)
                .setMessage("Если вы выйдете, то введенные продукты и фото не сохраняться! Вы уверены что хотите выйти?")
                .setTitle("Выход")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper db = new DatabaseHelper(WorkActivity.this);
                        db.deleteLastJobAndProducts(jobId);
                        startActivity(new Intent(WorkActivity.this, MainScreenActivity.class));
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
