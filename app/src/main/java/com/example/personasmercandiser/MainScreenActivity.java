package com.example.personasmercandiser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String[] shops;
    private ListView shopListView;
    private DatabaseHelper db;
    private DrawerLayout drawer;
    int performerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        db = new DatabaseHelper(this);

        shopListView = findViewById(R.id.ListView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        fillListView();
    }

    private void fillListView() { // Filling list with shops
        shops = db.getShops();

        // Adapter fill list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shops);
        shopListView.setAdapter(adapter);

        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = shops[position];

                // Receiving date and time for Job
                Date currDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String date = dateFormat.format(currDate);
                dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String time = dateFormat.format(currDate);

                // Receiving information for job
                Intent workScreen = new Intent(MainScreenActivity.this, WorkActivity.class);
                Intent intent = getIntent();
                performerId = intent.getIntExtra("performerId", 0);
                int shopId = db.getShopIdByInf(selectedItem);
                workScreen.putExtra("shopId", shopId);
                workScreen.putExtra("performerId", performerId);

                // Later job will store products and photo from shop
                db.createJob(shopId, performerId, date, time);
                startActivity(workScreen);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выход")
                    .setMessage("Вы уверены, что хотите выйти из своего аккаунта?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MainScreenActivity.this, MainActivity.class));
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.PersAcc) {
            Intent personalCabinet = new Intent(this, PersonalCabinetActivity.class);
            personalCabinet.putExtra("UserId", performerId);
            startActivity(personalCabinet);
        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
