package com.example.pokusaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Mojnalog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mojnalog);
        Button izmjena_naloga = findViewById(R.id.button11);
        izmjena_naloga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Izmjenanaloga.class);
                startActivity(intent);            }
        });
        Button recepti = findViewById(R.id.button12);
        recepti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Listarecepata.class);
                startActivity(intent);            }
        });
        Button naruceno = findViewById(R.id.button13);
        naruceno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Naruceno.class);
                startActivity(intent);            }
        });
        Button search_lijek = findViewById(R.id.button14);
        search_lijek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, SearchStrana.class);
                startActivity(intent);             }
        });
        Button o_nama = findViewById(R.id.button15);
        o_nama.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Onama.class);
                startActivity(intent);
            }
        });
        Button kontakt = findViewById(R.id.button16);
        kontakt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Kontakt.class);
                startActivity(intent);            }
        });
    }
}