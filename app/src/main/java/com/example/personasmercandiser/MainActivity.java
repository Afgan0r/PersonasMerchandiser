package com.example.personasmercandiser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText emailText, passText;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.email);
        passText = findViewById(R.id.pass);
        db = new DatabaseHelper(this);

        buttonListeners();
    }



    public void buttonListeners() {

        Button signIn = findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean res = db.checkLoginAndPass(emailText.getText().toString().trim(), passText.getText().toString().trim());
                if (res) {
                    // Remember user id
                    int loginId = db.getPerformerIdByEmail(emailText.getText().toString());
                    Intent mainScreenActivity = new Intent(MainActivity.this, MainScreenActivity.class);
                    // PerformerId == User id
                    mainScreenActivity.putExtra("performerId", loginId);
                    startActivity(mainScreenActivity);
                } else {
                    Toast.makeText(MainActivity.this, "Неправильный логин или пароль.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
