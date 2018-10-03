package br.com.ipet.view.fragment.drawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.ipet.R;
import butterknife.ButterKnife;

public class QuemSomosFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quem_somos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
