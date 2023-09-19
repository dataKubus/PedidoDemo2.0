package com.example.pedidosdemo20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PrimeraPantallaActivity extends AppCompatActivity
{
    private TextView tvCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_pantalla);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvCuenta = findViewById(R.id.tvCuenta);
        Typeface poppins = Typeface.createFromAsset(getAssets(), "font/Poppins-Regular.ttf");
        tvCuenta.setTypeface(poppins);
    }

    public void onclickIniciarSesion(View view)
    {
        startActivity(new Intent(this, IniciarSesionActivity.class));
        finish();
    }

    public void onclickRegistrase(View view)
    {
        startActivity(new Intent(this, PrimeraPantallaRegistroActivity.class));
        finish();
    }

    @Override
    public void onBackPressed()
    {

    }
}