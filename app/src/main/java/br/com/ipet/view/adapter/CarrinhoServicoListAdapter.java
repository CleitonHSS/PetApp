package br.com.ipet.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.ipet.R;
import br.com.ipet.model.entities.Servico;

public class CarrinhoServicoListAdapter extends RecyclerView.Adapter<CarrinhoServicoListAdapter.ServicoListHolder> {

    private List<Servico> servicoList;
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public CarrinhoServicoListAdapter(List<Servico> servicoList) {
        this.servicoList = servicoList;
    }

    @NonNull
    @Override
    public ServicoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.card_carrinho_servico;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ServicoListHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicoListHolder holder, int position) {
        if (servicoList != null && position < servicoList.size()) {
            Servico servico = servicoList.get(position);
            // TODO: Agrupar por serviço e colocar a quantidade correta
            holder.servicoTitulo.setText("1 - " + servico.titulo);
            holder.servicoPreco.setText(numberFormat.format(servico.preco));
        }
    }

    @Override
    public int getItemCount() {
        return servicoList.size();
    }

    class ServicoListHolder extends RecyclerView.ViewHolder {

        TextView servicoTitulo;
        TextView servicoPreco;

        ServicoListHolder(@NonNull View itemView) {
            super(itemView);
            servicoTitulo = itemView.findViewById(R.id.carrinho_servico_titulo);
            servicoPreco = itemView.findViewById(R.id.carrinho_servico_preco);
        }
    }
}
