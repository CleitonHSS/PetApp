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
import br.com.ipet.model.entities.Produto;

public class CarrinhoProdutoListAdapter extends RecyclerView.Adapter<CarrinhoProdutoListAdapter.ProdutoListHolder> {

    private List<Produto> produtoList;
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public CarrinhoProdutoListAdapter(List<Produto> produtoList) {
        this.produtoList = produtoList;
    }

    @NonNull
    @Override
    public ProdutoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.card_carrinho_produto;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ProdutoListHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoListHolder holder, int position) {
        if (produtoList != null && position < produtoList.size()) {
            Produto produto = produtoList.get(position);
            // TODO: Agrupar por produto e colocar a quantidade correta
            holder.produtoTitulo.setText("1 - " + produto.titulo);
            holder.produtoPreco.setText(numberFormat.format(produto.preco));
        }
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    class ProdutoListHolder extends RecyclerView.ViewHolder {

        TextView produtoTitulo;
        TextView produtoPreco;

        ProdutoListHolder(@NonNull View itemView) {
            super(itemView);
            produtoTitulo = itemView.findViewById(R.id.carrinho_produto_titulo);
            produtoPreco = itemView.findViewById(R.id.carrinho_produto_preco);
        }
    }
}
