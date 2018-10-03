package br.com.ipet.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ipet.R;
import br.com.ipet.model.entities.Pedido;

public class PedidoListAdapter extends RecyclerView.Adapter<PedidoListAdapter.PedidoListHolder> {

    private List<Pedido> pedidoList;

    public PedidoListAdapter(List<Pedido> pedidoList) {
        this.pedidoList = pedidoList;
    }

    @NonNull
    @Override
    public PedidoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.card_pedido;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new PedidoListHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoListHolder holder, int position) {
        if (pedidoList != null && position < pedidoList.size()) {
            Pedido pedido = pedidoList.get(position);
            holder.pedidoProdutoQuantidade.setText("" + pedido.quantidadeProduto());
            holder.pedidoProdutoValor.setText("R$ " + pedido.valorTotalProduto());
            holder.pedidoServicoQuantidade.setText("" + pedido.quantidadeServico());
            holder.pedidoServicoValor.setText("R$ " + pedido.valorTotalServico());
        }
    }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }

    class PedidoListHolder extends RecyclerView.ViewHolder {

        TextView pedidoProdutoQuantidade;
        TextView pedidoProdutoValor;
        TextView pedidoServicoQuantidade;
        TextView pedidoServicoValor;

        PedidoListHolder(@NonNull View itemView) {
            super(itemView);
            pedidoProdutoQuantidade = itemView.findViewById(R.id.pedido_produto_quantidade);
            pedidoProdutoValor = itemView.findViewById(R.id.pedido_produto_valor);
            pedidoServicoQuantidade = itemView.findViewById(R.id.pedido_servico_quantidade);
            pedidoServicoValor = itemView.findViewById(R.id.pedido_servico_valor);
        }
    }
}
