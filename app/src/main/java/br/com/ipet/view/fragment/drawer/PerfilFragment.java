package br.com.ipet.view.fragment.drawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.ipet.R;
import butterknife.ButterKnife;

public class PerfilFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        ButterKnife.bind(this, view);

//        consumirApiInfnet();

        return view;
    }

//    private void consumirApiInfnet() {
//        // TODO: Exemplo de consumo de api com o retrofit. Remover quando usar numa api do app
//        InfnetService service = RetrofitClientInstance.getInstance().create(InfnetService.class);
//        Call<List<InfnetTarefa>> call = service.getAllPhotos();
//        call.enqueue(new Callback<List<InfnetTarefa>>() {
//            @Override
//            public void onResponse(Call<List<InfnetTarefa>> call, Response<List<InfnetTarefa>> response) {
//                response.body();
//            }
//
//            @Override
//            public void onFailure(Call<List<InfnetTarefa>> call, Throwable t) {
//                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
