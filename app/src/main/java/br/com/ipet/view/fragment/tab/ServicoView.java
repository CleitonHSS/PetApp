package br.com.ipet.view.fragment.tab;

import java.util.List;

import br.com.ipet.model.entities.Servico;

public interface ServicoView {
    void onLoadServicos(List<Servico> servicoList);
}
