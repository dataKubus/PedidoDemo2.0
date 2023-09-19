package com.example.pedidosdemo20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class OlvidePasswordActivity extends AppCompatActivity
{
    private EditText etCorreo;
    private ImageView ivAcceder;

    private String jSonDatos = "";
    private String clave = "";
    private String nombre = "pedidos_kubus";
    private String password = "C4lleV@rsov1a.,:";

    String codigoVerificacion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvide_password);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ivAcceder = findViewById(R.id.ivAcceder);
        etCorreo = findViewById(R.id.etCorreo);
        etCorreo.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (etCorreo.getText().toString().equals(""))
                {
                    ivAcceder.setImageResource(R.drawable.ic_validar_bloqueado);
                    ivAcceder.setTag("0");
                }
                else
                {
                    ivAcceder.setImageResource(R.drawable.ic_validar_activo);
                    ivAcceder.setTag("1");
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
    }

    public void onclickValidar(View view)
    {
        if (ivAcceder.getTag().toString().equals("1"))
        {
            validarCorreo();
        }
    }

    private void validarCorreo()
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
                    generarCodigoAcceso();
                }
                else
                {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OlvidePasswordActivity.this);
                    View view = getLayoutInflater().inflate(R.layout.item_error_registro, null);
                    builder.setView(view);
                    builder.setCancelable(true);

                    ImageView ivIntentalo = view.findViewById(R.id.ivIntentalo);
                    TextView tvEmail = view.findViewById(R.id.tvEmail);
                    TextView tvTextoError = view.findViewById(R.id.tvTextoError);

                    tvEmail.setText(R.string.emailNoRegistrado);
                    tvTextoError.setText(R.string.emailIncorrecto);
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

    private void generarCodigoAcceso()
    {
        Random random = new Random();
        ArrayList<String> arCaracteres = new ArrayList<>();

        int caracter1 = random.nextInt(9);
        int caracter2 = random.nextInt(9);
        int caracter3 = random.nextInt(9);
        char caracter4 = (char) (random.nextInt(26) + 'a');
        char caracter5 = (char) (random.nextInt(26) + 'a');
        char caracter6 = (char) (random.nextInt(26) + 'a');

        arCaracteres.add(caracter1 + "");
        arCaracteres.add(caracter2 + "");
        arCaracteres.add(caracter3 + "");
        arCaracteres.add(caracter4 + "");
        arCaracteres.add(caracter5 + "");
        arCaracteres.add(caracter6 + "");

        Collections.shuffle(arCaracteres);
        boolean terminado = false;
        int contador = 0;
        for (String caracteres: arCaracteres)
        {
            codigoVerificacion += caracteres;
            contador++;
            if (contador == arCaracteres.size())
            {
                terminado = true;
            }
        }
        if (terminado)
        {
            mandarMail();
        }
    }

    private void mandarMail()
    {
        String emailDestino = etCorreo.getText().toString();
        final String correoOficialenvio = "pedidosKubus@gmail.com";
        final String passwordOficial = "qhxbsqntdkayzscw";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session = Session.getInstance(properties, new Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(correoOficialenvio, passwordOficial);
            }
        });

        Message message = new MimeMessage(session);

        String asunto = "Código de verificación";
        String mensaje = "Hola, tu código de verificación es: " + codigoVerificacion;
        try
        {
            message.setFrom(new InternetAddress(correoOficialenvio));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino.trim()));
            message.setSubject(asunto.trim());
            message.setText(mensaje.trim());

            new SendMail().execute(message);
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void onclickAtras(View view)
    {
        startActivity(new Intent(this, IniciarSesionActivity.class));
        finish();
    }

    private class SendMail extends AsyncTask<Message,String,String>
    {
        @Override
        protected String doInBackground(Message... messages)
        {
            try
            {
                Transport.send(messages[0]);
            }
            catch (MessagingException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(this, IniciarSesionActivity.class));
        finish();
    }
}