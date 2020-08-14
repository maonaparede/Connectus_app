package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.telas_background.fragment.Home;
import com.example.telas_background.fragment.Perfil;
import com.google.android.material.navigation.NavigationView;

public class FragmentHandler extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_handler);

        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar_toolbar_custon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_perfil,
                R.id.nav_home,
                R.id.nav_mensagem,
                R.id.nav_friends,
                R.id.nav_request,
                R.id.nav_config

        )
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull
                    NavDestination destination, @Nullable Bundle arguments) {

                if (destination.getId() == R.id.nav_home) {
                  //  Toast.makeText(FragmentHandler.this, "Encontro", Toast.LENGTH_LONG).show();
                }

                if (destination.getId() == R.id.nav_config) {

                    SharedPreferences pref;
                    pref = getSharedPreferences("info", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear().apply();

                    startActivity( new Intent( context , MainActivity.class));
                //    Toast.makeText(FragmentHandler.this, "Perfil", Toast.LENGTH_LONG).show();
                }

                if((!destination.getArguments().isEmpty())){

                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void replaceFragments(Fragment fragment, Bundle bundle) {

        fragment = new Home();
        bundle = null;

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                .commit();

        fragment.setArguments(bundle);

    }

}