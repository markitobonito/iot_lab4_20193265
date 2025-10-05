package com.example.myapplication;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecasterFragment extends Fragment implements SensorEventListener {

    private EditText editTextIdLocation, editTextDays;
    private Button btnBuscar;
    private RecyclerView recyclerView;
    private ForecasterAdapter adapter;
    private final String API_KEY = "5586a0acd5a345e0b4361158250210";

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isShakeDialogShown = false;
    private final float SHAKE_THRESHOLD = 2.0f; // m/s²

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecaster, container, false);

        editTextIdLocation = view.findViewById(R.id.editTextIdLocation);
        editTextDays = view.findViewById(R.id.editTextDays);
        btnBuscar = view.findViewById(R.id.btnBuscarForecaster);
        recyclerView = view.findViewById(R.id.recyclerViewForecaster);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ForecasterAdapter();
        recyclerView.setAdapter(adapter);

        // Recibe argumento idLocation si viene de LocationFragment
        Bundle args = getArguments();
        if (args != null && args.containsKey("idLocation")) {
            editTextIdLocation.setText(args.getString("idLocation", ""));
        }

        btnBuscar.setOnClickListener(v -> {
            String idLocation = editTextIdLocation.getText().toString().trim();
            String daysStr = editTextDays.getText().toString().trim();
            int days = daysStr.isEmpty() ? 1 : Integer.parseInt(daysStr);
            if (!idLocation.isEmpty()) {
                buscarPronosticos(idLocation, days);
            }
        });

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);

            if (acceleration > SHAKE_THRESHOLD && !isShakeDialogShown) {
                isShakeDialogShown = true;
                showDeleteDialog();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No se requiere para este caso
    }

    private void buscarPronosticos(String idLocation, int days) {
        WeatherApiService api = RetrofitInstance.getInstance().create(WeatherApiService.class);
        api.getForecast(API_KEY, "id:" + idLocation, days).enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Forecast> forecasts = new ArrayList<>();
                    for (ForecastResponse.ForecastDay fd : response.body().getForecast().getForecastday()) {
                        Forecast f = new Forecast();
                        f.setDate(fd.getDate());
                        f.setMaxTemp(String.valueOf(fd.getDay().getMaxtemp_c()));
                        f.setMinTemp(String.valueOf(fd.getDay().getMintemp_c()));
                        f.setCondition(fd.getDay().getCondition().getText());
                        forecasts.add(f);
                    }
                    adapter.setForecasts(forecasts);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                // Maneja el error (puedes mostrar un Toast)
            }
        });
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(requireContext())
            .setTitle("Eliminar últimos pronósticos")
            .setMessage("¿Desea eliminar los últimos pronósticos obtenidos?")
            .setPositiveButton("Sí", (dialog, which) -> {
                // Elimina el último elemento de la lista y actualiza el RecyclerView
                if (adapter != null && adapter.getItemCount() > 0) {
                    adapter.removeLastItem();
                    adapter.notifyDataSetChanged();
                }
                isShakeDialogShown = false;
            })
            .setNegativeButton("No", (dialog, which) -> {
                isShakeDialogShown = false;
            })
            .setCancelable(false)
            .show();
    }
}
