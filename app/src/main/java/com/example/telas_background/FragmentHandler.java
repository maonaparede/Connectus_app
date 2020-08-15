package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telas_background.fragment.FragmentHome;
import com.example.telas_background.sqlite.FriendsSqlController;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class FragmentHandler extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static Context context;
    private ImageView imageUser;
    private TextView nameUser;
    private TextView emailUser;


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
        View view = navigationView.getHeaderView(0);

        imageUser = view.findViewById(R.id.toolbar_image_user);
        nameUser = view.findViewById(R.id.toolbar_name_user);
        emailUser = view.findViewById(R.id.toolbar_email_user);
        setHeaderToolbar();

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

                    FriendsSqlController sqlController = new FriendsSqlController(context);
                    sqlController.excludeAll();

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

    private void setHeaderToolbar() {
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
       String name = shared.getString("name" , "");
       String image = shared.getString("image" , "");
        String email = shared.getString("email" , "");

        nameUser.setText(name);
        emailUser.setText(email);
        if(!image.isEmpty()) {
            Picasso.get().load(image).into(imageUser);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }




}