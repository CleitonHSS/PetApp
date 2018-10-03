package br.com.ipet.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import br.com.ipet.R;
import br.com.ipet.infrastructure.requesters.ImgRequester;
import br.com.ipet.model.entities.Produto;

public class ProdutoListAdapter extends RecyclerView.Adapter<ProdutoListAdapter.ProdutoListHolder> {

    private List<Produto> produtoList;
    private ImgRequester imgRequester;

    public ProdutoListAdapter(List<Produto> produtoList) {
        this.produtoList = produtoList;
        imgRequester = ImgRequester.getInstance();
    }

    @NonNull
    @Override
    public ProdutoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.card_produto;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ProdutoListHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoListHolder holder, int position) {
        if (produtoList != null && position < produtoList.size()) {
            Produto produto = produtoList.get(position);
            holder.productTitle.setText(produto.titulo);
            holder.productPrice.setText("R$" + produto.preco);
            imgRequester.setImageFromUrl(holder.productImage, produto.url);
        }
    }

    @Override
    public int getItemCount() {
        return produtoList.size();
    }

    class ProdutoListHolder extends RecyclerView.ViewHolder {

        NetworkImageView productImage;
        TextView productTitle;
        TextView productPrice;

        ProdutoListHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
