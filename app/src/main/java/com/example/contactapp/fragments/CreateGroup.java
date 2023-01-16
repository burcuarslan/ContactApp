package com.example.contactapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.contactapp.R;
import com.example.contactapp.adapters.GroupListAdapter;
import com.example.contactapp.models.GroupModel;
import com.example.contactapp.ui.activity.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class CreateGroup extends Fragment {

    EditText groupName, groupDescription;
    ImageView groupImage;
    Button createGroupButton;
    RecyclerView groupListRecyclerView;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    View root;
    String name,desc,image;
    Uri imageUri;
    ArrayList<GroupModel> groupModelArrayList;
    GroupListAdapter groupAdapter;

    public CreateGroup() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root= inflater.inflate(R.layout.fragment_create_group, container, false);

        groupName = root.findViewById(R.id.create_group_name);
        groupDescription = root.findViewById(R.id.create_group_desc);
        groupImage = root.findViewById(R.id.create_group_image);
        groupListRecyclerView = root.findViewById(R.id.group_list_recycler);
        createGroupButton = root.findViewById(R.id.send_message_button);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 name = groupName.getText().toString();
                 desc = groupDescription.getText().toString();

                if(Tools.validator(name, desc)&&imageUri!=null){
                    StorageReference storageReference = storage.getReference().child("GroupImages/"+ mAuth.getCurrentUser().getUid()).child(imageUri.getLastPathSegment());
                    storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                        storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                             image = uri.toString();
                            createGroup();
                        });
                    });
                }
                else{
                    Tools.showMessage("Please fill all the fields");
                }

            }
        });
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK) {
                Intent data = result.getData();
                imageUri = data.getData();
                groupImage.setImageURI(imageUri);
            }
        });

        groupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });


        messageRecyclerView();
        getGroupList();
        return root;
    }


    private void createGroup() {

        GroupModel groupModel = new GroupModel(name,desc,image);
        db.collection("groups").document(mAuth.getCurrentUser().getUid()).collection("user_groups").add(groupModel).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Tools.showMessage("Group Created");
            } else {
                Tools.showMessage("Group Creation Failed");
            }
        });
    }

    private void messageRecyclerView() {
        groupListRecyclerView = root.findViewById(R.id.group_list_recycler);

        groupListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        groupModelArrayList = new ArrayList<>();
        groupAdapter = new GroupListAdapter(getActivity(), groupModelArrayList);
        groupListRecyclerView.setAdapter(groupAdapter);
    }

    private void getGroupList() {
        db.collection("groups").document(mAuth.getCurrentUser().getUid()).collection("user_groups").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                groupModelArrayList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    GroupModel groupModel = document.toObject(GroupModel.class);
                    groupModelArrayList.add(groupModel);
                    groupAdapter.notifyDataSetChanged();
                }

            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
    }
}