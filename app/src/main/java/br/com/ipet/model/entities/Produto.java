package br.com.ipet.model.entities;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Produto {
    @Exclude
    public String id;
    public String titulo;
    public String url;
    public float preco;
}
