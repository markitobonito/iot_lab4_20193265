package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
            .findFragmentById(R.id.fragment_container);
        NavController navController = navHostFragment.getNavController();

        Button btnLocation = findViewById(R.id.btnLocation);
        Button btnForecaster = findViewById(R.id.btnForecaster);
        Button btnDate = findViewById(R.id.btnDate);

        btnLocation.setOnClickListener(v -> {
            navController.navigate(R.id.locationFragment);
        });
        btnForecaster.setOnClickListener(v -> {
            navController.navigate(R.id.forecasterFragment);
        });
        btnDate.setOnClickListener(v -> {
            navController.navigate(R.id.dateFragment);
        });
    }

    @Override
    public void onBackPressed() {
        // Regresar a MainActivity y limpiar el backstack
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
