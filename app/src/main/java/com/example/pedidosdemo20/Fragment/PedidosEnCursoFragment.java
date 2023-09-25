package com.example.pedidosdemo20.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pedidosdemo20.BasesDatos.PedidosDB;
import com.example.pedidosdemo20.MisPedidosActivity;
import com.example.pedidosdemo20.R;
import com.example.pedidosdemo20.ReciclerView.MyAdapter;
import com.example.pedidosdemo20.ReciclerView.MyAdapterPedEnCurso;
import com.example.pedidosdemo20.ReciclerView.MyList;
import com.example.pedidosdemo20.ReciclerView.MyListEnCurso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class PedidosEnCursoFragment extends Fragment
{
    private PedidosDB pedidosDB;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PedidosEnCursoFragment()
    {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PedidosEnCursoFragment newInstance(int columnCount)
    {
        PedidosEnCursoFragment fragment = new PedidosEnCursoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pedidos_en_curso_list, container, false);

        pedidosDB = PedidosDB.getInstance(getContext());

        if (view instanceof RecyclerView)
        {
            int size = MisPedidosActivity.alId.size();

            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            List<MyListEnCurso> arList = new ArrayList();
            for (int i = 0; i < size; i++)
            {
                //List<String> listaProdutco = Arrays.asList("", "alLinea.get(j)", "alFormato.get(j)");
                int pedFav = pedidosDB.strPedidoFav(MisPedidosActivity.alId.get(i));
                if (pedFav != 0)
                {
                    arList.add(new MyListEnCurso(MisPedidosActivity.alId.get(i), MisPedidosActivity.alFechaSolicitud.get(i), MisPedidosActivity.alNumPedidoBSM.get(i), "A000016", MisPedidosActivity.alFechaEntrega.get(i), MyAdapter.pedidoFavorito, MisPedidosActivity.alEstado.get(i), MisPedidosActivity.alNevera.get(i), MisPedidosActivity.alComentarios.get(i), MisPedidosActivity.alNumero.get(i), MisPedidosActivity.alFormato.get(i), MisPedidosActivity.alLinea.get(i)));
                }
                else {
                    arList.add(new MyListEnCurso(MisPedidosActivity.alId.get(i), MisPedidosActivity.alFechaSolicitud.get(i), MisPedidosActivity.alNumPedidoBSM.get(i), "A000016", MisPedidosActivity.alFechaEntrega.get(i), MyAdapter.pedidoNormal, MisPedidosActivity.alEstado.get(i), MisPedidosActivity.alNevera.get(i), MisPedidosActivity.alComentarios.get(i), MisPedidosActivity.alNumero.get(i), MisPedidosActivity.alFormato.get(i), MisPedidosActivity.alLinea.get(i)));
                }

                MyAdapterPedEnCurso adpater = new MyAdapterPedEnCurso(getContext(), arList);
                recyclerView.setAdapter(adpater);
            }
        }
        return view;
    }
}