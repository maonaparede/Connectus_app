package com.example.telas_background;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private String email;
    private String password;
    private FirebaseAuth mAuth;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
         email = shared.getString("email" , "");
         password = shared.getString("password" , "");

    }

    @Override
    protected void onStart() {

        mAuth = FirebaseAuth.getInstance();

        if((!email.isEmpty()) && (!password.isEmpty())){

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                updateUI(user);
                            } else {
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



        super.onStart();
    }



    public void updateUI(FirebaseUser account){
                if(account != null){
                    new Initialize().sql(context).friendListener();
                    setNameImage();
                    startActivity(new Intent( this , FragmentHandler.class));
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
                    String image = documentSnapshot.get("foto").toString();

                    SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
                    shared.edit().putString("name" , name).apply();
                    shared.edit().putString("image" , image).apply();

                    UserPrincipal.setFoto(image);
                    UserPrincipal.setNome(name);
                }
            }
        });
    }
}
