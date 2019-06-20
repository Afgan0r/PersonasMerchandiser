package com.example.personasmercandiser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateProductActivity extends AppCompatActivity {

    EditText nomenclature, productCount, productPrice;
    Button QRCheckButton, enterDataButton;
    DatabaseHelper db;
    int jobId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        nomenclature = findViewById(R.id.Nomenclature);
        productCount = findViewById(R.id.ProductCount);
        productPrice = findViewById(R.id.ProductPrice);
        QRCheckButton = findViewById(R.id.OpenQRCodeCheckerButton);
        enterDataButton = findViewById(R.id.EnterDataButton);
        db = new DatabaseHelper(this);
        jobId = WorkActivity.jobId;
        listeners();
    }

    private void listeners() {
        QRCheckButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO QR check
            }
        });

        enterDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomenclatureString = nomenclature.getText().toString();
                int productCountInt = Integer.parseInt(productCount.getText().toString());
                double productPriceDouble = Double.parseDouble(productPrice.getText().toString());
                if (nomenclatureString != null && productCountInt > 0 && productPriceDouble > 0) {
                    db.addProduct(jobId, nomenclatureString, productCountInt, productPriceDouble);
                    Intent workScreen = new Intent(CreateProductActivity.this, WorkActivity.class);
                    startActivity(workScreen);
                } else {
                    Toast.makeText(CreateProductActivity.this, "Одно из полей пусто", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
