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
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    private RecyclerView meetingRecycler;
    private GroupAdapter meetingAdapter;
    private RecyclerView userRecycler;
    private GroupAdapter userAdapter;
    private Button createMeeting;
    private TextView nearMeetingName;
    private TextView nearMeetingDay;
    private ImageView nearMeetingImage;

    private ArrayList<Item_home_user> nearUsers;
    private ArrayList<Item_home_meeting> meetingList;
    private Integer nearestMeeting = 30003030;
    private Integer state = 0;
    private Context context;


    @Override
    public void onAttach(@NonNull Context context1) {
        context = context1;
        super.onAttach(context1);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_home, container , false);

        nearMeetingName = root.findViewById(R.id.home_textview_next_meeting_name);
        nearMeetingDay = root.findViewById(R.id.home_textview_next_meeting_day);
        nearMeetingImage = root.findViewById(R.id.home_imageview_nextmeeting);
        createMeeting = root.findViewById(R.id.home_button_create_meeting);
        userRecycler = root.findViewById(R.id.home_recyclerview_user);
        meetingRecycler = root.findViewById(R.id.home_recyclerview_meeting);

        userAdapter = new GroupAdapter();
        meetingAdapter = new GroupAdapter();

        meetingRecycler.setLayoutManager(new LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL , false));
        userRecycler.setLayoutManager(new LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL , false));

        meetingAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull Item item, @NonNull View view) {
                Log.d("encontro " , "boa");
                toMeeting(item);
            }
        });

        userRecycler.setAdapter(userAdapter);
        meetingRecycler.setAdapter(meetingAdapter);


        nearUsers = new ArrayList<Item_home_user>();
        meetingList = new ArrayList<Item_home_meeting>();

        userAdapter.setOnItemClickListener(new OnItemClickListener() {
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
        if(state == 0) {
            Intent intent = new Intent(getActivity(), MeetingEdit.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getActivity(), MeetingEdit.class);
            Bundle bundle = new Bundle();

            bundle.putString("encontro", "encontro/"+ UserPrincipal.getId());
            bundle.putInt("estado", 1);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    //
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


    //pega todos os encontros
    private void getfMeetings(){
        FirebaseFirestore.getInstance().collection("caminho_encontro").
                document(UserPrincipal.getId()).collection("encontro")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@com.google.firebase.database.annotations.Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "listen:error", e);
                            return;
                        }
                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:

                                    getfMeeting(dc.getDocument());
                                    break;
                                case MODIFIED:
                                    Log.d("TAG", "Modified doc: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:

                                    String ownerMeet = dc.getDocument().get("dono").toString();
                                    removeMeetingItem(ownerMeet);
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value: " + dc.getType());
                            }
                        }
                    }
                });
    }

    //pega os atributos específicos de cada encontro
    private void getfMeeting(final QueryDocumentSnapshot docA) {
        FirebaseFirestore.getInstance().collection("encontro")
                .document(docA.get("dono").toString()).collection("atributos")
                .document("atributos").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                   Item_home_meeting item = new Item_home_meeting(documentSnapshot.get("nome").toString()
                                    , documentSnapshot.get("foto").toString(),
                           documentSnapshot.get("dono").toString() , Integer.parseInt(documentSnapshot.get("dia").toString()));

                   meetingList.add(item);

                   verifyIfUserIsOwnerMeeting(item);

                   meetingAdapter.add(item);
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                getNearestMeet();
            }
        });
    }

    //pega o encontro com a data mais proxima e coloca no topo da home
    private void getNearestMeet() {
        Item_home_meeting replace = null;
        for (Item_home_meeting meet : meetingList) {
        Integer a = meet.getDay();
        if(a < nearestMeeting){
            nearestMeeting = a;
            replace = meet;
        }
     }

        if(replace != null) {
           setNearMeeting(replace);
        }else{

            Picasso.get().load("v").into(nearMeetingImage);
        }
    }

    private void setNearMeeting(Item_home_meeting item){
        nearMeetingName.setText(item.getName());

        String date1 = String.valueOf(item.getDay());
        String year2 = date1.substring(0,4);
        String month2 = date1.substring( 4,6);
        String day2 = date1.substring(6);
        nearMeetingDay.setText(day2 + "/" + month2 + "/" + year2);

        Picasso.get().load(item.getImage()).into(nearMeetingImage);
    }

    //Verifica se o user é dono de algum dos encontros e se for muda o botao de "Criar encontro" para "Editar Encontro"
    private void verifyIfUserIsOwnerMeeting(Item_home_meeting item){
        if(item.getOwnerId().equals(UserPrincipal.getId())){
            state = 1;
            createMeeting.setText("Editar Meu Encontro");
        }
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
                removeNearUserItem(b);
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

    //Called by getfLocUsersNear, get the user photo and id
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
                                userAdapter.update(nearUsers);
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void removeNearUserItem(String id){
        for (Item_home_user item : nearUsers) {
            if (item.user.getId().contains(id)) {
                nearUsers.remove(item);
                userAdapter.update(nearUsers);
            }
        }
    }

    private void removeMeetingItem(String id){
        for (Item_home_meeting item : meetingList) {
            if (item.getOwnerId().contains(id)) {
                meetingList.remove(item);
                getNearestMeet();
                meetingAdapter.update(meetingList);
            }
        }
    }

}
