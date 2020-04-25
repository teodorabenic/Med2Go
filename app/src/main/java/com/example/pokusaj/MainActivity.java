package com.example.pokusaj;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prvastrana);
        CardView dugme1 = findViewById(R.id.dugme1);
        dugme1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);            }
        });
        TextView dug_nastavi = findViewById(R.id.textView4);
        dug_nastavi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchStrana.class);
                startActivity(intent);
            }
        });
        CardView registruj_se = findViewById(R.id.registruj_se);
        registruj_se.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registracija.class);
                startActivity(intent);
            }
        });
    }
}
