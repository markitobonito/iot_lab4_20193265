package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    // Buscar locaciones
    @GET("search.json")
    Call<List<Location>> searchLocations(
        @Query("key") String apiKey,
        @Query("q") String query
    );

    // Pronóstico (hasta 14 días)
    @GET("forecast.json")
    Call<ForecastResponse> getForecast(
        @Query("key") String apiKey,
        @Query("q") String idLocation,
        @Query("days") int days
    );

    // Pronóstico futuro (más de 14 días)
    @GET("future.json")
    Call<FutureResponse> getFuture(
        @Query("key") String apiKey,
        @Query("q") String idLocation,
        @Query("dt") String date
    );

    // Pronóstico histórico
    @GET("history.json")
    Call<HistoryResponse> getHistory(
        @Query("key") String apiKey,
        @Query("q") String idLocation,
        @Query("dt") String date
    );
}
