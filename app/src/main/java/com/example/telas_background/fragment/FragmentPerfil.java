package com.example.telas_background.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.PostCreate;
import com.example.telas_background.R;
import com.example.telas_background.dialog_toast.DialogCreatePost;
import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.instanceClasses.ClassPerfilPerfil;
import com.example.telas_background.instanceClasses.ClassPerfilPost;
import com.example.telas_background.instanceClasses.ClassUser;
import com.example.telas_background.firebase.FriendRequestFirebase;
import com.example.telas_background.item.Item_perfil_perfil;
import com.example.telas_background.item.Item_post;
import com.example.telas_background.dialog_toast.MakeToast;
import com.example.telas_background.sqlite.FriendsSqlController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;

public class FragmentPerfil extends Fragment {

    private RecyclerView recycler;
    private GroupAdapter adapter;
    public Button buttonConnect;

    private String idUser;
    private ClassPerfilPerfil perfil;
    private ClassUser classUser;
    private String idPerfil;
    private Integer buttonConnectstate = 0;
    private Context context;
    private Bundle bundle;

    @Override
    public void onAttach(@NonNull Context context1) {
        context = context1;
        super.onAttach(context1);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_perfil, container , false);

        recycler = root.findViewById(R.id.recyclerP);
        buttonConnect = root.findViewById(R.id.socializar_botao_perfil);

        adapter = new GroupAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycler.setAdapter(adapter);

        idUser = UserPrincipal.getId();
        bundle = getArguments();


        try {
            setButtonState();
            setTextButton();
        }catch (Exception e){
            Log.d("Error Button " , e.getMessage());
        }

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyButton(v);
            }
        });

       getfPerfil();

        return root;
    }


    private void getfPerfil(){
        FirebaseFirestore.getInstance().collection("user").document(idPerfil).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //User, id, foto, nome
                if(documentSnapshot.exists()) {
                    classUser = new ClassUser(documentSnapshot.get("foto").toString(),
                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                    //perfil - descrição - Hobbie
                    FirebaseFirestore.getInstance().collection("perfil").document(idPerfil)
                            .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            perfil = documentSnapshot.toObject(ClassPerfilPerfil.class);
                            adapter.add( new Item_perfil_perfil( classUser , perfil));
                            getfPosts();
                        }
                    });
                }
                }
        });
    }

    private void getfPosts(){
        FirebaseFirestore.getInstance().collection("post").
                document(idPerfil).collection("post").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ClassPerfilPost post = new ClassPerfilPost(document.get("imagemPost").toString()
                                        , document.get("descricao").toString());

                                adapter.add(new Item_post(classUser.getName() , classUser.getImage(), post));
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    private void setButtonState(){

        if(bundle != null) {
            String user = bundle.getString("user");

            if(!user.isEmpty()) {
                FriendsSqlController searchFriend = new FriendsSqlController(getActivity());
                if(searchFriend.verifyIfExist(user)){
                    idPerfil = user;
                    buttonConnectstate = 3;
                }else {
                    idPerfil = user;
                     buttonConnectstate = bundle.getInt("estado");
                    }
            }
            if(idUser.equals(idPerfil)){
                buttonConnectstate = 0;
                idPerfil = UserPrincipal.getId();
            }

        }else{
            idPerfil = idUser;
        }
    }

    private void setTextButton(){
        switch (buttonConnectstate){
            case 0:
                buttonConnect.setText("Criar Post");
                break;
            case 1:
                buttonConnect.setText("Socializar");
                break;
            default:
                buttonConnect.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void verifyButton(View v) {
        switch (buttonConnectstate) {
            //Caso for dono do perfil CriarPost
            case 0:
                startActivity(new Intent(context, PostCreate.class));
                break;
            //Caso não for amigo Enviar Friend Request
            case 1:
                buttonConnect.setVisibility(View.INVISIBLE);
                FriendRequestFirebase.sendFriendRequest(idPerfil);
                MakeToast.makeToast(context , "Teste");
                break;
            default:
                buttonConnect.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
