package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

// Autor: Nassim hamed ramirez 20193265

public class ForecasterAdapter extends RecyclerView.Adapter<ForecasterAdapter.ForecastViewHolder> {

    private List<Forecast> forecasts = new ArrayList<>();

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public void removeLastItem() {
        if (!forecasts.isEmpty()) {
            forecasts.remove(forecasts.size() - 1);
        }
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Forecast forecast = forecasts.get(position);
        holder.textDate.setText(forecast.getDate());
        holder.textMaxTemp.setText("Max: " + forecast.getMaxTemp());
        holder.textMinTemp.setText("Min: " + forecast.getMinTemp());
        holder.textCondition.setText(forecast.getCondition());
        // Puedes mostrar más atributos si lo deseas
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView textDate, textMaxTemp, textMinTemp, textCondition;
        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.textDate);
            textMaxTemp = itemView.findViewById(R.id.textMaxTemp);
            textMinTemp = itemView.findViewById(R.id.textMinTemp);
            textCondition = itemView.findViewById(R.id.textCondition);
        }
    }
}
