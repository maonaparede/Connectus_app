package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telas_background.dialog_toast.MakeDialogGeneric;
import com.example.telas_background.dialog_toast.MakeToast;
import com.example.telas_background.location.FirebaseGeoFire;
import com.example.telas_background.location.LocationStateControler;
import com.example.telas_background.sqlite.FriendsSqlController;
import com.example.telas_background.timer.Cronos;
import com.example.telas_background.timer.CronosInterface;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class FragmentHandler extends AppCompatActivity implements CronosInterface, LifecycleObserver {

    private AppBarConfiguration mAppBarConfiguration;
    private static Context context;
    private ImageView imageUser;
    private TextView nameUser;
    private TextView emailUser;
    private Integer timerCounter;
    private Cronos cronos;


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
                        listenerDestination(destination);
                }
            });


        cronos = new Cronos(this , 5);
        cronos.startOrPlay();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        timerCounter = 0;

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void appGo(){
       cronos.pause();
        LocationStateControler.stopLocationService(this);
        FirebaseGeoFire.removeLocationServer();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void appBack(){
       cronos.startOrPlay();
       LocationStateControler.startLocationService(this);
    }

    //Responsável por ser o timer, com 5 min atualiza a localização do user
    // E a cada 30 manda um dialog falando pro user dar uma pausa
    //720 x 5 segundos = 1h
    @Override
    public void cronosFinish() {
        if (timerCounter == 72) {
                new MakeDialogGeneric().createDialogOk(this,
                        "Respira um pouco, viva a vida real!",
                        "Que tal dar uma pausa?");

                timerCounter = 0;
        }
                timerCounter++;
                FirebaseGeoFire.setLocationServer(this);
                cronos.startOrPlay();
    }


    private void listenerDestination(NavDestination destination){
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
        }

        if((!destination.getArguments().isEmpty())){

        }

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