package br.com.ipet.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.text.NumberFormat;
import java.util.List;

import br.com.ipet.R;
import br.com.ipet.infrastructure.requesters.ImageRequester;
import br.com.ipet.model.entities.Produto;
import br.com.ipet.view.fragment.tab.ProdutoFragment;

public class ProdutoListAdapter extends RecyclerView.Adapter<ProdutoListAdapter.ProdutoListHolder> {

    private List<Produto> produtoList;
    NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
    private ImageRequester imageRequester;
    ProdutoFragment produtoFragment;

    public ProdutoListAdapter(List<Produto> produtoList, ProdutoFragment produtoFragment) {
        this.produtoFragment = produtoFragment;
        this.produtoList = produtoList;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public ProdutoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.card_produto;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ProdutoListHolder(layoutView, produtoFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoListHolder holder, int position) {
        if (produtoList != null && position < produtoList.size()) {
            Produto produto = produtoList.get(position);
            holder.produtoTitulo.setText(produto.titulo);
            holder.produtoPreco.setText(numberFormat.format(produto.preco));
            imageRequester.setImageFromUrl(holder.produtoImagem, produto.url);
        }
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    class ProdutoListHolder extends RecyclerView.ViewHolder {

        NetworkImageView produtoImagem;
        TextView produtoTitulo;
        TextView produtoPreco;
        CardView cardView;
        ProdutoFragment produtoFragment;

        ProdutoListHolder(@NonNull View itemView,ProdutoFragment produtoFragment) {
            super(itemView);
            produtoImagem = itemView.findViewById(R.id.produto_imagem);
            produtoTitulo = itemView.findViewById(R.id.produto_titulo);
            produtoPreco = itemView.findViewById(R.id.produto_preco);
            cardView = itemView.findViewById(R.id.card_view);
            this.produtoFragment = produtoFragment;
            itemView.setOnClickListener(produtoFragment);//(produtoFragment);
        }
    }
}
