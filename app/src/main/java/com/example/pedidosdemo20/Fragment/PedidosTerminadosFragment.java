package com.example.pedidosdemo20.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pedidosdemo20.BasesDatos.PedidosDB;
import com.example.pedidosdemo20.MisPedidosActivity;
import com.example.pedidosdemo20.R;
import com.example.pedidosdemo20.ReciclerView.MyAdapter;
import com.example.pedidosdemo20.ReciclerView.MyAdapterPedEnCurso;
import com.example.pedidosdemo20.ReciclerView.MyList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class PedidosTerminadosFragment extends Fragment
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
    public PedidosTerminadosFragment()
    {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PedidosTerminadosFragment newInstance(int columnCount)
    {
        PedidosTerminadosFragment fragment = new PedidosTerminadosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pedidos_terminados_list, container, false);

        pedidosDB = PedidosDB.getInstance(getContext());

        if (view instanceof RecyclerView)
        {
            int size = MisPedidosActivity.alId.size();

            RecyclerView rwPedidosEntregados = (RecyclerView) view;
            rwPedidosEntregados.setHasFixedSize(true);
            rwPedidosEntregados.setLayoutManager(new LinearLayoutManager(getContext()));
            List<MyList> arList = new ArrayList<>();
            for (int i = 0; i < size; i++)
            {
                //List<String> listaProdutco = Arrays.asList("", "alLinea.get(j)", "alFormato.get(j)");
                int pedFav = pedidosDB.strPedidoFav(MisPedidosActivity.alId.get(i));
                if (pedFav != 0)
                {
                    arList.add(new MyList(MisPedidosActivity.alId.get(i), MisPedidosActivity.alFechaSolicitud.get(i), MisPedidosActivity.alNumPedidoBSM.get(i), "A000016", MisPedidosActivity.alFechaEntrega.get(i), MyAdapter.pedidoFavorito, MisPedidosActivity.alNevera.get(i), MisPedidosActivity.alComentarios.get(i)));
                }
                else {
                    arList.add(new MyList(MisPedidosActivity.alId.get(i), MisPedidosActivity.alFechaSolicitud.get(i), MisPedidosActivity.alNumPedidoBSM.get(i), "A000016", MisPedidosActivity.alFechaEntrega.get(i), MyAdapter.pedidoNormal, MisPedidosActivity.alNevera.get(i), MisPedidosActivity.alComentarios.get(i)));
                }

                MyAdapter adpater = new MyAdapter(getContext(), arList);
                rwPedidosEntregados.setAdapter(adpater);
            }
        }
        return view;
    }
}