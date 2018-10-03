package br.com.ipet.view.fragment.drawer;

import java.util.List;

import br.com.ipet.model.entities.Pedido;

public interface MeusPedidosView {
    void onLoadPedidos(List<Pedido> pedidoList);
}
