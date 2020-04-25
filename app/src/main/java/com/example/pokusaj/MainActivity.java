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
                setContentView(R.layout.loginstrana);
            }
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
        /*
        Button login = findViewById(R.id.button3);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.searchstrana);
            }
        });
        TextView izmjena_naloga = findViewById(R.id.editText10);
        izmjena_naloga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.izmjenanaloga);
            }
        });
        TextView recepti = findViewById(R.id.editText11);
        recepti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.listarecepata);
            }
        });
        TextView naruceno = findViewById(R.id.editText12);
        naruceno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.naruceno);
            }
        });
        TextView search_lijek = findViewById(R.id.editText13);
        search_lijek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.searchstrana);
            }
        });
        TextView o_nama = findViewById(R.id.editText14);
        o_nama.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.onama);
            }
        });
        TextView kontakt = findViewById(R.id.editText8);
        kontakt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.kontaktirajtenas);
            }
        });
         */
    }
}
