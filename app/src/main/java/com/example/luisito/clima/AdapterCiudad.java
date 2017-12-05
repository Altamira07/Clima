package com.example.luisito.clima;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Created by luisito on 22/11/17.
 */

public class AdapterCiudad extends  RecyclerView.Adapter<AdapterCiudad.ViewHolderCiudad>{

    private Context context;
    public AdapterCiudad(Context context)
    {
        this.context = context;
    }
    @Override
    public AdapterCiudad.ViewHolderCiudad onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ciudad,parent,false);
        AdapterCiudad.ViewHolderCiudad vh  = new AdapterCiudad.ViewHolderCiudad(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterCiudad.ViewHolderCiudad holder, int position) {
        holder.ciudad.setText(MapsActivity.respuestas.get(position).getCity().getName()+", "+MapsActivity.respuestas.get(position).getCity().getCountry());
        holder.temp.setText(MapsActivity.respuestas.get(position).getList().get(0).getMain().getTemp()+"°C");
        Picasso.with(context).load("http://openweathermap.org/img/w/"+MapsActivity.respuestas.get(position).getList().get(0).getWeather().get(0).getIcon()+".png").into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return MapsActivity.respuestas.size();
    }

    public static class ViewHolderCiudad extends RecyclerView.ViewHolder
    {
        private TextView ciudad;
        private TextView temp;
        private ImageView imageView;

        public ViewHolderCiudad(View itemView) {
            super(itemView);
            ciudad = (TextView) itemView.findViewById(R.id.ciudad);
            imageView =(ImageView) itemView.findViewById(R.id.imvClimaC);
            temp = (TextView) itemView.findViewById(R.id.txtTempC);
        }
    }
}
