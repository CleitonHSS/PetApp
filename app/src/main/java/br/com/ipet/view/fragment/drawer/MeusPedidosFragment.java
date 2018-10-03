package br.com.ipet.view.fragment.drawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import br.com.ipet.R;
import br.com.ipet.model.entities.Pedido;
import br.com.ipet.model.repository.PedidoRepository;
import br.com.ipet.view.adapter.PedidoListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MeusPedidosFragment extends Fragment implements MeusPedidosView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    PedidoRepository pedidoRepository = new PedidoRepository(this);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meus_pedidos, container, false);
        ButterKnife.bind(this, view);

        pedidoRepository.getAll();
        return view;
    }

    @Override
    public void onLoadPedidos(List<Pedido> pedidoList) {
        PedidoListAdapter adapter = new PedidoListAdapter(pedidoList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
