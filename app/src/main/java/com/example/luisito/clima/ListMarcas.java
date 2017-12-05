package com.example.luisito.clima;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ListMarcas extends AppCompatActivity {
    private RecyclerView mRecycleView;

    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManayer;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_marcas);
        context = this.getApplicationContext();
        mRecycleView = (RecyclerView) findViewById(R.id.dReclycleCiudad);
        mRecycleView.setHasFixedSize(true);
        mLayoutManayer = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManayer);

        mAdapter = new AdapterCiudad(this);
        mRecycleView.setAdapter(mAdapter);
    }
}
