package com.example.pedidosdemo20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pedidosdemo20.BasesDatos.Usuario;
import com.example.pedidosdemo20.ReciclerView.MyAdapter;
import com.example.pedidosdemo20.BasesDatos.PedidoFavorito;
import com.example.pedidosdemo20.BasesDatos.PedidosDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetallesPedidoActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigation;

    private int idPedido;
    private String numeroPedido;
    private String numeroAlbaran;
    private String fechaPedido;
    private String fechaSolicitud;

    private LayoutInflater layoutInflater;
    private LinearLayout llDetallesPedido;
    private ConstraintLayout constraintLayout;

    private String nombreGranja = "";
    private String direccion = "";
    private String telefono = "";
    private String correoelectronico = "";
    private String nevera = "";
    private String comentarios = "";
    private String nombreUsuario = "";

    public static final String KEY_ID_PEDIDO = "key_id";

    private String clave = "";
    private String nombre = "pedidos_kubus";
    private String password = "C4lleV@rsov1a.,:";

    private final ArrayList<Integer> alNumero = new ArrayList<>();
    private final ArrayList<String> alFormato = new ArrayList<>();
    private final ArrayList<String> alLinea = new ArrayList<>();
    private final ArrayList<Integer> alHeterospermia = new ArrayList<>();

    private PedidosDB pedidosDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedido);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        llDetallesPedido = findViewById(R.id.llDetallesPedido);
        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        pedidosDB = PedidosDB.getInstance(this);

        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.itMisPedidos:

                    return true;
                case R.id.itNuevoPedido:
                    startActivity(new Intent(getApplicationContext(), NuevoPedidoActivity.class));
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.itNeveras:
                    startActivity(new Intent(getApplicationContext(), NeveraActivity.class));
                    //overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                    finish();
                    return true;
            }
            return false;
        });

        Intent intent = getIntent();
        idPedido = intent.getIntExtra(MisPedidosActivity.KEY_ID, 0);
        numeroPedido = intent.getStringExtra(MisPedidosActivity.KEY_NUM_PEDIDO);
        numeroAlbaran = intent.getStringExtra(MisPedidosActivity.KEY_NUM_ALBARAN);
        fechaPedido = intent.getStringExtra(MisPedidosActivity.KEY_FECHA_PEDIDO);
        fechaSolicitud = intent.getStringExtra(MisPedidosActivity.KEY_FECHA_SOLICITUD);
        //comentarios = intent.getStringExtra(MisPedidosActivity.KEY)

        if (numeroPedido == null && fechaPedido == null && numeroAlbaran == null)
        {
            idPedido = intent.getIntExtra(MyAdapter.KEY_ID, 0);
            numeroPedido = intent.getStringExtra(MyAdapter.KEY_NUM_PEDIDO);
            numeroAlbaran = intent.getStringExtra(MyAdapter.KEY_NUM_ALBARAN);
            fechaPedido = intent.getStringExtra(MyAdapter.KEY_FECHA_PEDIDO);
            fechaSolicitud = intent.getStringExtra(MyAdapter.KEY_FECHA_SOLICITUD);
            comentarios = intent.getStringExtra(MyAdapter.KEY_COMENTARIOS);
            nevera = intent.getStringExtra(MyAdapter.KEY_NEVERA);
        }

        sacarDatosPedidos();
        cargarDetallesPedido();
    }

    private void sacarDatosPedidos()
    {
        nevera = pedidosDB.neveraSeleccionada();
        ArrayList<Usuario> arUser = pedidosDB.getUsuario();
        for (Usuario usuario: arUser)
        {
            nombreGranja = usuario.getNombreGranja();
            direccion = usuario.getDireccion();
            nombreUsuario = usuario.getNombreUsuario();
            telefono = usuario.getTelefono();
            correoelectronico = usuario.getEmail();
            //latitud
            //longitud
        }
    }

    private void cargarDetallesPedido()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final JSONObject jsonObj = new JSONObject();

        try
        {
            jsonObj.put("idPedido", idPedido);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlRecogerPedido = "https://connect.animalsat.com/api/productosDePedidoBeta";

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, urlRecogerPedido, jsonObj, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray = null;

                try
                {
                    jsonArray = response.getJSONArray("response");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JSONObject jsonObjectID;
                int numero;
                String linea;
                String formato;
                int heterospermia;
                int contador = 0;

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    try
                    {
                        jsonObjectID = jsonArray.getJSONObject(i);

                        formato = jsonObjectID.getString("formato");
                        alFormato.add(formato);

                        linea = jsonObjectID.getString("linea");
                        alLinea.add(linea);

                        numero = Integer.parseInt(jsonObjectID.getString("numero"));
                        alNumero.add(numero);

                        heterospermia = Integer.parseInt(jsonObjectID.getString("heterospermia"));
                        alHeterospermia.add(heterospermia);
                        //Habra que poner la herospermia en un campo
                        contador++;
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                    if (contador == jsonArray.length())
                    {
                        rellenarFicha();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d("TAG", "Error = "+ error);
            }
        })
        {
            @Override
            public byte[] getBody()
            {
                try
                {
                    return jsonObj.toString() == null ? null : jsonObj.toString().getBytes("utf-8");
                }
                catch (UnsupportedEncodingException uee)
                {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<>();
                clave = "Basic " + Base64.encodeToString((nombre + ":" + password).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", clave);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(stringRequest);

        Log.d( "js", stringRequest.toString());
    }

    private void rellenarFicha()
    {
        constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.item_detalles_pedido, null);

        TextView tvNumPedido = constraintLayout.findViewById(R.id.tvNumPedido);
        TextView tvFechaPedido = constraintLayout.findViewById(R.id.tvFechaPedido);
        TextView tvNumAlbaran = constraintLayout.findViewById(R.id.tvNumAlbaran);
        TextView tvFechaAlbaran = constraintLayout.findViewById(R.id.tvFechaAlbaran);

        LinearLayout llProductosPedido = constraintLayout.findViewById(R.id.llProductosPedido);
        LinearLayout llEntrega = constraintLayout.findViewById(R.id.llEntrega);
        LinearLayout llContacto = constraintLayout.findViewById(R.id.llContacto);

        tvNumPedido.setText(numeroPedido);
        tvFechaPedido.setText(fechaSolicitud);
        tvNumAlbaran.setText(numeroAlbaran);
        tvFechaAlbaran.setText(fechaPedido);

        int size = alFormato.size();
        for (int i = 0; i < size; i++)
        {
            ConstraintLayout segundoConstraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.item_productos_terminados, null);

            TextView tvNumdosis = segundoConstraintLayout.findViewById(R.id.tvNumdosis);
            TextView tvLineaGenetica = segundoConstraintLayout.findViewById(R.id.tvLineaGenetica);
            TextView tvFormato = segundoConstraintLayout.findViewById(R.id.tvFormato);

            tvNumdosis.setText(alNumero.get(i) + "");
            tvLineaGenetica.setText(alLinea.get(i));
            tvFormato.setText(alFormato.get(i));

            llProductosPedido.addView(segundoConstraintLayout);
        }

        ConstraintLayout entregaConstraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.item_detalle_entrega, null);

        TextView tvNombreGranja = entregaConstraintLayout.findViewById(R.id.tvNombreGranja);
        TextView tvDireccionEntrega = entregaConstraintLayout.findViewById(R.id.tvDireccionEntrega);
        TextView tvLatitud = entregaConstraintLayout.findViewById(R.id.tvLatitud);
        TextView tvLongitud = entregaConstraintLayout.findViewById(R.id.tvLongitud);
        TextView tvNevera = entregaConstraintLayout.findViewById(R.id.tvNevera);
        TextView tvObservaciones = entregaConstraintLayout.findViewById(R.id.tvObservaciones);

        //nevera = pedidosDB.neveraSeleccionada();

        tvNombreGranja.setText(nombreGranja);
        tvDireccionEntrega.setText(direccion);
        tvLatitud.setText("-");
        tvLongitud.setText("-");
        tvNevera.setText(nevera);
        tvObservaciones.setText(comentarios);

        llEntrega.addView(entregaConstraintLayout);

        ConstraintLayout contactoConstraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.item_datos_contacto, null);

        TextView tvNombre1 = contactoConstraintLayout.findViewById(R.id.tvNombre1);
        TextView tvNombre2 = contactoConstraintLayout.findViewById(R.id.tvNombre2);
        TextView tvTelefono1 = contactoConstraintLayout.findViewById(R.id.tvTelefono1);
        TextView tvTelefono2 = contactoConstraintLayout.findViewById(R.id.tvTelefono2);
        TextView tvCorreoElectronico = contactoConstraintLayout.findViewById(R.id.tvCorreoElectronico);
        ImageView ivRepetirPedido = contactoConstraintLayout.findViewById(R.id.ivRepetirPedido);
        ImageView ivFavorito = contactoConstraintLayout.findViewById(R.id.ivFavorito);

        tvNombre1.setText(nombreUsuario);
        tvNombre2.setText("-");
        tvTelefono1.setText(telefono);
        tvTelefono2.setText("-");
        tvCorreoElectronico.setText(correoelectronico);

        int pedidoFavExiste = pedidosDB.strPedidoFav(idPedido);

        boolean favorito = false;
        if (pedidoFavExiste != 0)
        {
            favorito = true;
            ivFavorito.setImageResource(R.drawable.ic_favorito_activo);
        }
        else
        {
            favorito = false;
            ivFavorito.setImageResource(R.drawable.ic_favorito);
        }

        ivRepetirPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //TE MANDARA A LA PANTALLA DE REPETIR PEDIDO
            }
        });

        boolean finalFavorito = favorito;
        ivFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (finalFavorito)
                {
                    ivFavorito.setImageResource(R.drawable.ic_favorito);
                    pedidosDB.elimiarPedidoFavorito(idPedido);
                }
                else
                {
                    ivFavorito.setImageResource(R.drawable.ic_favorito_activo);
                    pedidosDB.insertarPedidoFavorito(new PedidoFavorito(idPedido, fechaSolicitud, "", "", fechaPedido, MyAdapter.pedidoFavorito, "", nevera, comentarios));
                }
            }
        });

        llContacto.addView(contactoConstraintLayout);


        llDetallesPedido.addView(constraintLayout);
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, MisPedidosActivity.class));
        finish();
        //super.onBackPressed();
    }

    public void onClickAtras(View view)
    {
        startActivity(new Intent(this, MisPedidosActivity.class));
        finish();
    }
}