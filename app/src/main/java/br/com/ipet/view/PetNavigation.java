package br.com.ipet.view;

import android.support.v4.app.Fragment;

public interface PetNavigation {
    void navigateTo(Fragment fragment, boolean addToBackstack);
}
