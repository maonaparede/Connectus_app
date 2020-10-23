package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.telas_background.dialog_toast.MakeToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailEditText = findViewById(R.id.register_edittext_email);
        passwordEditText = findViewById(R.id.register_edittext_password);
        passwordEditText2 = findViewById(R.id.register_edittext_password_confirm);
    }



    //Verifica se os parametros estão preenchidos e se a senha está igual
    public void verifyFields(View view){

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String password2 = passwordEditText2.getText().toString();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()
                || password2 == null || password2.isEmpty()){

            MakeToast.makeToast(this , "Campos não preenchidos!");
            return;
        }
        if(!password.equals(password2)){
            MakeToast.makeToast(this , "As senhas fornecidas não são iguais!");
            return;
        }
        if (password.length() < 6){
            //obrigatório pro Auth do firebase
            MakeToast.makeToast(this , "A senha deve ser de no mínimo 6 caracteres");
            return;
        }

        registerUser(email , password);
    }



    public void updateUI(FirebaseUser account){
        if(account != null){
            MakeToast.makeToast(this, "Registrado Com Sucesso");
            startActivity(new Intent( this , PerfilEdit.class));
        }else {
            MakeToast.makeToast(this,"Você não foi Registrado");
        }
    }


    //Parte do BD


    private void registerUser(String email, String password) {

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
                            MakeToast.makeToast( Register.this , "Authentication failed.");
                            updateUI(null);
                        }
                        // ...
                    }
                });

    }


}
