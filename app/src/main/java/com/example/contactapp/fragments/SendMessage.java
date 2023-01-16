package com.example.contactapp.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactapp.R;
import com.example.contactapp.adapters.GroupAdapter;
import com.example.contactapp.adapters.MessageAdapter;
import com.example.contactapp.adapters.MessageHAdapter;
import com.example.contactapp.models.GroupModel;
import com.example.contactapp.models.MessageModel;
import com.example.contactapp.ui.activity.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class SendMessage extends Fragment {

    RecyclerView groupRecyclerView, messageRecyclerView;
    ArrayList<GroupModel> groupModelArrayList;
    ArrayList<MessageModel> messageModelArrayList;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    View root;
    GroupModel selected;
    MessageModel selectedMessageModel;
    TextView selectedGroup,selectedMessage;
    Button sendButton;
    MessageHAdapter messageHAdapter;
    public SendMessage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_send_message, container, false);
        groupRecyclerView=root.findViewById(R.id.recycler_sendm_groupl);
        messageRecyclerView=root.findViewById(R.id.message_list);
        selectedGroup=root.findViewById(R.id.selected_group);
        selectedMessage=root.findViewById(R.id.selected_message);
        groupModelArrayList=new ArrayList<>();
        messageModelArrayList=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        sendButton=root.findViewById(R.id.send_message_button);

        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), a -> {
            if (a) {
                getMessage();
            } else {
                Tools.showMessage("Permission Denied");
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(Manifest.permission.SEND_SMS);
        } else {
            getMessage();
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMessage();
            }
        });
        getGroupList();
        readData();
        return root;
    }

    private void getGroupList() {
        firestore.collection("groups").document(mAuth.getCurrentUser().getUid()).collection("user_groups").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                groupModelArrayList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    GroupModel groupModel = new GroupModel(document.getId(), document.getString("group_name"), document.getString("group_desc"), document.getString("group_image"));
                    groupModelArrayList.add(groupModel);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                linearLayoutManager.setReverseLayout(false);
                groupRecyclerView.setLayoutManager(linearLayoutManager);

                groupRecyclerView.setAdapter(new GroupAdapter(groupModelArrayList, a -> {
                    selected = groupModelArrayList.get(a);
                    selectedGroup.setText("Seçili Grup: " + selected.getGroup_name());

                }));

            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
    }


    private void readData() {
        firestore.collection("messages").document(mAuth.getCurrentUser().getUid()).collection("user_messages").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                messageModelArrayList.clear();

                for (QueryDocumentSnapshot document : task.getResult()) {
                    MessageModel messageModel = document.toObject(MessageModel.class);
                    messageModelArrayList.add(messageModel);

                }
                messageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                messageRecyclerView.setAdapter(new MessageHAdapter(messageModelArrayList, a -> {
                    selectedMessageModel = messageModelArrayList.get(a);
                    selectedMessage.setText("Seçili Mesaj: " + selectedMessageModel.getMessage_name());

                }));
            }
        });
    }

    private void getMessage(){
        if(selected==null){
            Tools.showMessage("Lütfen Grup Seçiniz");
            return;
        }
        if(selected.getGroup_members()!=null&&selected.getGroup_members().size()>0){
            SmsManager smsManager=SmsManager.getDefault();
            for(String number:selected.getGroup_members()){
                smsManager.sendTextMessage(number,null,selectedMessageModel.getMessage_text(),null,null);
            }
            Tools.showMessage("Başarılı");
        }
    }
}