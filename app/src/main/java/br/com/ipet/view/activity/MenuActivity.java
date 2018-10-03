package br.com.ipet.view.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import br.com.ipet.R;
import br.com.ipet.infrastructure.image.picasso.transform.CircleTransform;
import br.com.ipet.view.fragment.TabFragment;
import br.com.ipet.view.fragment.drawer.ConfiguracoesFragment;
import br.com.ipet.view.fragment.drawer.ContatoFragment;
import br.com.ipet.view.fragment.drawer.MeusPedidosFragment;
import br.com.ipet.view.fragment.drawer.PerfilFragment;
import br.com.ipet.view.fragment.drawer.QuemSomosFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.drawerLayout)
    public DrawerLayout drawerLayout;
    @BindView(R.id.navView)
    public NavigationView navigationView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.containerView, new TabFragment())
                .commit();

        navigationView.setCheckedItem(R.id.nav_item_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment selectedFragment = selectDrawerItem(menuItem);

                if (menuItem.getItemId() != R.id.nav_item_avaliar) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.containerView, selectedFragment)
                            .commit();

                    if (menuItem.getItemId() != R.id.nav_item_avaliar) getSupportActionBar().setTitle(menuItem.getTitle());
                    navigationView.setCheckedItem(menuItem.getItemId());
                }

                drawerLayout.closeDrawers();

                return false;
            }
        });
        View headerLayout = navigationView.getHeaderView(0);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null)
        {
            TextView navHeaderText = headerLayout.findViewById(R.id.nav_header_textView);
            ImageView navHeaderImage = headerLayout.findViewById(R.id.nav_header_imageView);
            navHeaderText.setText(firebaseUser.getDisplayName());
            Picasso.get().load(firebaseUser.getPhotoUrl()).transform(new CircleTransform()).into(navHeaderImage);
        }

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    public Fragment selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_item_home: {
                fragment = new TabFragment();
                break;
            }
            case R.id.nav_item_meus_pedidos: {
                fragment = new MeusPedidosFragment();
                break;
            }
            case R.id.nav_item_perfil: {
                fragment = new PerfilFragment();
                break;
            }
            case R.id.nav_item_configuracoes: {
                fragment = new ConfiguracoesFragment();
                break;
            }
            case R.id.nav_item_avaliar: {
                launchPlayStore();
                break;
            }
            case R.id.nav_item_quem_somos: {
                fragment = new QuemSomosFragment();
                break;
            }
            case R.id.nav_item_contato: {
                fragment = new ContatoFragment();
                break;
            }
        }
        return fragment;
    }

    private void launchPlayStore() {
//        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Uri uri = Uri.parse("market://details?id=" + "com.trello");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}