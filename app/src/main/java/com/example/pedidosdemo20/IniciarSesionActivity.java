package com.example.pedidosdemo20;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pedidosdemo20.BasesDatos.CiaRegistrado;
import com.example.pedidosdemo20.BasesDatos.Nevera;
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

public class IniciarSesionActivity extends AppCompatActivity
{
    private TextView tvOlvidastePassword;
    private EditText etCorreo;
    private EditText etPassword;
    private ImageView ivAcceder;
    private ImageView ivActivarPassword;

    private String clave = "";
    private String nombreAnimalSat = "pedidos_kubus";
    private String passwordAnimalSat = "C4lleV@rsov1a.,:";

    private int idUser;
    private String rega = "";
    private String correo = "";
    private int idClienteFinal;
    private String idBSMFinal;
    private int idRutaFinal;

    private PedidosDB pedidosDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvOlvidastePassword = findViewById(R.id.tvOlvidastePassword);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        ivAcceder = findViewById(R.id.ivAcceder);
        ivActivarPassword = findViewById(R.id.ivActivarPassword);
        tvOlvidastePassword.setText(Html.fromHtml(getResources().getString(R.string.olvidastePasword)));
        pedidosDB = PedidosDB.getInstance(this);

        etCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etCorreo.getText().toString().equals("") || etPassword.getText().toString().equals(""))
                {
                    ivAcceder.setImageResource(R.drawable.ic_boton_acceder_bloqueado);
                    ivAcceder.setTag("0");
                }
                else
                {
                    ivAcceder.setImageResource(R.drawable.ic_boton_acceder_activo);
                    ivAcceder.setTag("1");
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etCorreo.getText().toString().equals("") || etPassword.getText().toString().equals(""))
                {
                    ivAcceder.setImageResource(R.drawable.ic_boton_acceder_bloqueado);
                    ivAcceder.setTag("0");
                }
                else
                {
                    ivAcceder.setImageResource(R.drawable.ic_boton_acceder_activo);
                    ivAcceder.setTag("1");
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    public void onclickOlvidastePassword(View view)
    {
        startActivity(new Intent(this, OlvidePasswordActivity.class));
        finish();
    }

    public void onclickActivarPassword(View view)
    {
        if (ivActivarPassword.getTag().toString().equals("0"))
        {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivActivarPassword.setImageResource(R.drawable.ic_ojo_password_activado);
            ivActivarPassword.setTag("1");
        }
        else
        {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivActivarPassword.setImageResource(R.drawable.ic_ojo_password);
            ivActivarPassword.setTag("0");
        }
    }

    public void onclickAcceder(View view)
    {
        if (ivAcceder.getTag().toString().equals("1"))
        {
            iniciarSesion();
        }
        else
        {

        }
    }

    private void iniciarSesion()
    {
        String email = etCorreo.getText().toString();
        String password = etPassword.getText().toString();

        postRequestUser(password, email);
    }

    private void postRequestUser(String password, String email)
    {
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer db = new StringBuffer();
        for (byte b: hash)
        {
            db.append(String.format("%02x", b));
        }

        final JSONObject jsonObj = new JSONObject();

        try
        {
            jsonObj.put("codigo", db.toString());
            jsonObj.put("email", email);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        String urlCrearfichaje = "https://connect.animalsat.com/api/userRegistradoBeta";

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, urlCrearfichaje, jsonObj, new Response.Listener<JSONObject>()
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

                if (jsonArray.length() > 0)
                {
                    JSONObject jsonObjectID;

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        try
                        {
                            jsonObjectID = jsonArray.getJSONObject(i);

                            idUser = jsonObjectID.getInt("id");
                            correo = jsonObjectID.getString("email");
                            rega = jsonObjectID.getString("rega");

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                    //pedidosDB.insertarUsuario(new Usuario(idUser,"-", email, rega, "-", "-", "-", "-", "-", "-", 0, 0));
                    sacarCiasRegistrados();
                }
                else
                {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(IniciarSesionActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.item_error_registro, null);
                    builder.setView(view);
                    builder.setCancelable(true);

                    ImageView ivIntentalo = view.findViewById(R.id.ivIntentalo);
                    TextView tvEmail = view.findViewById(R.id.tvEmail);
                    TextView tvTextoError = view.findViewById(R.id.tvTextoError);

                    tvTextoError.setText(getString(R.string.usuarioNoValido));
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
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                /*View view = new View(LoginActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
                clave = "Basic " + Base64.encodeToString((nombreAnimalSat + ":" + passwordAnimalSat).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", clave);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    private void sacarCiasRegistrados()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject bloquePrincipal = new JSONObject();

        try
        {
            bloquePrincipal.put("idUsuario", idUser);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        //jSonDatos = bloquePrincipal.toString();

        String urlCrearfichaje = "https://connect.animalsat.com/api/ciasDeUsuarioBeta";

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, urlCrearfichaje, bloquePrincipal, new Response.Listener<JSONObject>()
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

                int idUsuario;
                int idCliente;
                String nombreCia;
                String idBSM;
                int idRuta;
                String nombreGranja;
                String direccionGranja;
                String telefonoGranja;

                int contador = 0;

                if (jsonArray.length() == 0)
                {
                    postRequestNeverasUser();
                }
                else
                {
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        try
                        {
                            jsonObjectID = jsonArray.getJSONObject(i);

                            idUsuario = jsonObjectID.getInt("idUsuario");
                            idCliente = jsonObjectID.getInt("idcliente");
                            idClienteFinal = idCliente;
                            nombreCia = jsonObjectID.getString("nombreCia");
                            idBSM = jsonObjectID.getString("idBSM");
                            idBSMFinal = idBSM;
                            idRuta = jsonObjectID.getInt("idRuta");
                            idRutaFinal = idRuta;
                            nombreGranja = jsonObjectID.getString("nombreGranja");

                            direccionGranja = jsonObjectID.getString("direccionGranja");

                            telefonoGranja = jsonObjectID.getString("telefonoGranja");

                            pedidosDB.insertarCiaGuardado(new CiaRegistrado(idUsuario, idCliente, idRuta, nombreCia, idBSM, nombreGranja, direccionGranja, telefonoGranja));
                            pedidosDB.insertarUsuario(new Usuario(idUser, nombreGranja, "prueva", correo, rega, telefonoGranja, direccionGranja, nombreCia, idBSMFinal, idClienteFinal, idRutaFinal));
                            //pedidosDB.actualizarCamposUsuarioCia(idUser, nombreGranja, telefonoGranja, direccionGranja, "-", "-", nombreCia, idBSM, idCliente, idRuta);
                            contador++;
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        if (contador == jsonArray.length())
                        {
                            postRequestNeverasUser();//SACAR LA INFORMACION DE LA GRANJA PARA GUARDARLO EN DB LOCAL
                            //sacarInfoGranja();
                        }
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
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<>();
                clave = "Basic " + Base64.encodeToString((nombreAnimalSat + ":" + passwordAnimalSat).getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", clave);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        queue.add(stringRequest);
    }

    private void postRequestNeverasUser()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject bloquePrincipal = new JSONObject();

        try
        {
            bloquePrincipal.put("idUsuario", idUser);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        String urlCrearfichaje = "https://connect.animalsat.com/api/neverasRegistradasUserBeta";

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, urlCrearfichaje, bloquePrincipal, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                //Registrar las neveras en caso de que las tuviese de antes
                JSONArray jsonArray = null;

                try
                {
                    jsonArray = response.getJSONArray("response");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                if (jsonArray.length() != 0)
                {
                    JSONObject jsonObject;
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        try
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            String nombre = jsonObject.getString("nombre");
                            String numSerie = jsonObject.getString("numSerie");

                            pedidosDB.insertarNevera(new Nevera(nombre, numSerie));
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }

                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    pedidosDB.crearNotificacion(PedidosDB.NOT_ACTIVADAS, PedidosDB.notPosibles);
                }
                else
                {
                    pedidosDB.crearNotificacion(PedidosDB.NOT_DESACTIVADAS, PedidosDB.bloquearNOT);
                }*/

                startActivity(new Intent(IniciarSesionActivity.this, MainActivity.class));
                finish();
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
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> headers = new HashMap<>();
                clave = "Basic " + Base64.encodeToString((nombreAnimalSat + ":" + passwordAnimalSat).getBytes(), Base64.NO_WRAP);
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