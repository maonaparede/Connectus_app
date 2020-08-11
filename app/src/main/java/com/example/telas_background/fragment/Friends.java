package com.example.telas_background.fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.telas_background.Classes_estaticas.UserPrincipal;
import com.example.telas_background.R;
import com.example.telas_background.fragment.Perfil;
import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.item.Item_friend;
import com.example.telas_background.dialog_toast.DialogFriendRemove;
import com.example.telas_background.dialog_toast.MakeToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;

public class Friends extends Fragment {

    private RecyclerView recycler;
    private static GroupAdapter adapter;
    private static Context context;


    @Override
    public void onAttach(@NonNull Context context1) {
        context = context1;
        super.onAttach(context1);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.activity_friends , container ,false);


        recycler = root.findViewById(R.id.friends_recyclerview);
        adapter = new GroupAdapter();
        recycler.setLayoutManager(new LinearLayoutManager( context, LinearLayoutManager.VERTICAL , false));
        recycler.setAdapter(adapter);

        getfFriends();

        return root;
    }

    public static void fromItemFriend(Item item , Integer state , final Integer position){

        Item_friend friend = (Item_friend) item;
        switch (state){
            case 0:
                MakeToast.makeToast(context , "Remover");
                DialogFriendRemove.createDialogOkCancel(context, friend.user.getId());
                adapter.removeGroup(position);
                adapter.notifyItemRemoved(position);
                break;
            default:
                MakeToast.makeToast(context , "Perfil");
                Intent intent = new Intent(context, Perfil.class);
                Bundle bundle = new Bundle();

                bundle.putString("user", friend.user.getId());
                bundle.putInt("estado", 3);

                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
        }
    }

    private void getfFriends(){
        FirebaseFirestore.getInstance().collection("amigo").
                document(UserPrincipal.getId()).collection("amigo").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                               addItemRecyclerView(document);
                            }
                        } else {
                            Log.d("Friends ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void addItemRecyclerView(final QueryDocumentSnapshot docpath){
        FirebaseFirestore.getInstance().collection("user").
                document(docpath.getId()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            ClassUserScreen user1 = new ClassUserScreen(documentSnapshot.get("foto").toString()
                                    ,documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString());

                            adapter.add(new Item_friend(user1 , docpath.get("path").toString()));
                        }
                    }
                });
    }
}