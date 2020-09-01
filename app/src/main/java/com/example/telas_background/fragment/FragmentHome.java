package com.example.telas_background.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
import com.example.telas_background.location.FirebaseGeoFire;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    private RecyclerView encontrosRecycler;
    private GroupAdapter encontrosAdapter;
    private RecyclerView pessoasRecycler;
    private GroupAdapter pessoasAdapter;
    private Button createMeeting;

    private ArrayList<Item_home_user> nearUsers;
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
        createMeeting = root.findViewById(R.id.home_button_create_meeting);
        pessoasRecycler = root.findViewById(R.id.home_recyclerview_user);
        encontrosRecycler = root.findViewById(R.id.home_recyclerview_meeting);

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


        nearUsers = new ArrayList<Item_home_user>();

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

        getfLocUsersNear();
        getfMeetings();

        return root;
    }

    //Criar Encontro
    public void toMeetingEdit(View view){
        Intent intent = new Intent(getActivity() , MeetingEdit.class);
        startActivity(intent);
    }

    public void toMeeting(Item item){
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

    private void getfLocUsersNear(){
        Location location1 = FirebaseGeoFire.getLocation(context.getApplicationContext());
        GeoLocation location = new GeoLocation(location1.getLatitude() , location1.getLongitude());
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("location");
        GeoFire geoFire = new GeoFire(ref);

        Double raio = 0.6;

        GeoQuery query  = geoFire.queryAtLocation(location , raio);

        query.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {
            @Override
            public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {
                String a = dataSnapshot.getKey();
                if(!a.equals(UserPrincipal.getId())) {
                    getfUserNear(a);
                }
                Log.d("FirebaseLocation.class" , "onDataEntered" + a);
            }

            @Override
            public void onDataExited(DataSnapshot dataSnapshot) {
                String b = dataSnapshot.getKey();
                removeNearUser(b);
            }

            @Override
            public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    private void getfUserNear(String id){
        FirebaseFirestore.getInstance().collection("user").document(id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            Item_home_user user = new Item_home_user(
                                    new ClassUserScreen(documentSnapshot.get("foto").toString(),
                                            documentSnapshot.get("id").toString(), documentSnapshot.get("nome").toString())
                            );
                            try{
                                nearUsers.add(user);
                                pessoasAdapter.update(nearUsers);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void removeNearUser(String id){
        for (Item_home_user item : nearUsers) {
            if (item.user.getId().contains(id)) {
                nearUsers.remove(item);
            }
        }
    }

}
