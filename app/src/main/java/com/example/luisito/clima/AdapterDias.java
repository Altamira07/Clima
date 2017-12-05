package com.example.luisito.clima;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by luisito on 20/11/17.
 */

public class AdapterDias  extends RecyclerView.Adapter<AdapterDias.ViewHolder>
{
    private List<com.example.luisito.clima.api.List> data;
    private Context context;
    public AdapterDias(List<com.example.luisito.clima.api.List> data, Context context)
    {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater .from(parent.getContext()).inflate(R.layout.card_dia,parent,false);
        ViewHolder vh  = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            holder.temp.setText(""+data.get(position).getMain().getTemp()+" °C");
            Picasso.with(context).load("http://openweathermap.org/img/w/"+data.get(position).getWeather().get(0).getIcon()+".png").into(holder.iconClima);
            holder.dia.setText(data.get(position).getDtTxt()+"");
            holder.estado.setText(data.get(position).getWeather().get(0).getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iconClima;
        public TextView temp;
        public TextView dia;
        public TextView estado;
        public ViewHolder(View view) {
            super(view);
            iconClima = view.findViewById(R.id.imvClima);
            temp = view.findViewById(R.id.txtTemp);
            dia = view.findViewById(R.id.dia);
            estado = view.findViewById(R.id.estado);

        }
    }
}
