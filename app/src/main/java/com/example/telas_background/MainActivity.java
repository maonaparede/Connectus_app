package com.example.telas_background;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.telas_background.firebase.GetUserPrincipal;
import com.example.telas_background.dialog_toast.MakeToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email1;
    private EditText password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //startActivity(new Intent(this , Perfil.class));

        email1 = findViewById(R.id.login_edittext_email);
        password1 = findViewById(R.id.login_edittext_password);
    }

    public void cadastrar(View view){
        startActivity(new Intent(this , Register.class));
    }


    public void login(View v){

        String email = email1.getText().toString();
        String password = password1.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email  , password)
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

                        // ...
                    }
                });
    }


    public void updateUI(FirebaseUser account){
        if(account != null){
            MakeToast.makeToast(this, "Logado Com Sucesso");
            new GetUserPrincipal();
            startActivity(new Intent( this , Home.class));
        }else {
            MakeToast.makeToast(this,"Você não foi Logado");
        }
    }

}
