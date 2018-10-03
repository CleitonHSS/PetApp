package br.com.ipet;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import br.com.ipet.model.entities.Carrinho;
import br.com.ipet.model.entities.Usuario;

public class PetAppApplication extends Application {
    private static PetAppApplication instance;
    private static Context context;
    public static Usuario usuarioLogado;
    public static Carrinho carrinho;

    public static PetAppApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.setContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
