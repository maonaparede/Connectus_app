package com.example.telas_background;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.telas_background.initialize.Initialize;
import com.example.telas_background.initialize.UserPrincipal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity{

    private String email;
    private String password;
    private FirebaseAuth mAuth;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onStart() {
        verifyPermission();
        super.onStart();
    }

    private void verifyPermission(){
        if(ContextCompat.checkSelfPermission(
                getApplicationContext() , Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
        }
        verifyIsLogged();
    }

    private void verifyIsLogged(){

        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        email = shared.getString("email" , "");
        password = shared.getString("password" , "");
        mAuth = FirebaseAuth.getInstance();

        if((!email.isEmpty()) && (!password.isEmpty())){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task){
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            }else{
                                // If sign in fails, display a message to the user.
                                Log.w("Login", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }else{
            startActivity(new Intent(this, Login.class));
        }
    }

    private void updateUI(FirebaseUser account){
                if(account != null){
                    new Initialize().sql(this).friendListener();
                    setNameImage();
                    Intent intent = new Intent( this , FragmentHandler.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    startActivity(new Intent( this , Login.class));
                }
            }

    private void setNameImage() {
        FirebaseFirestore.getInstance().collection("user").document(UserPrincipal.getId())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.get("nome").toString();
                    String image = CleanString.clean(documentSnapshot.get("foto"));

                    SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
                    shared.edit().putString("name" , name).apply();
                    shared.edit().putString("image" , image).apply();
                    UserPrincipal.setFoto(image);
                    UserPrincipal.setName(name);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //startLocationService();
            }else{
                Toast.makeText(this , "Permission Denied" , Toast.LENGTH_SHORT).show();
                finishAffinity();
            }

        }
    }


}
