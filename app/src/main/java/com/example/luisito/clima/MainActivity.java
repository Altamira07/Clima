package com.example.luisito.clima;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisito.clima.api.Api;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements LocationListener{
    LocationManager locationManager;
    public  static  double longitud, latitud;
    Context context;
    Retrofit retrofit;
    @BindView(R.id.txvCiudad)
    TextView txvCiudad;
    @BindView(R.id.txvTemperatura)
    TextView txvTemperatura;
    @BindView(R.id.imvClimaD)
    ImageView imvClima;
    @BindView(R.id.txtTempMin)
    TextView txtTempMin;
    @BindView(R.id.txtTempMax)
    TextView txtTempMax;

    public static Api data;
    private RecyclerView mRecycleView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        context = this.getApplicationContext();
        mRecycleView = (RecyclerView) findViewById(R.id.dReclicle);
        mRecycleView.setHasFixedSize(true);
        mLayoutManayer = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManayer);


        cargar();
    }


    private void cargar()
    {
        ClimaService service = retrofit.create(ClimaService.class);
        Call<Api> call;
        String url;
        if(getCoodenadas())
            url = "http://api.openweathermap.org/data/2.5/forecast?lat="+latitud+"&lon="+longitud+"&units=metric&appid=86f83abe1f2c6984fbea24a30a6cf6b8&lang=es";
        else
            url = "http://api.openweathermap.org/data/2.5/forecast?lat=20.527961&lon=-100.811288&units=metric&appid=86f83abe1f2c6984fbea24a30a6cf6b8&lang=es";
        call = service.api(url);
        call.enqueue(new Callback<Api>() {
            @Override
            public void onResponse(Call<Api> call, Response<Api> response) {
                data = response.body();
                txvCiudad.setText(response.body().getCity().getName());
                txvTemperatura.setText(response.body().getList().get(0).getMain().getTemp() +"°C");
                txtTempMax.setText("Maxima "+response.body().getList().get(0).getMain().getTempMax()+"°C");
                txtTempMin.setText("Minima "+response.body().getList().get(0).getMain().getTempMin()+"°C");

                Picasso.with(context).load("http://openweathermap.org/img/w/"+response.body().getList().get(0).getWeather().get(0).getIcon()+".png").into(imvClima);

                mAdapter = new AdapterDias(response.body().getList(),context);
                mRecycleView.setAdapter(mAdapter);
              }
            @Override
            public void onFailure(Call<Api> call, Throwable t) {
                Log.e("Fallo",t.getMessage());
            }
        });
    }

    private boolean getCoodenadas()
    {
        Location ubicacion;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,10,this);
            ubicacion = (gps) ?
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER):
                    locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            latitud= ubicacion.getLatitude();
            longitud = ubicacion.getLongitude();
            return  true;
        }catch (SecurityException se) {
            Log.e("Erro",se.getMessage());
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId())
        {
            case R.id.bMapa:
                i = new Intent(this,MapsActivity.class);
                startActivity(i);
                break;
            case R.id.mAcercaDe:
                i = new Intent(this,AcercaDeActivity.class);
                startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
