package br.com.ipet.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import br.com.ipet.R;
import br.com.ipet.model.entities.Pedido;

public class PedidoListAdapter extends RecyclerView.Adapter<PedidoListAdapter.PedidoListHolder> {

    private List<Pedido> pedidoList;
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");

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

            holder.pedidoData.setText(dateFormat.format(pedido.data.toDate()));
            holder.pedidoProdutoQuantidade.setText("Produto: " + pedido.getQuantidadeProduto());
            holder.pedidoProdutoValor.setText(numberFormat.format(pedido.getValorTotalProduto()));
            holder.pedidoServicoQuantidade.setText("Serviço: " + pedido.getQuantidadeServico());
            holder.pedidoServicoValor.setText(numberFormat.format(pedido.getValorTotalServico()));
        }
    }

    @Override
    public int getItemCount() {
        return pedidoList.size();
    }

    class PedidoListHolder extends RecyclerView.ViewHolder {

        TextView pedidoData;
        TextView pedidoProdutoQuantidade;
        TextView pedidoProdutoValor;
        TextView pedidoServicoQuantidade;
        TextView pedidoServicoValor;

        PedidoListHolder(@NonNull View itemView) {
            super(itemView);
            pedidoData = itemView.findViewById(R.id.pedido_data);
            pedidoProdutoQuantidade = itemView.findViewById(R.id.pedido_produto_quantidade);
            pedidoProdutoValor = itemView.findViewById(R.id.pedido_produto_valor);
            pedidoServicoQuantidade = itemView.findViewById(R.id.pedido_servico_quantidade);
            pedidoServicoValor = itemView.findViewById(R.id.pedido_servico_valor);
        }
    }
}
