package br.com.ipet.model.entities;

public class Carrinho {
    public Pedido pedido = new Pedido();

    public void limparCarrinho() {
        pedido = new Pedido();
    }

    public void adicionarProduto(Produto produto){
        pedido.adicionarProduto(produto);
    }

    public void adicionarServico(Servico servico){
        pedido.adicionarServico(servico);
    }

    public void removerProduto(Produto produto){
       pedido.removerProduto(produto);
    }

    public void removerServico(Servico servico){
        pedido.removerServico(servico);
    }
}
