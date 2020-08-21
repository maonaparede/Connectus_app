package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.telas_background.dialog_toast.MakeToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private EditText email1;
    private EditText password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //startActivity(new Intent(this , Perfil.class));

        email1 = findViewById(R.id.login_edittext_email);
        password1 = findViewById(R.id.login_edittext_password);
    }

    public void cadastrar(View view){
        startActivity(new Intent(this , Register.class));
    }


    public void login(View v){

        final String email = email1.getText().toString();
        final String password = password1.getText().toString();

        mAuth = FirebaseAuth.getInstance();

        if((!email.isEmpty()) && (!password.isEmpty()) ) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Login", "signInWithEmail:success");

                                SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
                                 shared.edit().putString("email" , email).apply();
                                 shared.edit().putString("password" , password).apply();
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Login", "signInWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }else{
            MakeToast.makeToast(this , "Os dois Campos precisam estar Preenchidos!");
        }
    }


    public void updateUI(FirebaseUser account){
        if(account != null){
            MakeToast.makeToast(this, "Logado Com Sucesso");
            startActivity(new Intent( this , MainActivity.class));
        }else {
            MakeToast.makeToast(this,"Você não foi Logado");
        }
    }


}
