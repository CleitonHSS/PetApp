package br.com.ipet.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.ipet.IPetApplication;
import br.com.ipet.R;
import br.com.ipet.model.entities.Pedido;
import br.com.ipet.model.repository.PedidoRepository;
import br.com.ipet.view.adapter.CarrinhoProdutoListAdapter;
import br.com.ipet.view.adapter.CarrinhoServicoListAdapter;
import br.com.ipet.view.fragment.tab.ProdutoFragment;
import br.com.ipet.view.fragment.tab.ServicoFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TabFragment extends Fragment implements TabView {

    @BindView(R.id.tabs)
    public TabLayout tabLayout;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;
    @BindView(R.id.bottom_sheet_carrinho)
    public RelativeLayout layoutBottomSheet;
    @BindView(R.id.button_finalizar_compra)
    public MaterialButton buttonFinalizarCompra;
    @BindView(R.id.carrinho_produto_recycler_view)
    public RecyclerView produtoRecyclerView;
    @BindView(R.id.carrinho_servico_recycler_view)
    public RecyclerView servicoRecyclerView;
    @BindView(R.id.footer_carrinho)
    public RelativeLayout footerCarrinho;
    @BindView(R.id.carrinho_quantidade)
    public TextView carrinhoQuantidade;
    @BindView(R.id.carrinho_valor)
    public TextView carrinhoValor;

    public BottomSheetBehavior sheetBehavior;

    CarrinhoProdutoListAdapter carrinhoProdutoAdapter;
    CarrinhoServicoListAdapter carrinhoServicoAdapter;

    private final PedidoRepository pedidoRepository = new PedidoRepository(this);
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        viewPager.setAdapter(new MenuFragmentAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupBottomSheetCarrinho();
    }

    @Override
    public void onAddPedido() {
        IPetApplication.carrinho.limparCarrinho();
        Toast.makeText(getActivity(), "Compra finalizada com sucesso", Toast.LENGTH_SHORT).show();

        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @OnClick(R.id.footer_carrinho)
    public void onClickCarrinho() {
        switch (sheetBehavior.getState()) {
            case BottomSheetBehavior.STATE_COLLAPSED:
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case BottomSheetBehavior.STATE_EXPANDED:
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    private void setupBottomSheetCarrinho() {
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        buttonFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();

                Pedido pedido = IPetApplication.carrinho.pedido;
                pedido.usuarioId = IPetApplication.usuarioLogado.getUid();
                pedido.data = new Timestamp(currentTime);
                pedidoRepository.add(pedido);
            }
        });

        carrinhoProdutoAdapter = new CarrinhoProdutoListAdapter(IPetApplication.carrinho.pedido.produtoList);
        produtoRecyclerView.setHasFixedSize(true);
        produtoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        produtoRecyclerView.setAdapter(carrinhoProdutoAdapter);

        carrinhoServicoAdapter = new CarrinhoServicoListAdapter(IPetApplication.carrinho.pedido.servicoList);
        servicoRecyclerView.setHasFixedSize(true);
        servicoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        servicoRecyclerView.setAdapter(carrinhoServicoAdapter);
    }

    public void hideFooter() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void showFooter() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public void atualizarCarrinho() {
        Pedido pedido = IPetApplication.carrinho.pedido;
        carrinhoQuantidade.setText("" + (pedido.getQuantidadeProduto() + pedido.getQuantidadeServico()));
        carrinhoValor.setText(numberFormat.format(pedido.getValorTotalProduto() + pedido.getValorTotalServico()));

        carrinhoProdutoAdapter = new CarrinhoProdutoListAdapter(IPetApplication.carrinho.pedido.produtoList);
        produtoRecyclerView.setAdapter(carrinhoProdutoAdapter);
        carrinhoServicoAdapter = new CarrinhoServicoListAdapter(IPetApplication.carrinho.pedido.servicoList);
        servicoRecyclerView.setAdapter(carrinhoServicoAdapter);

        showFooter();
    }

    public class MenuFragmentAdapter extends FragmentPagerAdapter {

        MenuFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ProdutoFragment();
                case 1:
                    return new ServicoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Produtos";
                case 1:
                    return "Serviços";
            }
            return null;
        }
    }
}
