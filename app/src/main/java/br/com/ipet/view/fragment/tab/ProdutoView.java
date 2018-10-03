package br.com.ipet.view.fragment.tab;

import java.util.List;

import br.com.ipet.model.entities.Produto;

public interface ProdutoView {
    void onLoadProdutos(List<Produto> produtoList);
}
