package com.example.pokusaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Mojnalog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mojnalog);
        TextView izmjena_naloga = findViewById(R.id.editText10);
        izmjena_naloga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Izmjenanaloga.class);
                startActivity(intent);            }
        });
        TextView recepti = findViewById(R.id.editText11);
        recepti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Listarecepata.class);
                startActivity(intent);            }
        });
        TextView naruceno = findViewById(R.id.editText12);
        naruceno.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Naruceno.class);
                startActivity(intent);            }
        });
        TextView search_lijek = findViewById(R.id.editText13);
        search_lijek.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, SearchStrana.class);
                startActivity(intent);             }
        });
        TextView o_nama = findViewById(R.id.editText14);
        o_nama.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Onama.class);
                startActivity(intent);
            }
        });
        TextView kontakt = findViewById(R.id.editText8);
        kontakt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mojnalog.this, Kontakt.class);
                startActivity(intent);            }
        });
    }
}