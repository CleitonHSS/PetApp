package br.com.ipet.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.ipet.R;
import br.com.ipet.infrastructure.requesters.ImageRequester;
import br.com.ipet.model.entities.Servico;
import br.com.ipet.view.fragment.tab.ServicoView;

public class ServicoListAdapter extends RecyclerView.Adapter<ServicoListAdapter.ServicoListHolder> {

    private final ServicoView servicoView;
    private final List<Servico> servicoList;
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final ImageRequester imageRequester;

    public ServicoListAdapter(List<Servico> servicoList, ServicoView servicoView) {
        this.servicoList = servicoList;
        this.servicoView = servicoView;
        imageRequester = ImageRequester.getInstance();
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
            holder.servicoPreco.setText(numberFormat.format(servico.preco));
            imageRequester.setImageFromUrl(holder.servicoImagem, servico.url);
        }
    }

    @Override
    public int getItemCount() {
        return servicoList.size();
    }

    class ServicoListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        NetworkImageView servicoImagem;
        TextView servicoTitulo;
        TextView servicoPreco;

        ServicoListHolder(View itemView) {
            super(itemView);
            servicoImagem = itemView.findViewById(R.id.servico_imagem);
            servicoTitulo = itemView.findViewById(R.id.servico_titulo);
            servicoPreco = itemView.findViewById(R.id.servico_preco);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            servicoView.onServicoItemClick(servicoList.get(getAdapterPosition()));
        }
    }
}
