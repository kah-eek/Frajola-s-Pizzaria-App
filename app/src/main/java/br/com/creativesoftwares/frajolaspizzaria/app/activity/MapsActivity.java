package br.com.creativesoftwares.frajolaspizzaria.app.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.creativesoftwares.frajolaspizzaria.app.R;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Http;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Pizzaria;
import br.com.creativesoftwares.frajolaspizzaria.app.model.Produto;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Pizzaria> pizzarias;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        pizzarias = new ArrayList<>();

        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {

//                String jsonReturn = Http.get("http://10.0.2.2/site/Frajolas%20Pizzaria/api/pizzeria.php?action=getLatitudeLongitude");
                String jsonReturn = Http.get("http://www.frajolaspizzaria.com.br/api/pizzeria.php?action=getLatitudeLongitude");

                try{

                    JSONArray jsonArray = new JSONArray(jsonReturn);

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Pizzaria pizzaria = new Pizzaria();

                        pizzaria.setLatitude(String.valueOf(jsonObject.getDouble("latitude")));
                        pizzaria.setLongitude(String.valueOf(jsonObject.getDouble("longitude")));

                        // ADD NEW PRODUCT INTO ARRAY LIST
                        pizzarias.add(pizzaria);
                    }

                }catch(Exception e){
                    Log.e("ERROR", e.getMessage());
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                for (int i = 0; i < pizzarias.size(); i++){
                    LatLng address = new LatLng(Double.parseDouble(pizzarias.get(i).getLatitude()), Double.parseDouble(pizzarias.get(i).getLongitude()));
                    mMap.addMarker(new MarkerOptions().position(address).title("Pizzaria "+i));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
                }

            }
        }.execute();



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
