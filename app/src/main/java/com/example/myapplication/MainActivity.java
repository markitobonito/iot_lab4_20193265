package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Validar conexión a Internet
        if (!isInternetAvailable()) {
            showNoInternetDialog();
            return;
        }

        // Nombre de la aplicación
        TextView appName = findViewById(R.id.textAppName);
        appName.setText("Weather IoT App");

        // Imágenes relacionadas al clima
        ImageView img1 = findViewById(R.id.imageView1);
        img1.setImageResource(R.drawable.weather1);
        ImageView img2 = findViewById(R.id.imageView2);
        img2.setImageResource(R.drawable.weather2);

        // Botón 'Ingresar'
        Button btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AppActivity.class);
            startActivity(intent);
            finish();
        });

        // Texto de autor
        TextView autor = findViewById(R.id.textAutor);
        autor.setText("Elaborado por: Nassim hamed ramirez 20193265");
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
            .setTitle("Sin conexión")
            .setMessage("No hay conexión a Internet. ¿Desea ir a Configuración?")
            .setCancelable(false)
            .setPositiveButton("Configuración", (dialog, which) -> {
                startActivity(new Intent(Settings.ACTION_SETTINGS));
            })
            .setNegativeButton("Cancelar", (dialog, which) -> finish())
            .show();
    }
}