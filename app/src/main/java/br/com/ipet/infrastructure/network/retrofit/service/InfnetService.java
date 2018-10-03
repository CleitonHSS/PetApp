package br.com.ipet.infrastructure.network.retrofit.service;

import java.util.List;

import br.com.ipet.model.entities.InfnetTarefa;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InfnetService {

    @GET("/photos")
    Call<List<InfnetTarefa>> getAllPhotos();
}