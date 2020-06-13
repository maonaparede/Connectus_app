package com.example.telas_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.telas_background.notificaHelper.NotificaHelper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Registrar extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText nome1;
    private EditText email1;
    private EditText senha2;
    private EditText senha3;
    private ImageView foto;
    private Uri uri;
    private String urlfoto;
    private String uidUser;
    private String nomeUser;
    private StorageTask<UploadTask.TaskSnapshot> uploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        nome1 = findViewById(R.id.nome_registar);
        email1 = findViewById(R.id.email_registrar);
        senha2 = findViewById(R.id.senha_registrar);
        senha3 = findViewById(R.id.confirmar_senha_registrar);
        foto = findViewById(R.id.foto_registrar);
    }



    //Verifica se os parametros estão preenchidos e se a senha está igual
    public void botaoClick(View view){
        String nome = nome1.getText().toString();
        String email = email1 .getText().toString().trim();
        String senha = senha2.getText().toString();
        String senha1 = senha3.getText().toString();

        if ( nome == null || nome.isEmpty()  || email == null || email.isEmpty()
                || senha == null || senha.isEmpty() || senha1 == null || senha1.isEmpty()){
            NotificaHelper.mostrarToast(this , "Campos não preenchidos!");
            return;
        }
        if(!senha.equals(senha1)){
            NotificaHelper.mostrarToast(this , "As senhas fornecidas não são iguais!");
            return;
        }
        if (senha.length() < 6){
            //obrigatório pro Auth do firebase
            NotificaHelper.mostrarToast(this , "A senha deve ser de no mínimo 6 caracteres");
            return;
        }
        nomeUser = nome;

        registrar(email , senha);
    }


    //abre a tela para escolher as imgs
    public void imagemClick(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0 && resultCode == RESULT_OK && data!= null && data.getData() != null) {
            uri = data.getData();

            Picasso.get().load(uri).into(foto);
        }
    }


    //Parte do BD

    public void updateUI(FirebaseUser account){
        if(account != null){
            NotificaHelper.mostrarToast(this, "Registrado Com Sucesso");
            startActivity(new Intent( this , Home.class));
        }else {
            NotificaHelper.mostrarToast (this,"Você não foi Registrado");
        }
    }


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
                            //metodo cria pasta no Firestore, pasta perfil e upa a ft
                            uidUser = user.getUid().toString();
                            uparfotoperfil();
                            //
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Registrar", "createUserWithEmail:failure", task.getException());
                            NotificaHelper.mostrarToast( Registrar.this , "Authentication failed.");
                            updateUI(null);
                        }
                        // ...
                    }
                });

    }

    private void uparfotoperfil(){
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference( "/perfil/" + uidUser);
        final StorageReference reference = mStorageRef.child(uidUser);

         uploadTask = reference.putFile(uri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return reference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    urlfoto = task.getResult().toString();
                    criarPastaFirestore();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }


    private void criarPastaFirestore(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Perfil").document(uidUser);

        //Botando no Firestore
        Map<String, Object> userSend = new HashMap<>();
        userSend.put("id", uidUser);
        userSend.put("nome", nomeUser);
        userSend.put("foto", urlfoto);

        documentReference.set(userSend)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Registrar", "Pasta :success");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Registrar", "Pasta :Failuire");
                    }
                });
    }


}
