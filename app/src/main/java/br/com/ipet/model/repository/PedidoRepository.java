package br.com.ipet.model.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.ipet.IPetApplication;
import br.com.ipet.model.entities.Pedido;
import br.com.ipet.view.fragment.TabView;
import br.com.ipet.view.fragment.drawer.MeusPedidosView;
import timber.log.Timber;

public class PedidoRepository extends BaseRepository {

    private MeusPedidosView meusPedidosView;
    private TabView tabView;
    public static final String COLLECTION_PATH = "pedidos";

    public PedidoRepository(MeusPedidosView meusPedidosView) { // XGH por não ter usado arquitetura MVP
        this.meusPedidosView = meusPedidosView;
    }

    public PedidoRepository(TabView tabView){ // XGH por não ter usado arquitetura MVP
        this.tabView = tabView;
    }

    public void getAll() {
        getDatabase().collection(COLLECTION_PATH).whereEqualTo(FieldPath.of("usuarioId"), IPetApplication.usuarioLogado.getUid()) // TODO: Filtrar por usuário logado
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) { return; }

                        List<Pedido> pedidoList = new ArrayList<>();
                        for (DocumentSnapshot pedidoSnapshot : task.getResult()) {
                            final Pedido pedido = pedidoSnapshot.toObject(Pedido.class);
                            pedido.id = pedidoSnapshot.getId();
                            pedidoList.add(pedido);
                        }

                        meusPedidosView.onLoadPedidos(pedidoList);
                    }
                });
    }

    public void add(Pedido pedido) {

        getDatabase().collection(COLLECTION_PATH)
                .add(pedido)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Timber.d("Pedido adicionado com ID: %s", documentReference.getId());
                        tabView.onAddPedido();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Timber.e(e, "Erro ao adicionar pedido");
                    }
                });
    }
}