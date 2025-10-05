package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateFragment extends Fragment {

    private EditText editTextIdLocation, editTextFecha;
    private Button btnBuscar;
    private RecyclerView recyclerView;
    private DateAdapter adapter;
    private final String API_KEY = "5586a0acd5a345e0b4361158250210";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date, container, false);

        editTextIdLocation = view.findViewById(R.id.editTextIdLocationDate);
        editTextFecha = view.findViewById(R.id.editTextFecha);
        btnBuscar = view.findViewById(R.id.btnBuscarDate);
        recyclerView = view.findViewById(R.id.recyclerViewDate);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DateAdapter();
        recyclerView.setAdapter(adapter);

        btnBuscar.setOnClickListener(v -> {
            String idLocation = editTextIdLocation.getText().toString().trim();
            String fecha = editTextFecha.getText().toString().trim();
            if (!idLocation.isEmpty() && !fecha.isEmpty()) {
                buscarPronosticoPorFecha(idLocation, fecha);
            }
        });

        return view;
    }

    private void buscarPronosticoPorFecha(String idLocation, String fecha) {
        WeatherApiService api = RetrofitInstance.getInstance().create(WeatherApiService.class);
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        // Decide si usar future o history
        if (fecha.compareTo(today) > 0) {
            // Future
            api.getFuture(API_KEY, "id:" + idLocation, fecha).enqueue(new Callback<FutureResponse>() {
                @Override
                public void onResponse(Call<FutureResponse> call, Response<FutureResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<HourlyForecast> hourlyList = parseHourly(response.body().getForecast());
                        adapter.setHourlyForecasts(hourlyList);
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<FutureResponse> call, Throwable t) {
                    // Maneja el error
                }
            });
        } else {
            // History
            api.getHistory(API_KEY, "id:" + idLocation, fecha).enqueue(new Callback<HistoryResponse>() {
                @Override
                public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<HourlyForecast> hourlyList = parseHourly(response.body().getForecast());
                        adapter.setHourlyForecasts(hourlyList);
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<HistoryResponse> call, Throwable t) {
                    // Maneja el error
                }
            });
        }
    }

    // Extrae la lista de pronóstico por hora del modelo ForecastData
    private List<HourlyForecast> parseHourly(ForecastResponse.ForecastData forecastData) {
        List<HourlyForecast> result = new ArrayList<>();
        if (forecastData != null && forecastData.getForecastday() != null && !forecastData.getForecastday().isEmpty()) {
            ForecastResponse.ForecastDay fd = forecastData.getForecastday().get(0);
            // Suponiendo que fd tiene un campo List<Hour> hour
            // Ajusta según la estructura real de la API
            // for (Hour h : fd.getHour()) { ... }
        }
        return result;
    }
}
