package com.example.pokusaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registracija extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.napravitinalog);
        Button sign_up = findViewById(R.id.buttonReg);
        sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Registracija.this, SearchStrana.class);
                startActivity(intent);
            }
        });
    }
}
