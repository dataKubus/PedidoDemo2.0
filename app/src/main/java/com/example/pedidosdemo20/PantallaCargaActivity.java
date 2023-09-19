package com.example.pedidosdemo20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.pedidosdemo20.BasesDatos.PedidosDB;

public class PantallaCargaActivity extends AppCompatActivity
{
    private PedidosDB pedidosDB;

    private final int DURACION_SPLASH = 3000;
    Intent intent;

    private TextView tvEstadoPedido;
    private TextView tvBSMkubus;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_carga);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvEstadoPedido = findViewById(R.id.tvBSMkubus);
        tvBSMkubus = findViewById(R.id.tvBSMkubus);

        Typeface poppins = Typeface.createFromAsset(getAssets(), "font/Poppins-Regular.ttf");
        tvEstadoPedido.setTypeface(poppins);
        tvEstadoPedido.setTypeface(poppins);

        pedidosDB = PedidosDB.getInstance(this);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                String nombre = pedidosDB.emailUser();
                if (!nombre.equals(""))
                {
                    intent = new Intent(PantallaCargaActivity.this , MisPedidosActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    intent = new Intent(PantallaCargaActivity.this , PrimeraPantallaActivity.class);
                    startActivity(intent);
                    finish();
                }

            };
        }, DURACION_SPLASH);


    }
}