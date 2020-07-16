package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.telas_background.utils_helper.MakeToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registrar extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email1;
    private EditText senha2;
    private EditText senha3;
    private Uri uri;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        email1 = findViewById(R.id.email_registrar);
        senha2 = findViewById(R.id.senha_registrar);
        senha3 = findViewById(R.id.confirmar_senha_registrar);
    }



    //Verifica se os parametros estão preenchidos e se a senha está igual
    public void botaoClick(View view){

        String email = email1 .getText().toString().trim();
        String senha = senha2.getText().toString();
        String senha1 = senha3.getText().toString();

        if (email == null || email.isEmpty() || senha == null || senha.isEmpty()
                || senha1 == null || senha1.isEmpty()){

            MakeToast.makeToast(this , "Campos não preenchidos!");
            return;
        }
        if(!senha.equals(senha1)){
            MakeToast.makeToast(this , "As senhas fornecidas não são iguais!");
            return;
        }
        if (senha.length() < 6){
            //obrigatório pro Auth do firebase
            MakeToast.makeToast(this , "A senha deve ser de no mínimo 6 caracteres");
            return;
        }

        registrar(email , senha);
    }



    public void updateUI(FirebaseUser account){
        if(account != null){
            MakeToast.makeToast(this, "Registrado Com Sucesso");
            startActivity(new Intent( this , Editar_perfil.class));
        }else {
            MakeToast.makeToast(this,"Você não foi Registrado");
        }
    }


    //Parte do BD


    private void registrar(String email, String password) {

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.i("Registrar", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Registrar", "createUserWithEmail:failure", task.getException());
                            MakeToast.makeToast( Registrar.this , "Authentication failed.");
                            updateUI(null);
                        }
                        // ...
                    }
                });

    }


}
