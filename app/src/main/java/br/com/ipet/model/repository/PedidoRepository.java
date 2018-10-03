package br.com.ipet.model.repository;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.ipet.model.entities.Pedido;
import br.com.ipet.model.entities.Produto;
import br.com.ipet.model.entities.Servico;
import br.com.ipet.model.entities.Usuario;
import br.com.ipet.view.fragment.drawer.MeusPedidosView;
import timber.log.Timber;

public class PedidoRepository extends BaseRepository {

    private MeusPedidosView view;
    public static final String COLLECTION_PATH = "pedidos";

    public PedidoRepository(MeusPedidosView view) {
        this.view = view;
    }

    public void getAll() {
        getDatabase().collection(COLLECTION_PATH) // TODO: Filtrar por usuário logado
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful()) { return; }

                        List<Pedido> pedidoList = new ArrayList<>();
                        for (DocumentSnapshot pedidoSnapshot : task.getResult()) {
                            final Pedido pedido = pedidoSnapshot.toObject(Pedido.class);
                            pedido.id = pedidoSnapshot.getId();
                            pedido.usuarioRef
                                    .get()
                                    .addOnCompleteListener(
                                            new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (!task.isSuccessful()) { return; }
                                                    DocumentSnapshot usuarioSnapshot = task.getResult();
                                                    pedido.usuario = usuarioSnapshot.toObject(Usuario.class);
                                                    pedido.usuario.id = usuarioSnapshot.getId();
                                                }
                                            }
                                    );
                            pedidoSnapshot.getReference().collection(ProdutoRepository.COLLECTION_PATH)
                                    .get()
                                    .addOnCompleteListener(
                                            new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (!task.isSuccessful()) { return; }

                                                    for (DocumentSnapshot produtoSnapshot : task.getResult()) {
                                                        if (produtoSnapshot.exists()) {
                                                            Produto produto = produtoSnapshot.toObject(Produto.class);
                                                            produto.id = produtoSnapshot.getId();
                                                            pedido.produtoList.add(produto);
                                                        }
                                                    }
                                                }
                                            }
                                    );
                            pedidoSnapshot.getReference().collection(ServicoRepository.COLLECTION_PATH)
                                    .get()
                                    .addOnCompleteListener(
                                            new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (!task.isSuccessful()) { return; }

                                                    for (DocumentSnapshot servicoSnapshot : task.getResult()) {
                                                        if (servicoSnapshot.exists()) {
                                                            Servico servico = servicoSnapshot.toObject(Servico.class);
                                                            servico.id = servicoSnapshot.getId();
                                                            pedido.servicoList.add(servico);
                                                        }
                                                    }
                                                }
                                            }
                                    );
                            pedidoList.add(pedido);
                        }

                        view.onLoadPedidos(pedidoList);
                    }
                });
    }

    private void add(Pedido pedido) {

        getDatabase().collection(COLLECTION_PATH)
                .add(pedido)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Timber.d("Pedido adicionado com ID: %s", documentReference.getId());
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