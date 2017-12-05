package com.example.luisito.clima;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.luisito.clima.api.Api;
import com.example.luisito.clima.api.Main;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//FragmentActivity
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener{

    private GoogleMap mMap;
    private double latMarca,longMarca;
    Context context;
    private Marker marca;
    public static ArrayList<Api> respuestas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        context = this.getApplicationContext();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(MainActivity.latitud, MainActivity.longitud);
        mMap.setOnMapClickListener(this);
        Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(""));
        PicassoMarker pm = new PicassoMarker(marker);
        Picasso.with(context).load("http://openweathermap.org/img/w/"+MainActivity.data.getList().get(0).getWeather().get(0).getIcon()+".png").into(pm);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    public void cargar()
    {
        Api dat = MainActivity.data;
    }

    @Override
    public void onMapClick(LatLng latLng)
    {
        latMarca = latLng.latitude;
        longMarca= latLng.longitude;
        final LatLng ll  = latLng;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String url = "http://api.openweathermap.org/data/2.5/forecast?lat="+latMarca+"&lon="+longMarca+"&units=metric&appid=86f83abe1f2c6984fbea24a30a6cf6b8&lang=es";
        ClimaService service = retrofit.create(ClimaService.class);
        Call<Api> call = service.api(url);
        call.enqueue(new Callback<Api>() {
            @Override
            public void onResponse(Call<Api> call, Response<Api> response) {
                Marker marker = mMap.addMarker(new MarkerOptions().position(ll).title(response.body().getCity().getName()));

                respuestas.add(response.body());
                PicassoMarker pm = new PicassoMarker(marker);
                Picasso.with(context).load("http://openweathermap.org/img/w/"+response.body().getList().get(0).getWeather().get(0).getIcon()+".png").into(pm);

                Toast.makeText(context,"Temperatura"+response.body().getList().get(0).getMain().getTemp(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Api> call, Throwable t) {
                Toast.makeText(context,"Erro",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lista:
                Intent i = new Intent(this,ListMarcas.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
