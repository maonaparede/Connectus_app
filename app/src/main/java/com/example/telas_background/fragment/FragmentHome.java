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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.MeetingEdit;
import com.example.telas_background.R;
import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.item.Item_home_meeting;
import com.example.telas_background.item.Item_home_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

public class FragmentHome extends Fragment {

    private RecyclerView encontrosRecycler;
    private GroupAdapter encontrosAdapter;
    private RecyclerView pessoasRecycler;
    private GroupAdapter pessoasAdapter;
    private Button createMeeting;
    private static Context context;


    @Override
    public void onAttach(@NonNull Context context1) {
        context = context1;
        super.onAttach(context1);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_home, container , false);

        createMeeting = (Button) root.findViewById(R.id.home_button_create_meeting);

        pessoasRecycler = (RecyclerView) root.findViewById(R.id.home_recyclerview_user);
        encontrosRecycler = (RecyclerView) root.findViewById(R.id.home_recyclerview_meeting);


        pessoasAdapter = new GroupAdapter();
        encontrosAdapter = new GroupAdapter();

        encontrosRecycler.setLayoutManager(new LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL , false));
        pessoasRecycler.setLayoutManager(new LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL , false));

        encontrosAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Log.d("encontro " , "boa");
                toMeeting(item);
            }
        });

        pessoasRecycler.setAdapter(pessoasAdapter);
        encontrosRecycler.setAdapter(encontrosAdapter);


        pessoasAdapter.add(new Item_home_user(
                new ClassUserScreen("https://cdna.artstation.com/p/assets/images/images/027/262/302/small/bernardo-cruzeiro-13.jpg?1591038327" , "sPDdPbX7juTtGUDTN95uMlTEjes1" , "")));



        pessoasAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                toPerfil(item);
            }
        });




        createMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMeetingEdit(v);
            }
        });
        getfMeetings();


        return root;
    }


    private void getfMeetings(){
        FirebaseFirestore.getInstance().collection("caminho_encontro").
                document(UserPrincipal.getId()).collection("encontro").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                getfMeeting(document);
                            }
                        } else {
                            Log.d("Home ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getfMeeting(final QueryDocumentSnapshot docA) {
        FirebaseFirestore.getInstance().collection("encontro")
                .document(docA.get("dono").toString()).collection("atributos")
                .document("atributos").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                   Item_home_meeting item = new Item_home_meeting(documentSnapshot.get("nome").toString()
                                    , documentSnapshot.get("foto").toString(),
                           documentSnapshot.get("dono").toString());

                    encontrosAdapter.add(item);
                }
            }
        });
    }

    //Criar Encontro
    public void toMeetingEdit(View view){

        Intent intent = new Intent(getActivity() , MeetingEdit.class);
        startActivity(intent);
    }

    public void toMeeting(Item item){

        Log.d("encontro " , "picasso");


        Bundle bundle = new Bundle();
        Item_home_meeting meeting = (Item_home_meeting) item;
        bundle.putString("dono" , meeting.getOwnerId());

        Fragment fragment = new FragmentMeeting();


        fragment.setArguments(bundle);

        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.nav_host_fragment , fragment);

        transaction.commit();

    }

    public void toPerfil(Item item) {
        Bundle bundle = new Bundle();
        Item_home_user item1 = (Item_home_user) item;
        bundle.putString("user", item1.user.getId());
        bundle.putInt("estado", 1);

        Fragment fragment = new FragmentPerfil();


        fragment.setArguments(bundle);

        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.nav_host_fragment , fragment);

        transaction.commit();

    }
}
