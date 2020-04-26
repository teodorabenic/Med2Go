package com.example.pokusaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Registracija extends AppCompatActivity {

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.napravitinalog);
        Button sign_up = findViewById(R.id.buttonReg);
        sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText username_raw = findViewById(R.id.editText);
                EditText lozinka_raw = findViewById(R.id.editText3);
                EditText jmbg_raw = findViewById(R.id.editText15);
                final String username = username_raw.toString();
                final String lozinka = lozinka_raw.toString();
                final String jmbg = jmbg_raw.toString();
                boolean uspjesna_registracija = true;
                List<String> result = new ArrayList<>();
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(getAssets().open("BazaPodataka/podaci_korisnici.txt")));
                    String line;
                    while ((line = br.readLine()) != null) {
                        result.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                for(String line : result){
                    if((ordinalIndexOf(line, "'", 1) != -1) && (ordinalIndexOf(line, "'", 2) != -1)) {
                        if (line.substring(ordinalIndexOf(line, "'", 1) + 1, ordinalIndexOf(line, "'", 2)).equals(username)) {
                            Toast.makeText(Registracija.this, "Username je već zauzet!", Toast.LENGTH_LONG).show();
                            uspjesna_registracija = false;
                            break;
                        }
                    }
                }
                if(uspjesna_registracija) {
                    try{
                        /*
                        String data = "'" + username + "' '" + lozinka + "' '" + jmbg + "'" ;
                        File file =getAssets().open("BazaPodataka/podaci_korisnici.txt");
                        FileWriter fileWritter = new FileWriter(file,true);
                        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                        bufferWritter.write(data);
                        bufferWritter.close();
                        fileWritter.close();
                        System.out.println("Uradjeno");
                        */
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Toast.makeText(Registracija.this, "Uspješno ste registrovani!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Registracija.this, Login.class);
                    startActivity(intent);
                }
            }
        });
    }
}
