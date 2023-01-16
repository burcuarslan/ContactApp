package com.example.contactapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.contactapp.R;
import com.example.contactapp.adapters.MessageAdapter;
import com.example.contactapp.models.MessageModel;
import com.example.contactapp.ui.activity.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;


public class CreateMessage extends Fragment {

    EditText message_name, message_text;
    Button create_message_button;
    String message_name_string, message_text_string;
    View root;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ArrayList<MessageModel> messageModelArrayList;
    MessageAdapter messageAdapter;
    RecyclerView messageListRecyclerView;

    public CreateMessage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_create_message, container, false);

        message_name = root.findViewById(R.id.create_message_name);
        message_text = root.findViewById(R.id.create_message_text);
        create_message_button = root.findViewById(R.id.create_message_button);

        create_message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message_name_string = message_name.getText().toString();
                message_text_string = message_text.getText().toString();
                if (Tools.validator(message_name_string, message_text_string)) {
                    writeData();

                } else {
                    Tools.showMessage("Required fields are empty");
                }
            }
        });
        messageRecyclerView();
        readData();
        return root;
    }


    private void writeData() {
        MessageModel messageModel = new MessageModel(message_name_string, message_text_string);

        db.collection("messages").document(mAuth.getCurrentUser().getUid()).collection("user_messages").add(messageModel).addOnSuccessListener(documentReference -> {
            Tools.showMessage("Message created successfully");
        }).addOnFailureListener(e -> {
            Tools.showMessage("Message creation failed");
        });
    }

    private void messageRecyclerView() {
        messageListRecyclerView = root.findViewById(R.id.recycler_message_list);

        messageListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        messageModelArrayList = new ArrayList<>();
        messageAdapter = new MessageAdapter(getActivity(), messageModelArrayList);
        messageListRecyclerView.setAdapter(messageAdapter);
    }


    private void readData() {
        db.collection("messages").document(mAuth.getCurrentUser().getUid()).collection("user_messages").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                messageModelArrayList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    MessageModel messageModel = document.toObject(MessageModel.class);
                    messageModelArrayList.add(messageModel);
                    messageAdapter.notifyDataSetChanged();
                }

            }
        });
    }
}