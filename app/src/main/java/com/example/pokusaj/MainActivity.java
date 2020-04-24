package com.example.pokusaj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prvastrana);
        Button dug_login = findViewById(R.id.prijavi_se);
        dug_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.loginstrana);
            }
        });
        Button dug_nastavi = findViewById(R.id.dugmereg);
        dug_nastavi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.searchstrana);
            }
        });
    }
}
