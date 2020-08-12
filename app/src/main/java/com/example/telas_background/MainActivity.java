package com.example.telas_background;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.telas_background.firebase.GetUserPrincipal;
import com.example.telas_background.dialog_toast.MakeToast;
import com.example.telas_background.fragment.Home;
import com.example.telas_background.initialize.Initialize;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private String email;
    private String password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                    new GetUserPrincipal();
                    startActivity(new Intent( this , FragmentHandler.class));
                }else {
                    startActivity(new Intent( this , Login.class));
                }
            }
}
