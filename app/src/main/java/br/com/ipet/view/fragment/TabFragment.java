package br.com.ipet.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.ipet.R;
import br.com.ipet.view.fragment.tab.ProdutoFragment;
import br.com.ipet.view.fragment.tab.ServicoFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TabFragment extends Fragment {

    @BindView(R.id.tabs)
    public TabLayout tabLayout;
    @BindView(R.id.viewPager)
    public ViewPager viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        viewPager.setAdapter(new MenuFragmentAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;
    }

    public class MenuFragmentAdapter extends FragmentPagerAdapter {

        MenuFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ProdutoFragment();
                case 1:
                    return new ServicoFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Produtos";
                case 1:
                    return "Serviços";
            }
            return null;
        }
    }
}
