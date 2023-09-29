package com.example.pedidosdemo20;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.pedidosdemo20.BasesDatos.PedidoFavorito;
import com.example.pedidosdemo20.BasesDatos.Usuario;
import com.example.pedidosdemo20.Fragment.PedidosEnCursoFragment;
import com.example.pedidosdemo20.Fragment.PedidosTerminadosFragment;
import com.example.pedidosdemo20.BasesDatos.PedidosDB;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MisPedidosActivity extends AppCompatActivity
{
    private BottomNavigationView bottomNavigation;
    private LinearLayout llPerfil;

    private ImageView ivfotoPerfil;
    private String selectedImagePath;
    private SharedPreferences sharedPreferences;
    private static final String IMAGE_PATH_KEY = "imagePath";
    private static final String PREFS_NAME = "MyPrefsFile";
    private boolean buscarFoto = false;

    private ConstraintLayout constraintLayout;
    private boolean filtroPuesto = false;
    private boolean filtroFechaPuesto = false;

    private String estadoREg = "registrado";
    private String estadoAct = "en curso";
    private String estadoREp = "en reparto";

    private LayoutInflater layoutInflater;

    private ImageView ivIconoEstado;
    private TextView tvSinPedidos;
    private ImageView ivNuevoPedido;

    private ImageView ivPEdidosEntregados;
    private ImageView ivPedidosFav;
    private ImageView ivPEdidosFech;
    private ImageView ivPedidosLin;
    private ImageView ivPedidosFor;

    public static ArrayList<Integer> alId = new ArrayList<>();
    public static ArrayList<String> alNumPedidoBSM = new ArrayList<>();
    public static ArrayList<String> alCia = new ArrayList<>();
    public static ArrayList<String> alCliente = new ArrayList<>();
    public static ArrayList<String> alEmail = new ArrayList<>();
    public static ArrayList<String> alFechaSolicitud = new ArrayList<>();
    public static ArrayList<String> alFechaEntrega = new ArrayList<>();
    public static ArrayList<String> alEstado = new ArrayList<>();
    public static ArrayList<String> alNevera = new ArrayList<>();
    public static ArrayList<String> alComentarios = new ArrayList<>();

    public static ArrayList<Integer> alIdProducto = new ArrayList<>();
    public static ArrayList<Integer> alIdPedido = new ArrayList<>();
    public static ArrayList<Integer> alNumero = new ArrayList<>();
    public static ArrayList<String> alFormato = new ArrayList<>();
    public static ArrayList<String> alLinea = new ArrayList<>();

    private String clave = "";
    private String nombre = "pedidos_kubus";
    private String password = "C4lleV@rsov1a.,:";

    private PedidosDB pedidosDB;

    public static final String KEY_ID = "key_id";
    public static final String KEY_NUM_PEDIDO = "key_id_pedido";
    public static final String KEY_NUM_ALBARAN = "key_num_albaran";
    public static final String KEY_FECHA_PEDIDO = "key_fecha_pedido";
    public static final String KEY_FECHA_ENTREGA = "key_fecha_entrega";
    public static final String KEY_FECHA_SOLICITUD = "key_fecha_solicitud";

    public int pedidosEnCurso = 5;
    public int pedidosTerminados = 10;
    public int fragmentSeleccionado = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_pedidos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        llPerfil = findViewById(R.id.llPerfil);

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setSelectedItemId(R.id.itMisPedidos);

        ivIconoEstado = findViewById(R.id.ivIconoEstado);
        tvSinPedidos = findViewById(R.id.tvSinPedidos);
        ivNuevoPedido = findViewById(R.id.ivNuevoPedido);

        pedidosDB = PedidosDB.getInstance(this);

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        establecerPedidosEnCurso();
        establecerPestanaPerfil();

        if (!validarPermisos())
        {
            ivfotoPerfil.setEnabled(false);
        }

        /*sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        selectedImagePath = sharedPreferences.getString(IMAGE_PATH_KEY, null);
        if (selectedImagePath != null)
        {
            Bitmap bitmap = getBitmapFromPath(selectedImagePath);
            if (bitmap != null)
            {
                ivfotoPerfil.setImageBitmap(bitmap);
            }
        }*/
    }

    private void establecerPedidosEnCurso()
    {
        alId.clear();
        alNumPedidoBSM.clear();
        alCia.clear();
        alCliente.clear();
        alEmail.clear();
        alFechaSolicitud.clear();
        alFechaEntrega.clear();
        alEstado.clear();
        alIdProducto.clear();
        alIdPedido.clear();
        alNumero.clear();
        alFormato.clear();
        alLinea.clear();
        alEstado.clear();
        alNevera.clear();
        alComentarios.clear();

        RequestQueue queue = Volley.newRequestQueue(this);

        //svPedidosEnCurso.setVisibility(View.INVISIBLE);

        final JSONObject jsonObj = new JSONObject();
        //String rega = pedidosDB.rega();
        try
        {
            jsonObj.put("estado", "registrado");
            jsonObj.put("estado2", "en curso");
            jsonObj.put("estado3", "en reparto");
            jsonObj.put("email", "data@kubus.es");
            //jsonObj.put("email", email);
            jsonObj.put("rega", "ES001");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlRecogerPedido = "https://connect.animalsat.com/api/pedidosDeUserBeta2";

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
                JSONObject jsonObject;
                int id;
                String cia;
                String numPedido;
                String cliente;
                String fechaSolicitud;
                String fechaEntrega;
                String estado;
                String nevera;
                String comenatrios;
                int contador = 0;

                int idPro;
                int idPedido;
                int numero;
                String formato;
                String linea;

                if (jsonArray != null)
                {
                    tvSinPedidos.setVisibility(View.INVISIBLE);
                    ivIconoEstado.setVisibility(View.INVISIBLE);
                    ivNuevoPedido.setVisibility(View.INVISIBLE);

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        try
                        {
                            jsonObjectID = jsonArray.getJSONObject(i);

                            id = Integer.parseInt(jsonObjectID.getString("id"));
                            alId.add(id);

                            numPedido = jsonObjectID.getString("numPedidoBSM");
                            alNumPedidoBSM.add(numPedido);

                            cia = jsonObjectID.getString("cia");
                            alCia.add(cia);

                            cliente = jsonObjectID.getString("cliente");
                            alCliente.add(cliente);

                            fechaSolicitud = jsonObjectID.getString("fechaSolicitud");
                            fechaSolicitud = fechaSolicitud.substring(0, 4) + "/" + fechaSolicitud.substring(4, 6) + "/" + fechaSolicitud.substring(6, 8);
                            alFechaSolicitud.add(fechaSolicitud);

                            fechaEntrega = jsonObjectID.getString("fechaEntrega");
                            fechaEntrega = fechaEntrega.substring(0, 4) + "/" + fechaEntrega.substring(4, 6) + "/" + fechaEntrega.substring(6, 8);
                            alFechaEntrega.add(fechaEntrega);

                            estado = jsonObjectID.getString("estado");
                            alEstado.add(estado);

                            nevera = jsonObjectID.getString("nevera");
                            alNevera.add(nevera);

                            comenatrios = jsonObjectID.getString("comentarios");
                            alComentarios.add(comenatrios);

                            JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("Productos");
                            for (int j = 0; j < jsonArray1.length(); j++)
                            {
                                jsonObject = jsonArray1.getJSONObject(j);

                                idPro = Integer.parseInt(jsonObject.getString("id"));
                                alIdProducto.add(idPro);

                                idPedido = Integer.parseInt(jsonObject.getString("idPedido"));
                                alIdPedido.add(idPedido);

                                numero = Integer.parseInt(jsonObject.getString("numero"));
                                alNumero.add(numero);

                                formato = jsonObject.getString("formato");
                                alFormato.add(formato);

                                linea = jsonObject.getString("linea");
                                alLinea.add(linea);
                            }
                            contador++;
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        if (contador == jsonArray.length())
                        {
                            PedidosEnCursoFragment fragment1 = new PedidosEnCursoFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, fragment1)
                                    .commit();
                        }
                    }
                }
                else {
                    tvSinPedidos.setVisibility(View.VISIBLE);
                    ivIconoEstado.setVisibility(View.VISIBLE);
                    ivNuevoPedido.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*View view = new View(MainActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                view = getLayoutInflater().inflate(R.layout.item_sin_conexion, null);
                builder.setView(view);

                TextView tvOk = view.findViewById(R.id.tvOk);

                final AlertDialog alertDialog = builder.create();

                tvOk.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
                Log.d("TAG", "Error = "+ error);*/
            }
        })
        {

            @Override
            public byte[] getBody() {
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
    }

    /*private void dibujarPedidos()
    {
        int size = alId.size();

        if (size == 0)
        {

        }
        else
        {
            //tvSinPedidos.setVisibility(View.INVISIBLE);
            for (int i = 0; i < size; i++)
            {
                ConstraintLayout constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.item_pedido_en_progreso, null);

                TextView tvEstadoPedido = constraintLayout.findViewById(R.id.tvEstadoPedido);
                ImageView ivIconoEstado = constraintLayout.findViewById(R.id.ivIconoEstado);
                LinearLayout llProductos = constraintLayout.findViewById(R.id.llProductos);
                TextView tvFecha = constraintLayout.findViewById(R.id.tvFecha);
                TextView tvFechaPedido = constraintLayout.findViewById(R.id.tvFechaPedido);
                ImageView ivVerPedido = constraintLayout.findViewById(R.id.ivVerPedido);
                ImageView ivRepetirPedido = constraintLayout.findViewById(R.id.ivRepetirPedido);
                ImageView ivFavorito = constraintLayout.findViewById(R.id.ivFavorito);

                String estado = alEstado.get(i);
                String url = "";
                if (estado.equals(estadoAct))
                {
                    url = "https://www.kubus-sa.com/wp-content/uploads/2023/06/Confirmado.gif";
                    tvEstadoPedido.setBackgroundColor(getResources().getColor(R.color.naranja));
                    tvEstadoPedido.setText(R.string.pedidoConfirmado);
                }
                else if (estado.equals(estadoREg))
                {
                    url = "https://www.kubus-sa.com/wp-content/uploads/2023/06/Espera.gif";
                    tvEstadoPedido.setBackgroundColor(getResources().getColor(R.color.rojo));
                    tvEstadoPedido.setText(R.string.pedidoEnEspera);
                }
                else
                {
                    url = "https://www.kubus-sa.com/wp-content/uploads/2023/06/En-reparto_1.gif";
                    tvEstadoPedido.setBackgroundColor(getResources().getColor(R.color.azul));
                    tvEstadoPedido.setText(R.string.pedidoEnReparto);
                }

                final int finalI = i;
                ivVerPedido.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        //int idPedido = alId.get(finalI);
                        Intent intent = new Intent(MisPedidosActivity.this, DetallesPedidoActivity.class);
                        intent.putExtra(KEY_ID, alId.get(finalI));
                        intent.putExtra(KEY_NUM_PEDIDO, alNumPedidoBSM.get(finalI));
                        intent.putExtra(KEY_NUM_ALBARAN, "-");
                        intent.putExtra(KEY_FECHA_PEDIDO, alFechaEntrega.get(finalI));
                        intent.putExtra(KEY_FECHA_ENTREGA, alFechaEntrega.get(finalI));
                        intent.putExtra(KEY_FECHA_SOLICITUD, alFechaSolicitud.get(finalI));
                        startActivity(intent);
                        finish();
                    }
                });

                Uri uri = Uri.parse(url);
                Glide.with(getApplicationContext()).load(uri).into(ivIconoEstado);

                tvFecha.setText(R.string.fechaPrevistaEntrega);
                tvFechaPedido.setText(alFechaEntrega.get(i));

                for (int j = 0; j < alIdProducto.size(); j++)
                {
                    ConstraintLayout constraintLayout1 = (ConstraintLayout) layoutInflater.inflate(R.layout.item_productos, null);

                    TextView tvNumdosis = constraintLayout1.findViewById(R.id.tvNumdosis);
                    TextView tvLineaGenetica = constraintLayout1.findViewById(R.id.tvLineaGenetica);
                    TextView tvFormato = constraintLayout1.findViewById(R.id.tvFormato);

                    int idPed = alIdPedido.get(j);
                    int id = alId.get(i);

                    if (idPed == id)
                    {
                        tvNumdosis.setText(alNumero.get(j) + "");
                        tvLineaGenetica.setText(alLinea.get(j));
                        tvFormato.setText(alFormato.get(j));

                        llProductos.addView(constraintLayout1);
                    }
                }
                llPedidosEnCurso.addView(constraintLayout);
            }
        }
    }*/

    private void establecerPestanaPerfil()
    {
        constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.item_ficha_personal, null);

        ivfotoPerfil = constraintLayout.findViewById(R.id.ivfotoPerfil);
        TextView tvNombreGranja = constraintLayout.findViewById(R.id.tvNombreGranja);
        TextView tvFecha = constraintLayout.findViewById(R.id.tvFecha);
        ImageView ivAjustes = constraintLayout.findViewById(R.id.ivAjustes);
        TextView tvNombreUsuario = constraintLayout.findViewById(R.id.tvNombreUsuario);
        ImageView ivPEdidosEncurso = constraintLayout.findViewById(R.id.ivPEdidosEncurso);
        ivPEdidosEntregados = constraintLayout.findViewById(R.id.ivPEdidosEntregados);

        String fechaHoy = sacarFechaHoy();
        tvFecha.setText(fechaHoy);
        ArrayList<Usuario> arUser = pedidosDB.getUsuario();
        String urlfoto = "";

        for (Usuario usuario: arUser)
        {
            tvNombreGranja.setText(usuario.getNombreGranja());
            tvNombreUsuario.setText("¡" + getString(R.string.hola) + " " + usuario.getEmail() + "!");//poner nombre cuando este guardado en la base de datos
            urlfoto = usuario.getUrlFotoPerfil();
        }

        if (!urlfoto.equals(""))
        {
            try
            {
                Uri uri = Uri.parse(urlfoto);
                ivfotoPerfil.setImageURI(uri);
            }
            catch (Exception e)
            {
                ivfotoPerfil.setImageResource(R.drawable.ic_perfil);
            }
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                if (bitmap != null)
                {
                    ivfotoPerfil.setImageBitmap(bitmap);
                    //pedidosDB.actualizarfotoPerfil(selectedImagePath);
                }*/
        }

        ivfotoPerfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                cargarImagen();
            }
        });

        //ivPEdidosEncurso.setEnabled(false);

        ivPEdidosEncurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ivPEdidosEntregados.setEnabled(true);
                ivPEdidosEncurso.setEnabled(false);

                alId.clear();
                alNumPedidoBSM.clear();
                alCia.clear();
                alCliente.clear();
                alFechaSolicitud.clear();
                alFechaEntrega.clear();
                alEstado.clear();
                alNevera.clear();
                alComentarios.clear();
                alIdProducto.clear();
                alIdPedido.clear();
                alNumero.clear();
                alFormato.clear();
                alLinea.clear();

                ivPEdidosEntregados.setImageResource(R.drawable.ic_bot_n_entregados_sin_activar);
                ivPEdidosEncurso.setImageResource(R.drawable.ic_pedidos_en_curso_activados);
                ConstraintLayout segundoConstraintLayout = (ConstraintLayout) llPerfil.getChildAt(1);
                ConstraintLayout teceroConstraintLayout = (ConstraintLayout) llPerfil.getChildAt(2);
                if (filtroPuesto)
                {
                    llPerfil.removeView(segundoConstraintLayout);
                    llPerfil.removeView(teceroConstraintLayout);
                    filtroPuesto = false;
                    filtroFechaPuesto = false;
                    establecerPedidosEnCurso();
                }
                fragmentSeleccionado = pedidosEnCurso;
                PedidosEnCursoFragment fragment1 = new PedidosEnCursoFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment1)
                        .commit();
            }
        });

        ivPEdidosEntregados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ivPEdidosEntregados.setEnabled(false);
                ivPEdidosEncurso.setEnabled(true);

                //llPedidosEnCurso.removeAllViews();//ESTABLECER PEDIDOS ENTREGADOS

                alId.clear();
                alNumPedidoBSM.clear();
                alCia.clear();
                alCliente.clear();
                alFechaSolicitud.clear();
                alFechaEntrega.clear();
                alEstado.clear();
                alNevera.clear();
                alComentarios.clear();
                alIdProducto.clear();
                alIdPedido.clear();
                alNumero.clear();
                alFormato.clear();
                alLinea.clear();

                tvSinPedidos.setVisibility(View.INVISIBLE);
                ivIconoEstado.setVisibility(View.INVISIBLE);
                ivNuevoPedido.setVisibility(View.INVISIBLE);
                ivPEdidosEntregados.setImageResource(R.drawable.ic_pedidos_entregados);
                ivPEdidosEncurso.setImageResource(R.drawable.ic_pedidos_en_curso);
                if (!filtroPuesto)
                {
                    establecerFiltrado();
                    filtroPuesto = true;
                }
                String fechaHoy = fechaActualNumerico();
                String fechaInicio = "2022/01/01";
                establecerPedidosFinalizados(fechaInicio, fechaHoy);
                fragmentSeleccionado = pedidosTerminados;

                ivPedidosFav.setImageResource(R.drawable.ic_favorito_inactive);
                ivPEdidosFech.setImageResource(R.drawable.ic_fecha_inactive);
                ivPedidosLin.setImageResource(R.drawable.ic_linea_genetica_in_active);
                ivPedidosFor.setImageResource(R.drawable.ic_formato_inactive);
                if (filtroFechaPuesto)
                {
                    ConstraintLayout teceroConstraintLayout = (ConstraintLayout) llPerfil.getChildAt(2);
                    llPerfil.removeView(teceroConstraintLayout);
                    filtroFechaPuesto = false;
                 }

                PedidosTerminadosFragment fragment1 = new PedidosTerminadosFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment1)
                        .commit();
            }
        });

        llPerfil.addView(constraintLayout);
    }

    private String sacarFechaHoy()
    {
        Calendar calendar = Calendar.getInstance();
        int diaNumerico = calendar.get(Calendar.DAY_OF_MONTH);
        int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        int mesnumerico = calendar.get(Calendar.MONTH);

        String strDia = diaDeLaSemana(diaSemana);
        String strMes = diaDelMes(mesnumerico);

        String fechaActual = strDia + ", " + diaNumerico + " " + getString(R.string.de) + " " + strMes;
        return fechaActual;
    }

    private String diaDelMes(int mesnumerico)
    {
        String mes = "";
        switch (mesnumerico)
        {
            case 0:
                mes = getString(R.string.enero);
                break;
            case 1:
                mes = getString(R.string.febrero);
                break;
            case 2:
                mes = getString(R.string.marzo);
                break;
            case 3:
                mes = getString(R.string.abril);
                break;
            case 4:
                mes = getString(R.string.mayo);
                break;
            case 5:
                mes = getString(R.string.junio);
                break;
            case 6:
                mes = getString(R.string.julio);
                break;
            case 7:
                mes = getString(R.string.agosto);
                break;
            case 8:
                mes = getString(R.string.septiembre);
                break;
            case 9:
                mes = getString(R.string.octubre);
                break;
            case 10:
                mes = getString(R.string.noviembre);
                break;
            case 11:
                mes = getString(R.string.diciembre);
                break;
        }
        return mes;
    }

    private String diaDeLaSemana(int diaSemana)
    {
        String day = "";
        switch (diaSemana)
        {
            case 2:
                day = getString(R.string.lunes);
                break;
            case 3:
                day = getString(R.string.martes);
                break;
            case 4:
                day = getString(R.string.miercoles);
                break;
            case 5:
                day = getString(R.string.jueves);
                break;
            case 6:
                day = getString(R.string.viernes);
                break;
            case 7:
                day = getString(R.string.sabado);
                break;
            case 8:
                day = getString(R.string.domingo);
                break;
        }
        return day;
    }

    private void establecerPedidosFinalizados(String fechaHace7Dias, String fechaHoy)//Webservice para hacer pedidos entregados
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        alId.clear();
        alNumPedidoBSM.clear();
        alCia.clear();
        alCliente.clear();
        alEmail.clear();
        alFechaSolicitud.clear();
        alFechaEntrega.clear();
        alEstado.clear();
        alNevera.clear();
        alComentarios.clear();
        alIdProducto.clear();
        alIdPedido.clear();
        alNumero.clear();
        alFormato.clear();
        alLinea.clear();

        final JSONObject jsonObj = new JSONObject();
        //String rega = pedidosDB.rega();
        try
        {
            jsonObj.put("estado", "terminado");
            jsonObj.put("email", "data@kubus.es");
            jsonObj.put("rega", "ES001");
            jsonObj.put("fechaInicio", fechaHace7Dias);
            jsonObj.put("fechaFin", fechaHoy);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlRecogerPedido = "https://connect.animalsat.com/api/historicoPedidosBeta";

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
                int id;
                String numPedido;
                String cia;
                String cliente;
                String fechaSolicitud;
                String fechaEntrega;
                String nevera;
                String comentarios;
                int contador = 0;

                if (jsonArray.length() == 0)
                {
                    PedidosTerminadosFragment fragment1 = new PedidosTerminadosFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment1)
                            .commit();
                }
                else
                {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        try
                        {
                            jsonObjectID = jsonArray.getJSONObject(i);

                            id = Integer.parseInt(jsonObjectID.getString("id"));
                            alId.add(id);

                            numPedido = jsonObjectID.getString("numPedidoBSM");
                            alNumPedidoBSM.add(numPedido);

                            cia = jsonObjectID.getString("cia");
                            alCia.add(cia);

                            cliente = jsonObjectID.getString("cliente");
                            alCliente.add(cliente);

                            fechaSolicitud = jsonObjectID.getString("fechaSolicitud");
                            fechaSolicitud = fechaSolicitud.substring(0, 4) + "/" + fechaSolicitud.substring(4, 6) + "/" + fechaSolicitud.substring(6, 8);
                            alFechaSolicitud.add(fechaSolicitud);

                            fechaEntrega = jsonObjectID.getString("fechaEntrega");
                            fechaEntrega = fechaEntrega.substring(0, 4) + "/" + fechaEntrega.substring(4, 6) + "/" + fechaEntrega.substring(6, 8);
                            alFechaEntrega.add(fechaEntrega);

                            nevera = jsonObjectID.getString("nevera");
                            alNevera.add(nevera);

                            comentarios = jsonObjectID.getString("comentarios");
                            alComentarios.add(comentarios);

                            contador++;

                            //cambiar webService y que devuelva productos
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        if (contador == jsonArray.length())
                        {
                            PedidosTerminadosFragment fragment1 = new PedidosTerminadosFragment();
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.fragment_container, fragment1)
                                    .commit();
                            //dibujarPedidosEntregados();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*View view = new View(MainActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                view = getLayoutInflater().inflate(R.layout.item_sin_conexion, null);
                builder.setView(view);

                TextView tvOk = view.findViewById(R.id.tvOk);

                final AlertDialog alertDialog = builder.create();

                tvOk.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
                Log.d("TAG", "Error = "+ error);*/
            }
        })
        {

            @Override
            public byte[] getBody() {
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

    /*private void dibujarPedidosEntregados()
    {
        int size = alId.size();

        if (size == 0)
        {

        }
        else
        {
            rwPedidosEntregados.setHasFixedSize(true);
            rwPedidosEntregados.setLayoutManager(new LinearLayoutManager(this));
            List<MyList> arList = new ArrayList();
            for (int i = 0; i < size; i++)
            {
                List<String> listaProdutco = Arrays.asList("", "alLinea.get(j)", "alFormato.get(j)");
                int pedFav = pedidosDB.strPedidoFav(alId.get(i));
                if (pedFav != 0)
                {
                    arList.add(new MyList(alId.get(i), alFechaSolicitud.get(i), alNumPedidoBSM.get(i), "A000016", alFechaEntrega.get(i), MyAdapter.pedidoFavorito, listaProdutco));
                }
                else
                {
                    arList.add(new MyList(alId.get(i), alFechaSolicitud.get(i), alNumPedidoBSM.get(i), "A000016", alFechaEntrega.get(i), MyAdapter.pedidoNormal, listaProdutco));
                }

                //arList.add(new MyList(alNumPedidoBSM.get(i), "A000016", alFechaEntrega.get(i), alNumero.get(i) + "", alFormato.get(i), alLinea.get(i)));
                //List<String> listaProdutco = Arrays.asList("Elemento 1", "Elemento 2", "Elemento 3");
                /*for (int j = 0; j < alIdProducto.size(); j++)
                {
                    ConstraintLayout constraintLayout1 = (ConstraintLayout) layoutInflater.inflate(R.layout.item_productos, null);

                    TextView tvNumdosis = constraintLayout1.findViewById(R.id.tvNumdosis);
                    TextView tvLineaGenetica = constraintLayout1.findViewById(R.id.tvLineaGenetica);
                    TextView tvFormato = constraintLayout1.findViewById(R.id.tvFormato);

                    int idPed = alIdPedido.get(j);
                    int id = alId.get(i);

                    if (idPed == id)
                    {
                        tvNumdosis.setText(alNumero.get(j) + "");
                        tvLineaGenetica.setText(alLinea.get(j));
                        tvFormato.setText(alFormato.get(j));

                    }
                }
                MyAdapter adpater = new MyAdapter(this, arList);
                rwPedidosEntregados.setAdapter(adpater);
            }

        }
    }*/

    private void cargarImagen()
    {
        /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivity(intent.createChooser(intent,"Seleccione app"));*/

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            Uri image = data.getData();
            ivfotoPerfil.setImageURI(image);
            pedidosDB.actualizarfotoPerfil(image.toString());
            try
            {
                /*Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                if (bitmap != null)
                {
                    ivfotoPerfil.setImageURI(image);
                    pedidosDB.actualizarfotoPerfil(image + "");
                    //saveImagePath(selectedImagePath);
                }*/
            }
            catch (OutOfMemoryError e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromPath(String imagePath)
    {
        try
        {
            Uri imageUri = Uri.parse(imagePath);
            return MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (SecurityException e)
        {

        }
        return null;
    }

    private void saveImagePath(String imagePath)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGE_PATH_KEY, imagePath);
        editor.apply();
    }

    private void establecerFiltrado()
    {
        constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.item_filtrado_pedidos, null);

        ivPedidosFav = constraintLayout.findViewById(R.id.ivPedidosFav);
        ivPEdidosFech = constraintLayout.findViewById(R.id.ivPEdidosFech);
        ivPedidosLin = constraintLayout.findViewById(R.id.ivPedidosLin);
        ivPedidosFor = constraintLayout.findViewById(R.id.ivPedidosFor);

        ivPedidosFav.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ivPedidosFav.setImageResource(R.drawable.ic_favorito_active);

                ivPEdidosFech.setImageResource(R.drawable.ic_fecha_inactive);
                ivPedidosLin.setImageResource(R.drawable.ic_linea_genetica_in_active);
                ivPedidosFor.setImageResource(R.drawable.ic_formato_inactive);

                ConstraintLayout teceroConstraintLayout = (ConstraintLayout) llPerfil.getChildAt(2);
                llPerfil.removeView(teceroConstraintLayout);
                //sacarPedidos()
                filtroFechaPuesto = false;
                mostrarPedidosFavoritos();
            }
        });

        ivPEdidosFech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ivPedidosFav.setImageResource(R.drawable.ic_favorito_inactive);
                ivPedidosLin.setImageResource(R.drawable.ic_linea_genetica_in_active);
                ivPedidosFor.setImageResource(R.drawable.ic_formato_inactive);

                if (!filtroFechaPuesto)
                {
                    filtroFechaPuesto = true;
                    ivPEdidosFech.setImageResource(R.drawable.ic_fecha_active);
                    constraintLayout = (ConstraintLayout) layoutInflater.inflate(R.layout.item_filtrado_fecha, null);

                    ImageView ivUltimos7dias = constraintLayout.findViewById(R.id.ivUltimos7dias);
                    ImageView ivUltimos30Dias = constraintLayout.findViewById(R.id.ivUltimos30Dias);
                    ImageView ivUltimos90dias = constraintLayout.findViewById(R.id.ivUltimos90dias);
                    ImageView ivMes = constraintLayout.findViewById(R.id.ivMes);

                    ivUltimos7dias.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            ivUltimos7dias.setImageResource(R.drawable.ic_ultimos_7_dias_active);
                            ivUltimos30Dias.setImageResource(R.drawable.ic__utimos_30_dias);
                            ivUltimos90dias.setImageResource(R.drawable.ic_ultimos_90_dias);
                            ivMes.setImageResource(R.drawable.ic_mes);
                            String fechaHoy = fechaActualNumerico();
                            String fechaHace7Dias = restarDias(7);

                            establecerPedidosFinalizados(fechaHace7Dias, fechaHoy);
                        }
                    });

                    ivUltimos30Dias.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            ivUltimos7dias.setImageResource(R.drawable.ic_ultimos_7_dias_cative);
                            ivUltimos30Dias.setImageResource(R.drawable.ic_ultimos_30_dias_activo);
                            ivUltimos90dias.setImageResource(R.drawable.ic_ultimos_90_dias);
                            ivMes.setImageResource(R.drawable.ic_mes);

                            String fechaHoy = fechaActualNumerico();
                            String fechaUltimos30dias = restarDias(30);

                            establecerPedidosFinalizados(fechaUltimos30dias, fechaHoy);
                        }
                    });

                    ivUltimos90dias.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            ivUltimos7dias.setImageResource(R.drawable.ic_ultimos_7_dias_cative);
                            ivUltimos30Dias.setImageResource(R.drawable.ic__utimos_30_dias);
                            ivUltimos90dias.setImageResource(R.drawable.ic_ultimos_90_dias_activo);
                            ivMes.setImageResource(R.drawable.ic_mes);

                            String fechaHoy = fechaActualNumerico();
                            String fechaUltimos90dias = restarDias(90);
                            establecerPedidosFinalizados(fechaUltimos90dias, fechaHoy);
                        }
                    });

                    ivMes.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            ivUltimos7dias.setImageResource(R.drawable.ic_ultimos_7_dias_cative);
                            ivUltimos30Dias.setImageResource(R.drawable.ic__utimos_30_dias);
                            ivUltimos90dias.setImageResource(R.drawable.ic_ultimos_90_dias);
                            ivMes.setImageResource(R.drawable.ic_mes_activo);


                            //establecerPedidosFinalizados(fechaHace7Dias, fechaHoy);
                        }
                    });

                    llPerfil.addView(constraintLayout);
                }
                else {
                    ConstraintLayout teceroConstraintLayout = (ConstraintLayout) llPerfil.getChildAt(2);
                    llPerfil.removeView(teceroConstraintLayout);
                    filtroFechaPuesto = false;
                }
            }
        });

        ivPedidosLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivPedidosLin.setImageResource(R.drawable.ic_linea_genetica_active);

                ivPedidosFav.setImageResource(R.drawable.ic_favorito_inactive);
                ivPEdidosFech.setImageResource(R.drawable.ic_fecha_inactive);
                ivPedidosFor.setImageResource(R.drawable.ic_formato_inactive);

                ConstraintLayout teceroConstraintLayout = (ConstraintLayout) llPerfil.getChildAt(2);
                llPerfil.removeView(teceroConstraintLayout);
                filtroFechaPuesto = false;
            }
        });

        ivPedidosFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ivPedidosFor.setImageResource(R.drawable.ic_formato_active);

                ivPedidosFav.setImageResource(R.drawable.ic_favorito_inactive);
                ivPEdidosFech.setImageResource(R.drawable.ic_fecha_inactive);
                ivPedidosLin.setImageResource(R.drawable.ic_linea_genetica_in_active);

                ConstraintLayout teceroConstraintLayout = (ConstraintLayout) llPerfil.getChildAt(2);
                llPerfil.removeView(teceroConstraintLayout);
                filtroFechaPuesto = false;
            }
        });

        llPerfil.addView(constraintLayout);
    }

    private String restarDias(int dias)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -dias);
        Date nowMinus15 = c.getTime();
        String fecha7dias = nowMinus15 + "";
        String[] arFech = fecha7dias.split(" ");
        String anio = arFech[5];
        String dia = arFech[2];
        String mes = mesNumerico(arFech[1]);
        return anio + "/" + mes + "/" + dia;
    }

    private String mesNumerico(String mes)
    {
        String mesNumerico = "";
        switch (mes)
        {
            case "Jan":
                mesNumerico = "01";
                break;
            case "Feb":
                mesNumerico = "02";
                break;
            case "Mar":
                mesNumerico = "03";
                break;
            case "Apr":
                mesNumerico = "04";
                break;
            case "May":
                mesNumerico = "05";
                break;
            case "Jun":
                mesNumerico = "06";
                break;
            case "Jul":
                mesNumerico = "07";
                break;
            case "Aug":
                mesNumerico = "08";
                break;
            case "Sep":
                mesNumerico = "09";
                break;
            case "Oct":
                mesNumerico = "10";
                break;
            case "Nov":
                mesNumerico = "11";
                break;
            case "Dec":
                mesNumerico = "12";
                break;
        }
        return mesNumerico;
    }

    private String fechaActualNumerico()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH) +1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        String strMes = mes + "";
        if (strMes.length() == 1)
        {
            strMes = "0" + strMes;
        }

        String strDia = dia + "";
        if (strDia.length() == 1)
        {
            strDia = "0" + strDia;
        }
        return year + "/" + strMes + "/" + strDia;
    }

    private void mostrarPedidosFavoritos()
    {
        alId.clear();
        alNumPedidoBSM.clear();
        alCia.clear();
        alCliente.clear();
        alEmail.clear();
        alFechaSolicitud.clear();
        alFechaEntrega.clear();
        alEstado.clear();
        alNevera.clear();
        alComentarios.clear();
        alIdProducto.clear();
        alIdPedido.clear();
        alNumero.clear();
        alFormato.clear();
        alLinea.clear();

        ArrayList<PedidoFavorito> arPedFav = pedidosDB.getPedidosFavoritos();
        if (arPedFav.size() == 0)
        {
            PedidosTerminadosFragment fragment1 = new PedidosTerminadosFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment1)
                    .commit();
        }
        else
        {
            int contador = 0;

            for (PedidoFavorito pedidoFavorito: arPedFav)
            {
                alId.add(pedidoFavorito.getIdPedido());
                alIdPedido.add(pedidoFavorito.getIdPedido());
                alFechaSolicitud.add(pedidoFavorito.getFechaSolicitud());
                alNumPedidoBSM.add(pedidoFavorito.getNumeroPedido());
                alFechaEntrega.add(pedidoFavorito.getFechaPedido());

                alEstado.add(pedidoFavorito.getEstado());
                alNevera.add(pedidoFavorito.getNevera());
                alComentarios.add(pedidoFavorito.getComentarios());
                contador++;
                if (contador == alId.size())
                {

                }
            }
            ivPEdidosEntregados.setEnabled(true);
            PedidosTerminadosFragment fragment1 = new PedidosTerminadosFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment1)
                    .commit();
        }
    }

    private boolean validarPermisos()
    {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return true;
        }
        if(checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }

        if(shouldShowRequestPermissionRationale(CAMERA))
        {
            requestPermissions(new String[] {CAMERA} , 100);
        }
        if(shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE))
        {
            requestPermissions(new String[] {READ_EXTERNAL_STORAGE} , 100);
        }
        else
        {
            requestPermissions(new String[] {CAMERA, READ_EXTERNAL_STORAGE} , 100);
        }

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode , permissions , grantResults);

        if(requestCode == 100)
        {
            if(grantResults.length == 2 && grantResults[0]  == PackageManager.PERMISSION_GRANTED)
            {
                ivfotoPerfil.setEnabled(true);
            }
        }

    }
}