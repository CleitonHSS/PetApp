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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.ipet.IPetApplication;
import br.com.ipet.R;
import br.com.ipet.infrastructure.requesters.ImageRequester;
import br.com.ipet.model.entities.Servico;
import br.com.ipet.model.repository.ServicoRepository;
import br.com.ipet.view.activity.MenuActivity;
import br.com.ipet.view.adapter.ServicoListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicoFragment extends Fragment implements ServicoView, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.servico_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.servico_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.servico_scroll_view)
    NestedScrollView scrollView;

    BottomSheetDialog bottomSheetDialog;
    MaterialButton buttonAdicionarAoCarrinho;
    MaterialButton buttonAgendar;

    private MenuActivity menuActivity;
    private DatePickerDialog datePickerDialog;
    private final ServicoRepository servicoRepository = new ServicoRepository(this);
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
        View view = inflater.inflate(R.layout.fragment_servico, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        servicoRepository.getAll();

        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY && !IPetApplication.carrinho.pedido.isEmpty()) {
                    menuActivity.hideTabFragmentFooter();
                }
                if (scrollY < oldScrollY && !IPetApplication.carrinho.pedido.isEmpty()){
                    menuActivity.showTabFragmentFooter();
                }
            }
        });

        setupDatepickerDialog();
    }

    private void setupDatepickerDialog(){
        Calendar now = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);
        datePickerDialog.setOkColor("white");
        datePickerDialog.setCancelColor("white");
        datePickerDialog.setLocale(new Locale("pt", "BR"));
    }

    @Override
    public void onLoadServicos(List<Servico> servicoList) {
        ServicoListAdapter adapter = new ServicoListAdapter(servicoList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onServicoItemClick(final Servico servico) {
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet_servico, null);

        buttonAdicionarAoCarrinho = sheetView.findViewById(R.id.button_adicionar_ao_carrinho);
        buttonAdicionarAoCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Serviço adicionado com sucesso", Toast.LENGTH_SHORT).show();
                IPetApplication.carrinho.pedido.adicionarServico(servico);
                menuActivity.atualizarCarrinho();
                bottomSheetDialog.hide();
            }
        });
        buttonAgendar = sheetView.findViewById(R.id.button_agendar);
        buttonAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
            }
        });
        NetworkImageView servicoImagem = sheetView.findViewById(R.id.servico_imagem);
        TextView servicoTitulo = sheetView.findViewById(R.id.servico_titulo);
        TextView servicoPreco = sheetView.findViewById(R.id.servico_preco);
        TextView servicoDescricao = sheetView.findViewById(R.id.servico_descricao);

        imageRequester.setImageFromUrl(servicoImagem, servico.url);
        servicoTitulo.setText(servico.titulo);
        servicoPreco.setText(numberFormat.format(servico.preco));
        servicoDescricao.setText(""); // TODO: adicionar descrição ao serviço

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}
