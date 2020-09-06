package com.example.telas_background.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.Chat;
import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.R;
import com.example.telas_background.instanceClasses.ClassContact;
import com.example.telas_background.item.Item_contact;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.ArrayList;

public class FragmentContact extends Fragment {

    private ArrayList<Item_contact> list;

    private RecyclerView recycler;
    private GroupAdapter adapter;
    private SearchView filter;
    private static Context context;


    @Override
    public void onAttach(@NonNull Context context1) {
        context = context1;
        super.onAttach(context1);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_contact, container , false);

        recycler = root.findViewById(R.id.contact_recyclerview);
        filter = root.findViewById(R.id.contact_search);

        list = new ArrayList<Item_contact>();

        adapter = new GroupAdapter();
        recycler.setLayoutManager(new LinearLayoutManager( context, LinearLayoutManager.VERTICAL , false));
        recycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Intent intent = new Intent(context, Chat.class);
                Bundle bundle = new Bundle();

                Item_contact conversas= (Item_contact) item;
                bundle.putString("nome", conversas.contact.getName());
                bundle.putString("foto", conversas.contact.getImage());
                bundle.putString("path", conversas.contact.getPath());

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        filter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.trim().isEmpty()) {
                    filter(newText);
                }else{
                    filterReset();
                }
                return false;
            }
        });

        getfFriends();

        return root;
    }

    //procurar user por nome
    private void filter(String text) {

        if (!text.isEmpty()) {
            ArrayList<Item_contact> filteredList = new ArrayList<>();
            for (Item_contact item : list) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    Log.d("lista item", "top "+ item.getName());
                    filteredList.add(item);
                }
            }
               adapter.update(filteredList);
        }
    }

    private void filterReset() {
        adapter.update(list);
    }


    private void getfFriends(){
        FirebaseFirestore.getInstance().collection("amigo").
                document(UserPrincipal.getId()).collection("amigo").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                getfFriend(document);
                            }
                        } else {
                            Log.d("Perfil ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getfFriend(final QueryDocumentSnapshot docAmigo) {
        FirebaseFirestore.getInstance().collection("user")
                .document(docAmigo.get("id").toString()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        if (documentSnapshot.exists()) {

                            getfLastMsg( docAmigo.get("path").toString() , documentSnapshot);
                        }
                    }
                });
    }

    private void getfLastMsg(final String path , final DocumentSnapshot document1){
        FirebaseFirestore.getInstance().collection(path+"/mensagens")
                .orderBy("tempo" , Query.Direction.DESCENDING).limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            String msg = null;

                            for (QueryDocumentSnapshot document : task.getResult()){
                                if ( document.get("msg") != null){
                                    msg = document.get("msg").toString();
                                }else{
                                    msg = "...";
                                }
                            }

                            final ClassContact classUser = new ClassContact(document1.get("foto").toString(),
                                    document1.get("id").toString(), document1.get("nome").toString() , path , msg);

                            list.add(new Item_contact(classUser));
                            adapter.add(new Item_contact(classUser));
                        }
                    }
                });
    }

}