package com.example.personasmercandiser;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.personasmercandiser.Fragments.Fragment1;
import com.example.personasmercandiser.Fragments.Fragment2;

public class WorkActivity extends AppCompatActivity {

    private String shopInf;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

//        Intent intent = getIntent();
//        shopInf = intent.getStringExtra("ShopInf");

        tabLayout = findViewById(R.id.tablayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new Fragment1(), "Товары");
        adapter.AddFragment(new Fragment2(), "Фото");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void listeners() {

    }

    private String getShopName(String shopInf) {
        int start = shopInf.lastIndexOf("Название магазина - ") + 1;
        int end = shopInf.indexOf("Адресс - ") - 1;
        char[] buf = new char[end - start];
        shopInf.getChars(start, end, buf, 0);
        return new String(buf);
    }
}
