package br.com.ipet.model.entities;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Servico {
    @Exclude
    public String id;
    public String titulo;
    public String url;
    public float preco;
    public Timestamp dataAgendada;
}
