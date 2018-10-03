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

import br.com.ipet.model.entities.Servico;
import br.com.ipet.view.fragment.tab.ServicoView;
import timber.log.Timber;

public class ServicoRepository extends BaseRepository {

    private ServicoView view;
    public static final String COLLECTION_PATH = "serviços";

    public ServicoRepository(ServicoView view) {
        this.view = view;
    }

    public void getAll() {
        getDatabase().collection(COLLECTION_PATH)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Servico> servicoList = new ArrayList<>();
                            for (DocumentSnapshot snapshot : task.getResult()) {
                                Servico servico = snapshot.toObject(Servico.class);
                                servico.id = snapshot.getId();
                                servicoList.add(servico);
                            }

                            view.onLoadServicos(servicoList);
                        } else {
                            Timber.e(task.getException(), "Erro ao tentar obter serviços do servidor.");
                        }
                    }
                });
    }

    private void add(Servico servico) {

        getDatabase().collection(COLLECTION_PATH)
                .add(servico)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Timber.d("Serviço adicionado com ID: %s", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Timber.e(e, "Erro adicionando serviço");
                    }
                });
    }
}