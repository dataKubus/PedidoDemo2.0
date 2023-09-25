package com.example.pedidosdemo20.ReciclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pedidosdemo20.BasesDatos.PedidoFavorito;
import com.example.pedidosdemo20.BasesDatos.PedidosDB;
import com.example.pedidosdemo20.DetallesPedidoActivity;
import com.example.pedidosdemo20.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    static Context context;
    private List<MyList> arLista;
    public static int pedidoFavorito = 5;
    public static int pedidoNormal = 1;

    public static final String KEY_ID = "key_id";
    public static final String KEY_NUM_PEDIDO = "key_id_pedido";
    public static final String KEY_NUM_ALBARAN = "key_num_albaran";
    public static final String KEY_FECHA_PEDIDO = "key_fecha_pedido";
    public static final String KEY_FECHA_ENTREGA = "key_fecha_entrega";
    public static final String KEY_FECHA_SOLICITUD = "key_fecha_solicitud";
    public static final String KEY_NEVERA = "key_nevera";
    public static final String KEY_COMENTARIOS = "key_comentarios";

    public MyAdapter(Context context, List<MyList> arLista)
    {
        this.context = context;
        this.arLista = arLista;
    }

    /*@Override
    public void onClick(View view)
    {
        if (listener!=null)
        {
            listener.onClick(view);
        }
    }*/

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido_entregado, parent, false);

        //view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position)
    {
        holder.tvIdPedido.setText(arLista.get(position).getIdPedido() + "");
        holder.tvFechaSolicitud.setText(arLista.get(position).getFechaSolicitud());

        holder.tvNumPedido.setText(arLista.get(position).getNumeroPedido());
        holder.tvFechaPedidoEntregado.setText(arLista.get(position).getFechaPedido());
        holder.tvNumAlbaran.setText(context.getString(R.string.albaran) + " " + arLista.get(position).getNumeroAlbaran());



        /*holder.tvNumdosis.setText(arLista.get(position).getNumDosis() + "");
        holder.tvFormato.setText(arLista.get(position).getFormato());
        holder.tvLineaGenetica.setText(arLista.get(position).getLineaGenetica());*/

        if (arLista.get(position).getNumeroAlbaran() == "")
        {
            holder.tvNumAlbaran.setText("-");
        }
        if (arLista.get(position).getNumeroPedido() == "")
        {
            holder.tvNumPedido.setText("-");
        }
        if (arLista.get(position).getPedidoFavorito() == pedidoFavorito)
        {
            holder.ivFavoritoEntregado.setImageResource(R.drawable.ic_favorito_activo);
            holder.ivFavoritoEntregado.setTag(R.drawable.ic_favorito_activo);
        }
        else
        {
            holder.ivFavoritoEntregado.setImageResource(R.drawable.ic_favorito);
            holder.ivFavoritoEntregado.setTag(R.drawable.ic_favorito);
        }

        /*holder.textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                holder.imageView.setImageResource(R.drawable.ic_letras);
            }
        });
        if (arLista.get(position).getNombre().equals("isner"))
        {
            holder.imageView.setImageResource(R.drawable.ic_letras);
        }
        holder.imageView.setImageResource(R.drawable.ic_launcher_background);*/
        //String data = arLista.get(position);
        //holder.tvNumPedido.setText(data);
        /*MyList  = itemList.get(position);

        holder.textViewTitle.setText(item.getTitle());
        holder.textViewDescription.setText(item.getDescription());
        holder.imageView.setImageResource(item.getImageResId());

        List<String> listItems = item.getListItems();

        // Agrega los elementos de la lista de cosas al LinearLayout dentro del ScrollView
        for (String listItem : listItems) {
            TextView textView = new TextView(context);
            textView.setText(listItem);
            holder.listLinearLayout.addView(textView);
        }*/
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
        TextView tvNumPedido;
        TextView tvNumAlbaran;
        TextView tvFechaPedidoEntregado;
        TextView tvNevera;
        TextView tvComentarios;

        /*TextView tvNumdosis;
        TextView tvLineaGenetica;
        TextView tvFormato;*/

        ImageView ivVerPedidoEntregado;
        ImageView ivRepetirPedidoEntregado;
        ImageView ivFavoritoEntregado;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tvIdPedido = itemView.findViewById(R.id.tvIdPedido);
            tvFechaSolicitud = itemView.findViewById(R.id.tvFechaSolicitud);

            tvNumPedido = itemView.findViewById(R.id.tvNumPedido);
            tvNumAlbaran = itemView.findViewById(R.id.tvNumAlbaran);
            tvFechaPedidoEntregado = itemView.findViewById(R.id.tvFechaPedidoEntregado);
            ivVerPedidoEntregado = itemView.findViewById(R.id.ivVerPedidoEntregado);
            ivRepetirPedidoEntregado = itemView.findViewById(R.id.ivRepetirPedidoEntregado);
            ivFavoritoEntregado = itemView.findViewById(R.id.ivFavoritoEntregado);
            tvNevera = itemView.findViewById(R.id.tvNevera);
            tvComentarios = itemView.findViewById(R.id.tvComentarios);

            /*tvNumdosis = itemView.findViewById(R.id.tvNumdosis);
            tvLineaGenetica = itemView.findViewById(R.id.tvLineaGenetica);
            tvFormato = itemView.findViewById(R.id.tvFormato);*/

            pedidosDB = PedidosDB.getInstance(context);

            ivVerPedidoEntregado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    // Ejemplo: Iniciar una nueva actividad al hacer clic en el TextView
                    int idPed = Integer.parseInt(tvIdPedido.getText().toString());
                    String numPedido = tvNumPedido.getText().toString();
                    String numAlbaran = tvNumAlbaran.getText().toString();
                    String fechaPedido = tvFechaPedidoEntregado.getText().toString();
                    String fechaSolicitud = tvFechaSolicitud.getText().toString();
                    String nevera = tvNevera.getText().toString();
                    String comentarios = tvComentarios.getText().toString();

                    Intent intent = new Intent(context, DetallesPedidoActivity.class);
                    intent.putExtra(KEY_ID, idPed);
                    intent.putExtra(KEY_NUM_PEDIDO, numPedido);
                    intent.putExtra(KEY_NUM_ALBARAN, numAlbaran);
                    intent.putExtra(KEY_FECHA_PEDIDO, fechaPedido);
                    intent.putExtra(KEY_FECHA_ENTREGA, fechaPedido);
                    intent.putExtra(KEY_FECHA_SOLICITUD, fechaSolicitud);
                    intent.putExtra(KEY_NEVERA, nevera);
                    intent.putExtra(KEY_COMENTARIOS, comentarios);
                    context.startActivity(intent);
                    ((AppCompatActivity) view.getContext()).finish();
                }
            });

            ivFavoritoEntregado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int iconoActual = (Integer) ivFavoritoEntregado.getTag();
                    int comparar = R.drawable.ic_favorito;

                    if (iconoActual == comparar)
                    {
                        ivFavoritoEntregado.setImageResource(R.drawable.ic_favorito_activo);
                        ivFavoritoEntregado.setTag(R.drawable.ic_favorito_activo);
                        pedidosDB.insertarPedidoFavorito(new PedidoFavorito(Integer.parseInt(tvIdPedido.getText().toString()), tvFechaSolicitud.getText().toString(), tvNumPedido.getText().toString(), tvNumAlbaran.getText().toString(), tvFechaPedidoEntregado.getText().toString(), pedidoFavorito, context.getString(R.string.terminado), tvNevera.getText().toString(), tvComentarios.getText().toString()));
                    }
                    else
                    {
                        ivFavoritoEntregado.setImageResource(R.drawable.ic_favorito);
                        ivFavoritoEntregado.setTag(R.drawable.ic_favorito);
                        pedidosDB.elimiarPedidoFavorito(Integer.parseInt(tvIdPedido.getText().toString()));
                    }
                }
            });
        }
    }
}
