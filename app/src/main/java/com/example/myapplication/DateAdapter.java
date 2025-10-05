package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.HourlyForecastViewHolder> {

    private List<HourlyForecast> hourlyForecasts = new ArrayList<>();

    public void setHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
        this.hourlyForecasts = hourlyForecasts;
    }

    @NonNull
    @Override
    public HourlyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_hourly_forecast, parent, false);
        return new HourlyForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyForecastViewHolder holder, int position) {
        HourlyForecast hf = hourlyForecasts.get(position);
        holder.textHour.setText(hf.getHour());
        holder.textTemp.setText("Temp: " + hf.getTemp());
        holder.textCondition.setText(hf.getCondition());
        holder.textHumidity.setText("Humedad: " + hf.getHumidity());
        holder.textRain.setText("Lluvia: " + hf.getRain());
        // Puedes mostrar más atributos si lo deseas
    }

    @Override
    public int getItemCount() {
        return hourlyForecasts.size();
    }

    static class HourlyForecastViewHolder extends RecyclerView.ViewHolder {
        TextView textHour, textTemp, textCondition, textHumidity, textRain;
        public HourlyForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            textHour = itemView.findViewById(R.id.textHour);
            textTemp = itemView.findViewById(R.id.textTemp);
            textCondition = itemView.findViewById(R.id.textConditionHour);
            textHumidity = itemView.findViewById(R.id.textHumidity);
            textRain = itemView.findViewById(R.id.textRain);
        }
    }
}
