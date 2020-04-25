package com.example.pokusaj;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

/**
 * Display {@link SymbolLayer} icons on the map.
 */
public class SearchStrana extends AppCompatActivity implements
        OnMapReadyCallback {

    // Create names for the map's source, icon, and layer IDs.
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private MapView mapView;
    private static List<Feature> symbolLayerIconFeatureList = new ArrayList<>();

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<String> readFileInList(String fileName) {
        List<String> lines = Collections.emptyList();
        try { lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8); }
        catch (IOException e) { e.printStackTrace(); }
        return lines;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean pretragaPoApoteci(String key) throws IOException {
        boolean ans = false;
        List<String> result = new ArrayList<>();
        BufferedReader br = null;

        try {

            br = new BufferedReader(new InputStreamReader(getAssets().open("BazaPodataka/spiskovi_apoteka.txt")));

            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }
        for(String line : result){
            if(line.contains(key)){
                ans = true;
                double coordLng = Double.parseDouble(line.substring(ordinalIndexOf(line, "'", 7)+1, ordinalIndexOf(line, "'", 8)));
                double coordLat = Double.parseDouble(line.substring(ordinalIndexOf(line, "'", 9)+1, ordinalIndexOf(line, "'", 10)));
                System.out.println(coordLat + " " + coordLng);
                symbolLayerIconFeatureList.add(Feature.fromGeometry(
                        Point.fromLngLat(coordLat, coordLng)));
            }
        }
        return ans;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.searchstrana);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        ImageView kucica = findViewById(R.id.imageView4);
        kucica.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SearchStrana.this, Mojnalog.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {

        ImageView search_clue = findViewById(R.id.imageView2);
        search_clue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                symbolLayerIconFeatureList.clear();
                final EditText search = findViewById(R.id.editText7);
                String pretraga = search.getText().toString();
                try{
                    boolean apoteka = pretragaPoApoteci(pretraga);
                }catch (IOException e){
                    System.out.println("Greska");
                }
                System.out.println(symbolLayerIconFeatureList.size());
                mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
                        .withImage(ICON_ID, BitmapFactory.decodeResource(
                                SearchStrana.this.getResources(), R.drawable.marker))
                        .withSource(new GeoJsonSource(SOURCE_ID,
                                FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
                        .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                                .withProperties(PropertyFactory.iconImage(ICON_ID),
                                        iconAllowOverlap(true),
                                        iconOffset(new Float[] {0f, -9f}))
                        ), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                    }
                });

            }
        });
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-54.14164, -33.981818)));
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(-56.990533, -30.583266)));

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")

                .withImage(ICON_ID, BitmapFactory.decodeResource(
                        SearchStrana.this.getResources(), R.drawable.marker))
                .withSource(new GeoJsonSource(SOURCE_ID,
                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
                .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                        .withProperties(PropertyFactory.iconImage(ICON_ID),
                                iconAllowOverlap(true),
                                iconOffset(new Float[] {0f, -9f}))
                ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}

