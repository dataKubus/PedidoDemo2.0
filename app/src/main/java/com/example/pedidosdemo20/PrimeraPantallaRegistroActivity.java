package com.example.pedidosdemo20;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrimeraPantallaRegistroActivity extends AppCompatActivity
{
    private EditText etRega;
    private EditText etCodigoGranja;
    private ImageView ivValidar;

    private String nombreGranja = "";
    private int idCliente = 0;
    private String direccionGranja = "";
    private String telefonoGranja = "";
    private int idRuta = 0;

    private String clave = "";
    private String nombre = "pedidos_kubus";
    private String password = "C4lleV@rsov1a.,:";

    private TextView tvTituloPedido;
    private TextView tvRega;
    private TextView tvCodigoGranja;
    private TextView tvTextoCodigo;

    public static final String KEY_NOMBRE_GRANJA = "key_granja";
    public static final String KEY_ID_CLIENTE = "key_id_cliente";
    public static final String KEY_DIRECCION_GRANJA = "key_direccion_granja";
    public static final String KEY_TELEFONO_GRANJA = "key_telefono_granja";
    public static final String KEY_ID_RUTA = "key_id_ruta";
    public static final String KEY_CODIGO_GRANJA = "key_codigo_granja";
    public static final String KEY_REGA = "key_rega";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primera_pantalla_registro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        etRega = findViewById(R.id.etRega);
        etCodigoGranja = findViewById(R.id.etCodigoGranja);
        ivValidar = findViewById(R.id.ivValidar);

        tvTituloPedido = findViewById(R.id.tvTituloPedido);
        tvRega = findViewById(R.id.tvRega);
        tvCodigoGranja = findViewById(R.id.tvCodigoGranja);
        tvTextoCodigo = findViewById(R.id.tvTextoCodigo);
        establecerFuente();

        etCodigoGranja.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etCodigoGranja.getText().toString().equals("") || etRega.getText().toString().equals(""))
                {
                    ivValidar.setImageResource(R.drawable.ic_validar_bloqueado);
                    ivValidar.setTag(0);
                }
                else
                {
                    ivValidar.setImageResource(R.drawable.ic_validar_activo);
                    ivValidar.setTag(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        etRega.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etCodigoGranja.getText().toString().equals("") || etRega.getText().toString().equals(""))
                {
                    ivValidar.setImageResource(R.drawable.ic_validar_bloqueado);
                    ivValidar.setTag(0);
                }
                else
                {
                    ivValidar.setImageResource(R.drawable.ic_validar_activo);
                    ivValidar.setTag(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    private void establecerFuente()
    {
        Typeface poppins = Typeface.createFromAsset(getAssets(), "font/Poppins-Regular.ttf");
        tvRega.setTypeface(poppins);
        tvCodigoGranja.setTypeface(poppins);
        tvTextoCodigo.setTypeface(poppins);
        tvTituloPedido.setTypeface(poppins);
        etRega.setTypeface(poppins);
        etCodigoGranja.setTypeface(poppins);
    }

    public void onclickValidar(View view)
    {
        if (ivValidar.getTag().toString().equals("1"))
        {
            comprobarGranja();
        }
        else
        {

        }
    }

    private void comprobarGranja()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final JSONObject jsonObj = new JSONObject();

        String rega = etRega.getText().toString();
        String codigoGranja = etCodigoGranja.getText().toString();

        try
        {
            jsonObj.put("rega", rega);
            jsonObj.put("codigoGranja", codigoGranja);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlRecogerPedido = "https://connect.animalsat.com/api/ciasDisponibles";

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

                if (jsonArray.length() == 0)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PrimeraPantallaRegistroActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.item_error_registro, null);
                    builder.setView(view);
                    builder.setCancelable(true);

                    ImageView ivIntentalo = view.findViewById(R.id.ivIntentalo);
                    TextView tvEmail = view.findViewById(R.id.tvEmail);
                    TextView tvTextoError = view.findViewById(R.id.tvTextoError);

                    Typeface poppins = Typeface.createFromAsset(getAssets(), "font/Poppins-Regular.ttf");
                    tvEmail.setTypeface(poppins);
                    tvTextoError.setTypeface(poppins);

                    final AlertDialog alertDialog = builder.create();

                    ivIntentalo.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            alertDialog.dismiss();
                        }
                    });

                    alertDialog.show();
                }
                else
                {
                    JSONObject jsonObject;
                    String nombre = "";
                    int idCli = 0;
                    int contador = 0;
                    String direccion = "";
                    String telefono = "";
                    int idRut = 0;

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        try
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            nombre = jsonObject.getString("nombre");
                            if (nombre.equals(null))
                            {
                                nombreGranja = "-";
                            }
                            else
                            {
                                nombreGranja = nombre;
                            }

                            idCli = Integer.parseInt(jsonObject.getString("idCliente"));
                            idCliente = idCli;

                            direccion = jsonObject.getString("direccion");
                            if (direccion.equals("null"))
                            {
                                direccionGranja = "-";
                            }
                            else {
                                direccionGranja = direccion;
                            }

                            telefono = jsonObject.getString("telefono");
                            if (telefono.equals("null"))
                            {
                                telefonoGranja = "-";
                            }
                            else {
                                telefonoGranja = telefono;
                            }

                            idRut = jsonObject.getInt("idRuta");
                            idRuta = idRut;

                            contador++;
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    if (contador == jsonArray.length())
                    {
                        Intent intent = new Intent(PrimeraPantallaRegistroActivity.this, SegundaPantallaRegistroActivity.class);
                        intent.putExtra(KEY_NOMBRE_GRANJA, nombreGranja);
                        intent.putExtra(KEY_DIRECCION_GRANJA, direccionGranja);
                        intent.putExtra(KEY_ID_CLIENTE, idCliente);
                        intent.putExtra(KEY_ID_RUTA, idRuta);
                        intent.putExtra(KEY_TELEFONO_GRANJA, telefonoGranja);
                        intent.putExtra(KEY_CODIGO_GRANJA, codigoGranja);
                        intent.putExtra(KEY_REGA, rega);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                View view;
                /*AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);
                view = getLayoutInflater().inflate(R.layout.item_sin_conexion, null);
                builder.setView(view);

                TextView tvOk = view.findViewById(R.id.tvOk);
                TextView tvTexto = view.findViewById(R.id.tvTexto);

                tvTexto.setText(error + "");
                final AlertDialog alertDialog = builder.create();

                tvOk.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, PrimeraPantallaActivity.class));
        finish();
    }
}