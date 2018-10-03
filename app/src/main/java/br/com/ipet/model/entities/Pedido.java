package br.com.ipet.model.entities;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    public String id;
    public DocumentReference usuarioRef;
    public CollectionReference produtoListRef;
    public CollectionReference serviceListRef;

    public Usuario usuario;
    public List<Produto> produtoList = new ArrayList<>();
    public List<Servico> servicoList = new ArrayList<>();

    public int quantidadeProduto() {
        return produtoList.size();
    }

    public int quantidadeServico() {
        return servicoList.size();
    }

    public int valorTotalProduto() {
        int total = 0;
        for (Produto produto : produtoList) {
            total += produto.preco;
        }

        return total;
    }

    public int valorTotalServico() {
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
}
