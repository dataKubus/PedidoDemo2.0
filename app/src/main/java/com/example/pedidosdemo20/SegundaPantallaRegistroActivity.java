package com.example.pedidosdemo20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pedidosdemo20.BasesDatos.CiaRegistrado;
import com.example.pedidosdemo20.BasesDatos.PedidosDB;
import com.example.pedidosdemo20.BasesDatos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SegundaPantallaRegistroActivity extends AppCompatActivity
{
    private TextView tvNombreGranja;
    private TextView tvTituloPedido;
    private TextView tvNombre;
    private TextView tvCorreo;
    private TextView tvPassword;
    private TextView tvPassword2;

    private EditText etNombre;
    private EditText etCorreo;
    private EditText etPassword;
    private EditText etPassword2;

    private String nombreGranja = "";
    private int idCliente = 0;
    private String direccionGranja = "";
    private String telefonoGranja = "";
    private int idRuta = 0;
    private String codigoGranja = "";
    private String rega = "";

    private ImageView ivValidar;
    private ImageView ivActivarPassword;
    private ImageView ivActivarPassword2;

    private String jSonDatos = "";
    private String clave = "";
    private String nombre = "pedidos_kubus";
    private String password = "C4lleV@rsov1a.,:";

    private String nombreCia = "";
    private String idBSM = "";
    private PedidosDB pedidosDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_pantalla_registro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvNombreGranja = findViewById(R.id.tvNombreGranja);
        tvTituloPedido = findViewById(R.id.tvTituloPedido);
        tvNombre = findViewById(R.id.tvNombre);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvPassword = findViewById(R.id.tvPassword);
        tvPassword2 = findViewById(R.id.tvPassword2);
        ivValidar = findViewById(R.id.ivValidar);
        ivActivarPassword = findViewById(R.id.ivActivarPassword);
        ivActivarPassword2 = findViewById(R.id.ivActivarPassword2);
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        establecerFuentes();
        pedidosDB = PedidosDB.getInstance(this);

        nombreGranja = getIntent().getStringExtra(PrimeraPantallaRegistroActivity.KEY_NOMBRE_GRANJA);
        direccionGranja = getIntent().getStringExtra(PrimeraPantallaRegistroActivity.KEY_DIRECCION_GRANJA);
        idCliente = getIntent().getIntExtra(PrimeraPantallaRegistroActivity.KEY_ID_CLIENTE, 0);
        telefonoGranja = getIntent().getStringExtra(PrimeraPantallaRegistroActivity.KEY_NOMBRE_GRANJA);
        idRuta = getIntent().getIntExtra(PrimeraPantallaRegistroActivity.KEY_ID_RUTA, 0);
        codigoGranja = getIntent().getStringExtra(PrimeraPantallaRegistroActivity.KEY_CODIGO_GRANJA);
        rega = getIntent().getStringExtra(PrimeraPantallaRegistroActivity.KEY_REGA);
        tvNombreGranja.setText(nombreGranja);

        etNombre.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etNombre.getText().toString().equals("") || etCorreo.getText().toString().equals("") || etPassword.getText().toString().equals("") || etPassword2.getText().toString().equals(""))
                {
                    ivValidar.setImageResource(R.drawable.ic_boton_acceder_bloqueado);
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

        etCorreo.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etNombre.getText().toString().equals("") || etCorreo.getText().toString().equals("") || etPassword.getText().toString().equals("") || etPassword2.getText().toString().equals(""))
                {
                    ivValidar.setImageResource(R.drawable.ic_boton_acceder_bloqueado);
                    ivValidar.setTag(0);
                }
                else
                {
                    ivValidar.setImageResource(R.drawable.ic_boton_acceder_activo);
                    ivValidar.setTag(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etNombre.getText().toString().equals("") || etCorreo.getText().toString().equals("") || etPassword.getText().toString().equals("") || etPassword2.getText().toString().equals(""))
                {
                    ivValidar.setImageResource(R.drawable.ic_boton_acceder_bloqueado);
                    ivValidar.setTag(0);
                }
                else
                {
                    ivValidar.setImageResource(R.drawable.ic_boton_acceder_activo);
                    ivValidar.setTag(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        etPassword2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etNombre.getText().toString().equals("") || etCorreo.getText().toString().equals("") || etPassword.getText().toString().equals("") || etPassword2.getText().toString().equals(""))
                {
                    ivValidar.setImageResource(R.drawable.ic_boton_acceder_bloqueado);
                    ivValidar.setTag(0);
                }
                else
                {
                    ivValidar.setImageResource(R.drawable.ic_boton_acceder_activo);
                    ivValidar.setTag(1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

    }

    private void establecerFuentes()
    {
        Typeface poppins = Typeface.createFromAsset(getAssets(), "font/Poppins-Regular.ttf");
        tvNombreGranja.setTypeface(poppins);
        tvTituloPedido.setTypeface(poppins);
        tvNombre.setTypeface(poppins);
        tvCorreo.setTypeface(poppins);
        tvTituloPedido.setTypeface(poppins);
        tvPassword.setTypeface(poppins);
        tvPassword2.setTypeface(poppins);
        etNombre.setTypeface(poppins);
        etCorreo.setTypeface(poppins);
        etPassword.setTypeface(poppins);
        etPassword2.setTypeface(poppins);
    }

    public void onclickValidar(View view)
    {
        if (ivValidar.getTag().toString().equals("1"))
        {
            String email = etCorreo.getText().toString();
            if (emailValido(email))
            {
                comprobarUsuario();
            }
            else
            {
                Toast.makeText(SegundaPantallaRegistroActivity.this, R.string.emailNoValido, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {

        }
    }

    private boolean emailValido(String email)
    {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private void comprobarUsuario()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final JSONObject jsonObj = new JSONObject();
        String mail = etCorreo.getText().toString();
        try
        {
            jsonObj.put("email", mail);

            jSonDatos = jsonObj.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlCrearfichaje = "https://connect.animalsat.com/api/emailRepetidoBeta";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCrearfichaje, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                String[] email = response.split("email");
                if (email.length > 1)
                {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SegundaPantallaRegistroActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.item_error_registro, null);
                    builder.setView(view);
                    builder.setCancelable(true);

                    ImageView ivIntentalo = view.findViewById(R.id.ivIntentalo);
                    TextView tvEmail = view.findViewById(R.id.tvEmail);
                    TextView tvTextoError = view.findViewById(R.id.tvTextoError);

                    tvEmail.setText(R.string.emailRegistrado);
                    Typeface poppins = Typeface.createFromAsset(getAssets(), "font/Poppins-Regular.ttf");
                    tvEmail.setTypeface(poppins);
                    tvTextoError.setTypeface(poppins);

                    final android.app.AlertDialog alertDialog = builder.create();

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
                    //realizarRegistro();
                    sacarCiaRegistrado();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*View view = new View(SegundaPantallaRegistroActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(SegundaPantallaRegistroActivity.this);
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
            public byte[] getBody() throws AuthFailureError
            {
                try
                {
                    return jSonDatos == null ? null : jSonDatos.getBytes("utf-8");
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

    private void sacarCiaRegistrado()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final JSONObject jsonObj = new JSONObject();

        try
        {
            jsonObj.put("id", idCliente);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlRecogerPedido = "https://connect.animalsat.com/api/ciaEncontrado";

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
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SegundaPantallaRegistroActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.item_error_registro, null);
                    builder.setView(view);
                    builder.setCancelable(true);

                    ImageView ivIntentalo = view.findViewById(R.id.ivIntentalo);
                    TextView tvEmail = view.findViewById(R.id.tvEmail);
                    TextView tvTextoError = view.findViewById(R.id.tvTextoError);

                    tvEmail.setText(R.string.emailRegistrado);
                    Typeface poppins = Typeface.createFromAsset(getAssets(), "font/Poppins-Regular.ttf");
                    tvEmail.setTypeface(poppins);
                    tvTextoError.setTypeface(poppins);

                    final android.app.AlertDialog alertDialog = builder.create();

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
                    int contador = 0;

                    String cia = "";
                    String idbsm = "";

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        try
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            cia = jsonObject.getString("nombre");
                            nombreCia = cia;

                            idbsm = jsonObject.getString("idProducto");
                            idBSM = idbsm;

                            contador++;
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    if (contador == jsonArray.length())
                    {
                        leyProteccionDatos();
                        //realizarRegistro();
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*View view;
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);
                view = getLayoutInflater().inflate(R.layout.item_sin_conexion, null);
                builder.setView(view);

                TextView tvOk = view.findViewById(R.id.tvOk);

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

    private void leyProteccionDatos()
    {
        View view;
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(SegundaPantallaRegistroActivity.this);
        view = getLayoutInflater().inflate(R.layout.alert_dialog_ley_proteccion_datos, null);
        builder.setView(view);

        /*TextView tvAceptarTerminos = view.findViewById(R.id.tvAceptarTerminos);
        final CheckBox cbAceptar = view.findViewById(R.id.cbAceptar);*/
        ImageView ivAcceder = view.findViewById(R.id.ivAcceder);
        NestedScrollView scrollView5 = view.findViewById(R.id.scrollView5);

        scrollView5.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())
                {
                    ivAcceder.setTag("1");
                    ivAcceder.setImageResource(R.drawable.ic_boton_acceder_activo);
                }
                else
                {
                    ivAcceder.setTag("0");
                    ivAcceder.setImageResource(R.drawable.ic_boton_acceder_bloqueado);
                }
            }
        });

        ivAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (ivAcceder.getTag().toString().equals("1"))
                {
                    realizarRegistro();
                }
                else
                {

                }
            }
        });

        final android.app.AlertDialog alertDialog = builder.create();

        /*tvAceptarTerminos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (cbAceptar.isChecked())
                {

                }
                else
                {
                    Toast.makeText(SegundaPantallaRegistroActivity.this, R.string.debesAceptarTerminos, Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        alertDialog.show();
    }

    private void realizarRegistro()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final String mail = etCorreo.getText().toString();
        String passwd = etPassword.getText().toString();

        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        byte[] hash = md.digest(passwd.getBytes());
        StringBuffer db = new StringBuffer();
        for (byte b: hash)
        {
            db.append(String.format("%02x", b));
        }

        final JSONObject jsonObj = new JSONObject();

        try
        {
            jsonObj.put("email", mail);
            jsonObj.put("codigo", db.toString());
            jsonObj.put("rega", rega);

            jSonDatos = jsonObj.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlCrearfichaje = "https://connect.animalsat.com/api/insertaruserBeta";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCrearfichaje, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                postRequestIdUsuario();
                /*pedidosDB.insertarUsuario(new Usuario(idUser,"-", mail, rega, "-", "-", "-", "-", "", "", "", "-", "-"));
                startActivity(new Intent(RegistroActivity.this, MainActivity.class));
                finish();*/
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*View view = new View(RegistroActivity.this);
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(RegistroActivity.this);
                view = getLayoutInflater().inflate(R.layout.item_sin_conexion, null);
                builder.setView(view);

                TextView tvOk = view.findViewById(R.id.tvOk);

                final androidx.appcompat.app.AlertDialog alertDialog = builder.create();

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
            public byte[] getBody() throws AuthFailureError
            {
                try
                {
                    return jSonDatos == null ? null : jSonDatos.getBytes("utf-8");
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

    private void postRequestIdUsuario()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final JSONObject jsonObj = new JSONObject();

        final String mail = etCorreo.getText().toString();

        try
        {
            jsonObj.put("email", mail);

            jSonDatos = jsonObj.toString();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlCrearfichaje = "https://connect.animalsat.com/api/idUsuarioBeta";

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, urlCrearfichaje, jsonObj, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                JSONArray jsonArray = null;

                int idUser = 0;

                try
                {
                    jsonArray = response.getJSONArray("response");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JSONObject jsonObjectID;

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    try
                    {
                        jsonObjectID = jsonArray.getJSONObject(i);

                        idUser = jsonObjectID.getInt("id");

                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }

                //String[] arResponse = response.split("id\":");
                //String idUser = arResponse[1].replace("}]}","");
                pedidosDB.insertarUsuario(new Usuario(idUser, etNombre.getText().toString(), nombreGranja, mail, rega, telefonoGranja, direccionGranja, nombreCia, idBSM, idCliente, idRuta));
                //pedidosDB.actualizarCamposUsuarioCia(idUser, nombreGranja, telefonoGranja, direccionGranja, "-", "-", nombreCia, idBSM, idcliente, idRuta);
                pedidosDB.insertarCiaGuardado(new CiaRegistrado(idUser, idCliente, idRuta, nombreCia, idBSM, nombreGranja, direccionGranja, telefonoGranja));
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    pedidosDB.crearNotificacion(PedidosDB.NOT_ACTIVADAS, PedidosDB.notPosibles);
                }
                else
                {
                    pedidosDB.crearNotificacion(PedidosDB.NOT_DESACTIVADAS, PedidosDB.bloquearNOT);
                }*/
                insertarCiaAnimalSat(idUser, idCliente, nombreCia, idBSM, idRuta, nombreGranja, direccionGranja, telefonoGranja);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*View view = new View(RegistroActivity.this);
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(RegistroActivity.this);
                view = getLayoutInflater().inflate(R.layout.item_sin_conexion, null);
                builder.setView(view);

                TextView tvOk = view.findViewById(R.id.tvOk);

                final androidx.appcompat.app.AlertDialog alertDialog = builder.create();

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
    }

    private void insertarCiaAnimalSat(int idUser, int idCliente, String nombreCia, String idBSM, int idRuta, String nombreGranja, String direccionGranja, String telefonoGranja)
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final JSONObject jsonObj = new JSONObject();

        try
        {
            jsonObj.put("idUsuario", idUser);
            jsonObj.put("idCliente", idCliente);
            jsonObj.put("nombreCia", nombreCia);
            jsonObj.put("idBSM", idBSM);
            jsonObj.put("idRuta", idRuta);
            jsonObj.put("nombreGranja", nombreGranja);
            jsonObj.put("direccionGranja", direccionGranja);
            jsonObj.put("telefonoGranja", telefonoGranja);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlRecogerPedido = "https://connect.animalsat.com/api/insertarCiaClienteBeta";

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, urlRecogerPedido, jsonObj, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                startActivity(new Intent(SegundaPantallaRegistroActivity.this, MisPedidosActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*View view;
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(RegistroActivity.this);
                view = getLayoutInflater().inflate(R.layout.item_sin_conexion, null);
                builder.setView(view);

                TextView tvOk = view.findViewById(R.id.tvOk);

                final androidx.appcompat.app.AlertDialog alertDialog = builder.create();

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

    public void onclickActivarPassword(View view)
    {
        if (ivActivarPassword.getTag().toString().equals("1"))
        {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivActivarPassword.setTag(0);
            ivActivarPassword.setImageResource(R.drawable.ic_ojo_password);
        }
        else
        {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivActivarPassword.setTag(1);
            ivActivarPassword.setImageResource(R.drawable.ic_ojo_password_activado);
        }
    }

    public void onclickActivarPassword2(View view)
    {
        if (ivActivarPassword2.getTag().toString().equals("1"))
        {
            etPassword2.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivActivarPassword2.setTag(0);
            ivActivarPassword2.setImageResource(R.drawable.ic_ojo_password);
        }
        else
        {
            etPassword2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivActivarPassword2.setTag(1);
            ivActivarPassword2.setImageResource(R.drawable.ic_ojo_password_activado);
        }
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, PrimeraPantallaRegistroActivity.class));
        finish();
    }
}