package com.example.contactapp.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contactapp.R;
import com.example.contactapp.adapters.ContactAdapter;
import com.example.contactapp.adapters.GroupAdapter;
import com.example.contactapp.adapters.RecyclerViewClickListener;
import com.example.contactapp.models.ContactModel;
import com.example.contactapp.models.GroupModel;
import com.example.contactapp.ui.activity.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;


public class AddMember extends Fragment {

    RecyclerView groupListRecyclerView, contactListRecyclerView;
    TextView selectedGroup;
    GroupAdapter groupAdapter;
    ArrayList<GroupModel> groupModelArrayList;
    ArrayList<ContactModel> contactModelArrayList;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    View root;
    GroupModel selected;
    ContactModel contactModel;

    public AddMember() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_member, container, false);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        contactListRecyclerView = root.findViewById(R.id.recycler_contact_list);
        groupListRecyclerView = root.findViewById(R.id.recycler_group_list_horizontal);
//        contactListRecyclerView = root.findViewById(R.id.recycler_contact_list);
        selectedGroup = root.findViewById(R.id.selected_group_item);
        groupModelArrayList = new ArrayList<>();
        contactModelArrayList = new ArrayList<>();

        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), a -> {
            if (a) {
                getContact();
            } else {
                Tools.showMessage("Permission Denied");
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(Manifest.permission.READ_CONTACTS);
        } else {
            getContact();
        }


        getGroupList();
        return root;
    }


    private void getGroupList() {
        db.collection("groups").document(mAuth.getCurrentUser().getUid()).collection("user_groups").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                groupModelArrayList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    GroupModel groupModel = new GroupModel(document.getId(), document.getString("group_name"), document.getString("group_desc"), document.getString("group_image"));
                    groupModelArrayList.add(groupModel);
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                linearLayoutManager.setReverseLayout(false);
                groupListRecyclerView.setLayoutManager(linearLayoutManager);

                groupListRecyclerView.setAdapter(new GroupAdapter(groupModelArrayList, a -> {
                    selected = groupModelArrayList.get(a);
                    selectedGroup.setText("Seçili Grup: " + selected.getGroup_name());

                }));

            } else {
                Log.d("TAG", "Error getting documents: ", task.getException());
            }
        });
    }

    private void getContact() {
        Cursor cursor = getContext().getContentResolver().query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            @SuppressLint("Range") String phoneNumber = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactModel = new ContactModel(name, phoneNumber);
            contactModelArrayList.add(contactModel);
        }

        contactListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        contactListRecyclerView.setAdapter(new ContactAdapter(contactModelArrayList, a -> {
            contactModel = contactModelArrayList.get(a);
            if (selected != null) {
                db.collection("groups").document(mAuth.getCurrentUser().getUid()).collection("user_groups").document(selected.getUid()).update(new HashMap<String, Object>() {
                    {
                        put(contactModel.getContact_number(), contactModel.getContact_name());
                    }
                }).addOnCompleteListener(task -> {
                    Tools.showMessage("Kişi Gruba Eklendi");
                }).addOnFailureListener(e -> {
                    Tools.showMessage("Kişi Gruba Eklenemedi");
                });
            } else {
                Tools.showMessage("Lütfen Grup Seçiniz");
            }
        }));
    }

}