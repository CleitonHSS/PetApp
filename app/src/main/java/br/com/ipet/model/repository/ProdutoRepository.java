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

import br.com.ipet.model.entities.Produto;
import br.com.ipet.view.fragment.tab.ProdutoView;
import timber.log.Timber;

public class ProdutoRepository extends BaseRepository {

    private ProdutoView view;
    public static final String COLLECTION_PATH = "produtos";

    public ProdutoRepository(ProdutoView view) {
        this.view = view;
    }

    public void getAll() {
        getDatabase().collection(COLLECTION_PATH)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Produto> produtoList = new ArrayList<>();
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                Produto produto = snapshot.toObject(Produto.class);
                                produto.id = snapshot.getId();
                                produtoList.add(produto);
                            }

                            view.onLoadProdutos(produtoList);
                        } else {
                            Timber.e(task.getException(), "Erro ao tentar obter produtos do servidor.");
                        }
                    }
                });
    }

    private void add(Produto produto) {

        getDatabase().collection(COLLECTION_PATH)
                .add(produto)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Timber.d("Produto adicionado com ID: %s", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Timber.e(e, "Erro adicionando produto");
                    }
                });
    }
}