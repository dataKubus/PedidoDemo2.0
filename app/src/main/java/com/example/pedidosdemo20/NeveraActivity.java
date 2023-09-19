package com.example.pedidosdemo20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NeveraActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nevera);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.itNeveras);

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.itMisPedidos:
                    startActivity(new Intent(getApplicationContext(), MisPedidosActivity.class));
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.itNuevoPedido:
                    startActivity(new Intent(getApplicationContext(), NuevoPedidoActivity.class));
                    //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
                case R.id.itNeveras:

                    return true;
            }
            return false;
        });
    }
}