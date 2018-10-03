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
import br.com.ipet.model.entities.Servico;

public class ServicoListAdapter extends RecyclerView.Adapter<ServicoListAdapter.ServicoListHolder> {

    private List<Servico> servicoList;
    private ImgRequester imgRequester;

    public ServicoListAdapter(List<Servico> servicoList) {
        this.servicoList = servicoList;
        imgRequester = ImgRequester.getInstance();
    }

    @NonNull
    @Override
    public ServicoListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = R.layout.card_servico;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ServicoListHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicoListHolder holder, int position) {
        if (servicoList != null && position < servicoList.size()) {
            Servico servico = servicoList.get(position);
            holder.servicoTitulo.setText(servico.titulo);
            holder.servicoPreco.setText("R$" + servico.preco);
            imgRequester.setImageFromUrl(holder.servicoImagem, servico.url);
        }
    }

    @Override
    public int getItemCount() {
        return servicoList.size();
    }

    class ServicoListHolder extends RecyclerView.ViewHolder {

        NetworkImageView servicoImagem;
        TextView servicoTitulo;
        TextView servicoPreco;

        ServicoListHolder(@NonNull View itemView) {
            super(itemView);
            servicoImagem = itemView.findViewById(R.id.servico_imagem);
            servicoTitulo = itemView.findViewById(R.id.servico_titulo);
            servicoPreco = itemView.findViewById(R.id.servico_preco);
        }
    }
}
