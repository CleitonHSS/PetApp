package br.com.ipet.view.fragment.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.ipet.R;
import br.com.ipet.infrastructure.animation.AnimationUtil;
import br.com.ipet.infrastructure.network.retrofit.RetrofitClientInstance;
import br.com.ipet.infrastructure.network.retrofit.service.InfnetService;
import br.com.ipet.model.entities.InfnetTarefa;
import br.com.ipet.model.entities.Produto;
import br.com.ipet.model.repository.ProdutoRepository;
import br.com.ipet.view.adapter.ProdutoListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoFragment extends Fragment implements ProdutoView {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.produto_scroll_view)
    NestedScrollView scrollView;

    RelativeLayout footerCarrinho;
    TextView buttonVerCarrinho;
    BottomSheetDialog bottomSheetDialog;
    ProdutoRepository produtoRepository = new ProdutoRepository(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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

        footerCarrinho = getActivity().findViewById(R.id.footer_carrinho);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    AnimationUtil.slideDown(footerCarrinho);
                }
                if (scrollY < oldScrollY) {
                    AnimationUtil.slideUp(footerCarrinho);
                }
            }
        });

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.fragment_carrinho, null);
        bottomSheetDialog.setContentView(sheetView);

        buttonVerCarrinho = getActivity().findViewById(R.id.carrinho_ver_carrinho);
        buttonVerCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
            }
        });

//        consumirApiInfnet();
    }

    @Override
    public void onLoadProdutos(List<Produto> produtoList) {
        ProdutoListAdapter adapter = new ProdutoListAdapter(produtoList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void consumirApiInfnet() {
        // TODO: Exemplo de consumo de api com o retrofit. Remover quando usar numa api do app
        InfnetService service = RetrofitClientInstance.getInstance().create(InfnetService.class);
        Call<List<InfnetTarefa>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<InfnetTarefa>>() {
            @Override
            public void onResponse(Call<List<InfnetTarefa>> call, Response<List<InfnetTarefa>> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<List<InfnetTarefa>> call, Throwable t) {
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
