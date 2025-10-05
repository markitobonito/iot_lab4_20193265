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
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationFragment extends Fragment {

    private EditText editTextSearch;
    private Button btnBuscar;
    private RecyclerView recyclerView;
    private LocationAdapter adapter;

    private final String API_KEY = "5586a0acd5a345e0b4361158250210";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        editTextSearch = view.findViewById(R.id.editTextSearch);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        recyclerView = view.findViewById(R.id.recyclerViewLocation);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LocationAdapter();
        recyclerView.setAdapter(adapter);

        btnBuscar.setOnClickListener(v -> {
            String query = editTextSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                buscarLocaciones(query);
            }
        });

        adapter.setOnItemClickListener(locationId -> {
            // Navegar a ForecasterFragment con el id de la locación
            Bundle bundle = new Bundle();
            bundle.putString("idLocation", locationId);
            ForecasterFragment forecasterFragment = new ForecasterFragment();
            forecasterFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, forecasterFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void buscarLocaciones(String query) {
        WeatherApiService api = RetrofitInstance.getInstance().create(WeatherApiService.class);
        api.searchLocations(API_KEY, query).enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setLocations(response.body());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                // Maneja el error (puedes mostrar un Toast)
            }
        });
    }
}
