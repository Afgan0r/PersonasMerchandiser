package com.example.personasmercandiser;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button signIn;
    private EditText emailText, passText;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailText = findViewById(R.id.email);
        passText = findViewById(R.id.pass);
        db = new DatabaseHelper(this);
        boolean res = db.fillTables();
        if (res) {
            Toast.makeText(MainActivity.this ,"Не удалось подключиться к базе данных.", Toast.LENGTH_SHORT).show();
        }
        buttonListeners();
    }



    public void buttonListeners() {

        signIn = findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res = db.checkLoginAndPass(emailText.getText().toString().trim(), passText.getText().toString().trim());
                if (res) {
//                    Toast.makeText(MainActivity.this, "Успешный вход", Toast.LENGTH_LONG).show();
                    Intent mainScreenActivity = new Intent(MainActivity.this, MainScreenActivity.class);
                    startActivity(mainScreenActivity);
                } else {
                    Toast.makeText(MainActivity.this, "Неправильный логин или пароль.", Toast.LENGTH_LONG).show();
                }
//                Intent mainMenuIntent
            }
        });
    }
}
