package br.com.ipet.view.fragment.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import br.com.ipet.IPetApplication;
import br.com.ipet.R;
import br.com.ipet.infrastructure.requesters.ImageRequester;
import br.com.ipet.model.entities.Produto;
import br.com.ipet.model.repository.ProdutoRepository;
import br.com.ipet.view.activity.MenuActivity;
import br.com.ipet.view.adapter.ProdutoListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProdutoFragment extends Fragment implements ProdutoView {

    @BindView(R.id.produto_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.produto_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.produto_scroll_view)
    NestedScrollView scrollView;

    BottomSheetDialog bottomSheetDialog;
    MaterialButton buttonAdicionarAoCarrinho;

    private MenuActivity menuActivity;
    private final ProdutoRepository produtoRepository = new ProdutoRepository(this);
    private final ImageRequester imageRequester = ImageRequester.getInstance();
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        menuActivity = ((MenuActivity) getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produto, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        produtoRepository.getAll();

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY && !IPetApplication.carrinho.pedido.isEmpty()) {
                    menuActivity.hideTabFragmentFooter();
                }
                if (scrollY < oldScrollY && !IPetApplication.carrinho.pedido.isEmpty()) {
                    menuActivity.showTabFragmentFooter();
                }
            }
        });
    }

    @Override
    public void onLoadProdutos(List<Produto> produtoList) {
        ProdutoListAdapter adapter = new ProdutoListAdapter(produtoList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProdutoItemClick(final Produto produto) {
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_produto, null);

        buttonAdicionarAoCarrinho = sheetView.findViewById(R.id.button_adicionar_ao_carrinho);
        buttonAdicionarAoCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Produto adicionado com sucesso", Toast.LENGTH_SHORT).show();
                IPetApplication.carrinho.pedido.adicionarProduto(produto);
                menuActivity.atualizarCarrinho();
                bottomSheetDialog.hide();
            }
        });
        NetworkImageView produtoImagem = sheetView.findViewById(R.id.produto_imagem);
        TextView produtoTitulo = sheetView.findViewById(R.id.produto_titulo);
        TextView produtoPreco = sheetView.findViewById(R.id.produto_preco);
        TextView produtoDescricao = sheetView.findViewById(R.id.produto_descricao);

        imageRequester.setImageFromUrl(produtoImagem, produto.url);
        produtoTitulo.setText(produto.titulo);
        produtoPreco.setText(numberFormat.format(produto.preco));
        produtoDescricao.setText(""); // TODO: adicionar descrição ao produto

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }
}
