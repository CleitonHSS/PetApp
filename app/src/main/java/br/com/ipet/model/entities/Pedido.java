package br.com.ipet.model.entities;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

@IgnoreExtraProperties
public class Pedido {
    @Exclude
    public String id;
    public String usuarioId;
    public Timestamp data;

    public List<Produto> produtoList = new ArrayList<>();
    public List<Servico> servicoList = new ArrayList<>();

    public int getQuantidadeProduto() {
        return produtoList.size();
    }

    public int getQuantidadeServico() {
        return servicoList.size();
    }

    public int getValorTotalProduto() {
        int total = 0;
        for (Produto produto : produtoList) {
            total += produto.preco;
        }

        return total;
    }

    public int getValorTotalServico() {
        int total = 0;
        for (Servico servico : servicoList) {
            total += servico.preco;
        }

        return total;
    }

    public void adicionarProduto(Produto produto) {
        produtoList.add(produto);
    }

    public void adicionarServico(Servico servico) {
        servicoList.add(servico);
    }

    public void removerProduto(Produto produto) {
        produtoList.remove(produto);
    }

    public void removerServico(Servico servico) {
        servicoList.remove(servico);
    }

    public boolean isEmpty() {
        return (getQuantidadeProduto() + getQuantidadeServico()) == 0;
    }
}
