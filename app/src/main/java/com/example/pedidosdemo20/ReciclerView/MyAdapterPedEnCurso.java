package com.example.pedidosdemo20.ReciclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pedidosdemo20.BasesDatos.PedidoFavorito;
import com.example.pedidosdemo20.BasesDatos.PedidosDB;
import com.example.pedidosdemo20.DetallesPedidoActivity;
import com.example.pedidosdemo20.R;

import java.util.List;

public class MyAdapterPedEnCurso extends RecyclerView.Adapter<MyAdapterPedEnCurso.ViewHolder>
{
    static Context context;
    private List<MyListEnCurso> arLista;

    public static final String KEY_ID = "key_id";
    public static final String KEY_NUM_PEDIDO = "key_id_pedido";
    public static final String KEY_NUM_ALBARAN = "key_num_albaran";
    public static final String KEY_FECHA_PEDIDO = "key_fecha_pedido";
    public static final String KEY_FECHA_ENTREGA = "key_fecha_entrega";
    public static final String KEY_FECHA_SOLICITUD = "key_fecha_solicitud";
    public static final String KEY_NEVERA = "key_nevera";
    public static final String KEY_COMENTARIOS = "key_comentarios";

    private String estadoREg = "registrado";
    private String estadoAct = "en curso";
    private String estadoREp = "en reparto";

    public MyAdapterPedEnCurso(Context context, List<MyListEnCurso> arLista)
    {
        this.context = context;
        this.arLista = arLista;
    }

    @NonNull
    @Override
    public MyAdapterPedEnCurso.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_en_progreso, parent, false);

        //view.setOnClickListener(this);

        return new MyAdapterPedEnCurso.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterPedEnCurso.ViewHolder holder, int position)
    {
        holder.tvIdPedido.setText(arLista.get(position).getIdPedido() + "");
        holder.tvFechaSolicitud.setText(arLista.get(position).getFechaSolicitud());
        holder.tvNumdosis.setText(arLista.get(position).getNumDosis() + "");
        holder.tvFormato.setText(arLista.get(position).getFormato());
        holder.tvLineaGenetica.setText(arLista.get(position).getLineaGenetica());

        Typeface poppins = Typeface.createFromAsset(context.getAssets(), "font/Poppins-Regular.ttf");

        holder.tvFechaSolicitud.setTypeface(poppins);
        holder.tvNumdosis.setTypeface(poppins);
        holder.tvFormato.setTypeface(poppins);
        holder.tvLineaGenetica.setTypeface(poppins);

        //holder.tvNumPedido.setText(arLista.get(position).getNumeroPedido());
        //holder.tvNumAlbaran.setText(context.getString(R.string.albaran) + " " + arLista.get(position).getNumeroAlbaran());

        if (arLista.get(position).getNumeroAlbaran() == "")
        {
            //holder.tvNumAlbaran.setText("-");
        }
        if (arLista.get(position).getNumeroPedido() == "")
        {
            //holder.tvNumPedido.setText("-");
        }
        if (arLista.get(position).getPedidoFavorito() == MyAdapter.pedidoFavorito)
        {
            holder.ivFavorito.setImageResource(R.drawable.ic_favorito_activo);
            holder.ivFavorito.setTag(R.drawable.ic_favorito_activo);
        }
        else
        {
            holder.ivFavorito.setImageResource(R.drawable.ic_favorito);
            holder.ivFavorito.setTag(R.drawable.ic_favorito);
        }

        String estado = arLista.get(position).getEstado();
        String url = "";

        if (estado.equals(estadoREg))
        {
            url = "https://www.kubus-sa.com/wp-content/uploads/2023/06/Espera.gif";
            holder.tvEstadoPedido.setBackgroundColor(context.getResources().getColor(R.color.rojo));
            holder.tvEstadoPedido.setText(R.string.pedidoEnEspera);
            holder.tvEstadoPedido.setTypeface(poppins);
            holder.tvFecha.setText(R.string.fechaDePedido);
            holder.tvFecha.setTypeface(poppins);
            holder.tvFechaPedido.setText(arLista.get(position).getFechaSolicitud());
            holder.tvFechaPedido.setTypeface(poppins);
        }
        else if (estado.equals(estadoAct))
        {
            url = "https://www.kubus-sa.com/wp-content/uploads/2023/06/Confirmado.gif";
            holder.tvEstadoPedido.setBackgroundColor(context.getResources().getColor(R.color.naranja));
            holder.tvEstadoPedido.setText(R.string.pedidoConfirmado);
            holder.tvEstadoPedido.setTypeface(poppins);
            holder.tvFecha.setText(R.string.fechaPrevistaEntrega);
            holder.tvFecha.setTypeface(poppins);
            holder.tvFechaPedido.setText(arLista.get(position).getFechaPedido());
            holder.tvFechaPedido.setTypeface(poppins);
        }
        else
        {
            url = "https://www.kubus-sa.com/wp-content/uploads/2023/06/reparto_1.gif";
            holder.tvEstadoPedido.setBackgroundColor(context.getResources().getColor(R.color.azul));
            holder.tvEstadoPedido.setText(R.string.pedidoEnReparto);
            holder.tvEstadoPedido.setTypeface(poppins);
            holder.tvFecha.setText(R.string.fechaPrevistaEntrega);
            holder.tvFecha.setTypeface(poppins);
            holder.tvFechaPedido.setText(arLista.get(position).getFechaPedido());
            holder.tvFechaPedido.setTypeface(poppins);
        }

        Uri uri = Uri.parse(url);
        Glide.with(context.getApplicationContext()).load(uri).into(holder.ivIconoEstado);
    }

    @Override
    public int getItemCount()
    {
        return arLista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private PedidosDB pedidosDB;

        TextView tvFechaSolicitud;
        TextView tvIdPedido;
        TextView tvFecha;
        TextView tvFechaPedido;
        TextView tvEstadoPedido;
        TextView tvNevera;
        TextView tvComentarios;

        ImageView ivVerPedido;
        ImageView ivRepetirPedido;
        ImageView ivFavorito;
        ImageView ivIconoEstado;

        TextView tvNumdosis;
        TextView tvLineaGenetica;
        TextView tvFormato;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvIdPedido = itemView.findViewById(R.id.tvIdPedido);
            tvFechaSolicitud = itemView.findViewById(R.id.tvFechaSolicitud);

            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvFechaPedido = itemView.findViewById(R.id.tvFechaPedido);
            ivVerPedido = itemView.findViewById(R.id.ivVerPedido);
            ivRepetirPedido = itemView.findViewById(R.id.ivRepetirPedido);
            ivFavorito = itemView.findViewById(R.id.ivFavorito);
            ivIconoEstado = itemView.findViewById(R.id.ivIconoEstado);
            tvEstadoPedido = itemView.findViewById(R.id.tvEstadoPedido);
            tvNevera = itemView.findViewById(R.id.tvNevera);
            tvComentarios = itemView.findViewById(R.id.tvComentarios);

            tvNumdosis = itemView.findViewById(R.id.tvNumdosis);
            tvLineaGenetica = itemView.findViewById(R.id.tvLineaGenetica);
            tvFormato = itemView.findViewById(R.id.tvFormato);

            pedidosDB = PedidosDB.getInstance(context);

            ivVerPedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    // Ejemplo: Iniciar una nueva actividad al hacer clic en el TextView
                    int idPed = Integer.parseInt(tvIdPedido.getText().toString());
                    //String numPedido = tvNumPedido.getText().toString();
                    //String numAlbaran = tvNumAlbaran.getText().toString();
                    String fechaPedido = tvFechaPedido.getText().toString();
                    String fechaSolicitud = tvFechaSolicitud.getText().toString();
                    String nevera = tvNevera.getText().toString();
                    String comentarios = tvComentarios.getText().toString();

                    Intent intent = new Intent(context, DetallesPedidoActivity.class);
                    intent.putExtra(KEY_ID, idPed);
                    //intent.putExtra(KEY_NUM_PEDIDO, numPedido);
                    //intent.putExtra(KEY_NUM_ALBARAN, numAlbaran);
                    intent.putExtra(KEY_FECHA_PEDIDO, fechaPedido);
                    intent.putExtra(KEY_FECHA_ENTREGA, fechaPedido);
                    intent.putExtra(KEY_FECHA_SOLICITUD, fechaSolicitud);
                    intent.putExtra(KEY_NEVERA, nevera);
                    intent.putExtra(KEY_COMENTARIOS, comentarios);
                    context.startActivity(intent);
                    ((AppCompatActivity) view.getContext()).finish();
                }
            });

            ivFavorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int iconoActual = (Integer) ivFavorito.getTag();
                    int comparar = R.drawable.ic_favorito;

                    if (iconoActual == comparar)
                    {
                        ivFavorito.setImageResource(R.drawable.ic_favorito_activo);
                        ivFavorito.setTag(R.drawable.ic_favorito_activo);
                        pedidosDB.insertarPedidoFavorito(new PedidoFavorito(Integer.parseInt(tvIdPedido.getText().toString()), tvFechaSolicitud.getText().toString(), "", "", tvFechaPedido.getText().toString(), MyAdapter.pedidoFavorito, tvEstadoPedido.getText().toString(), tvNevera.getText().toString(), tvComentarios.getText().toString()));
                    }
                    else
                    {
                        ivFavorito.setImageResource(R.drawable.ic_favorito);
                        ivFavorito.setTag(R.drawable.ic_favorito);
                        pedidosDB.elimiarPedidoFavorito(Integer.parseInt(tvIdPedido.getText().toString()));
                    }
                }
            });
        }
    }
}
