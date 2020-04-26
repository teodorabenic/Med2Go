package com.example.pokusaj;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
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
import java.util.LinkedList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

/**
 * Display {@link SymbolLayer} icons on the map.
 */
public class SearchStrana extends AppCompatActivity implements
        OnMapReadyCallback, PermissionsListener {

    // Create names for the map's source, icon, and layer IDs.
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private MapView mapView;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private static List<Feature> symbolLayerIconFeatureList = new ArrayList<>();

    public static int ordinalIndexOf(String str, String substr, int n) {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

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

    public void pretragaPoProizvodu(String key){
        final AssetManager assets = getAssets();
        try{
            final String[] names = assets.list( "BazaPodataka/Proizvodi" );
            LinkedList<String> apoteke_sa_proizvodom = new LinkedList<String>();
            for(String apoteka : names){
                String path = "BazaPodataka/Proizvodi/" + apoteka;
                List<String> result = new ArrayList<>();
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(getAssets().open(path)));
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
                    if(line.substring(ordinalIndexOf(line, "'", 1) + 1, ordinalIndexOf(line, "'", 2)).equals(key)){
                        apoteke_sa_proizvodom.add(apoteka);
                        break;
                    }
                }
            }
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
                for(String apoteka_tr : apoteke_sa_proizvodom) {
                    if (line.substring(ordinalIndexOf(line, "'", 1) + 1, ordinalIndexOf(line, "'", 2)).equals(apoteka_tr.substring(0, apoteka_tr.length()-4))) {
                        double coordLng = Double.parseDouble(line.substring(ordinalIndexOf(line, "'", 7) + 1, ordinalIndexOf(line, "'", 8)));
                        double coordLat = Double.parseDouble(line.substring(ordinalIndexOf(line, "'", 9) + 1, ordinalIndexOf(line, "'", 10)));
                        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                                Point.fromLngLat(coordLat, coordLng)));
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Ne postoji direktorijum.");
            return;
        }
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
        SearchStrana.this.mapboxMap = mapboxMap;
        ImageView search_clue = findViewById(R.id.imageView2);
        search_clue.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {
                symbolLayerIconFeatureList.clear();
                final EditText search = findViewById(R.id.editText7);
                String pretraga = search.getText().toString();
                try{
                    boolean apoteka = pretragaPoApoteci(pretraga);
                    if(symbolLayerIconFeatureList.size()==0) pretragaPoProizvodu(pretraga);
                }catch (IOException e){
                    System.out.println("Greska");
                }
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
                        enableLocationComponent(style);
                    }
                });

            }
        });

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
                enableLocationComponent(style);
            }
        });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());

// Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
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

