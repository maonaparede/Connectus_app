package com.example.telas_background.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telas_background.CleanString;
import com.example.telas_background.initialize.UserPrincipal;
import com.example.telas_background.R;
import com.example.telas_background.dialog_toast.MakeToast;
import com.example.telas_background.firebase.FriendRequestFirebase;
import com.example.telas_background.firebase.MeetingFirebase;
import com.example.telas_background.instanceClasses.ClassMeetingRequest;
import com.example.telas_background.instanceClasses.ClassUserScreen;
import com.example.telas_background.item.Item_friend_request;
import com.example.telas_background.item.Item_meeting_request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;

public class FragmentRequest extends Fragment {

    private RecyclerView requestRecycler;
    private static GroupAdapter requestAdapter;
    private static Context context;


    @Override
    public void onAttach(@NonNull Context context1) {
        context = context1;
        super.onAttach(context1);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_request, container , false);

        requestRecycler = root.findViewById(R.id.request_recyclerview);
        requestAdapter = new GroupAdapter();
        requestRecycler.setLayoutManager(new LinearLayoutManager( context, LinearLayoutManager.VERTICAL , false));
        requestRecycler.setAdapter(requestAdapter);


        getfMeetingRequest();
        getfFriendRequest();

        return root;
    }


    public static void fromItemFriendRequest(Item item , Integer state , final Integer position){

        Item_friend_request request = (Item_friend_request) item;
        switch (state){
            case 0:
                MakeToast.makeToast(context , "Socializar");
                FriendRequestFirebase.friendAddFirebaseFunctionCall(request.user.getId());
                requestAdapter.removeGroup(position);
                requestAdapter.notifyItemChanged(position);
                break;
            case 1:
                MakeToast.makeToast(context , "Rejeitar");
                FriendRequestFirebase.denyRequest(request.user.getId());
                requestAdapter.removeGroup(position);
                requestAdapter.notifyItemChanged(position);
                break;
            default:
                /*
                MakeToast.makeToast(context , "Perfil");
                Intent intent = new Intent(context, Perfil.class);
                Bundle bundle = new Bundle();

                bundle.putString("user", request.user.getId());
                bundle.putInt("estado", 3);

                intent.putExtras(bundle);
                context.startActivity(intent);

                 */
                break;
        }
    }

    public static void fromItemMeetingRequest(Item item , Integer state , final Integer position){

        Item_meeting_request request = (Item_meeting_request) item;
        switch (state){
            case 0:
                MakeToast.makeToast(context , "Aceitou!");
                MeetingFirebase.acceptRequestMeeting(request.request.getOwnerId());
                requestAdapter.removeGroup(position);
                requestAdapter.notifyItemChanged(position);
                break;
            case 1:
                MakeToast.makeToast(context , "Rejeitar");
                MeetingFirebase.denyRequestMeeting(request.request.getOwnerId());
                requestAdapter.removeGroup(position);
                requestAdapter.notifyItemChanged(position);
                break;
            default:
                MakeToast.makeToast(context , "Encontro");
                break;
        }
    }

    private void getfFriendRequest(){
        FirebaseFirestore.getInstance().collection("request").
                document(UserPrincipal.getId()).collection("request").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                ClassUserScreen user = new ClassUserScreen(document.get("foto").toString()
                                        , document.get("id").toString(), document.get("nome").toString());

                                requestAdapter.add(new Item_friend_request(user));
                            }
                        } else {
                            Log.d("FriendRequest ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getfMeetingRequest(){
        FirebaseFirestore.getInstance().collection("request_encontro").
                document(UserPrincipal.getId()).collection("request").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //tem q colocar o nome do criador como parametro na criação do encontro
                                //pra depois puxar aqui
                                ClassMeetingRequest encontro = new ClassMeetingRequest(document.get("titulo").toString()
                                        , (document.get("dono").toString()), document.get("local").toString(),
                                        document.get("dia").toString(), document.get("horario").toString(),
                                        CleanString.clean(document.get("foto")) ,
                                        CleanString.clean(document.get("nome")));

                                requestAdapter.add(new Item_meeting_request(encontro));
                                // Log.d("Perfil" ,document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("FriendRequest ", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}