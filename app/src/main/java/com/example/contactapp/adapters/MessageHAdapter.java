package com.example.contactapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactapp.R;
import com.example.contactapp.models.MessageModel;

import java.util.ArrayList;

public class MessageHAdapter extends RecyclerView.Adapter<MessageHAdapter.ViewHolder> {

    private ArrayList<MessageModel> messageModelArrayList;
    RecyclerViewClickListener recyclerViewClickListener;

    public MessageHAdapter(){

    }

    public MessageHAdapter(ArrayList<MessageModel> messageModelArrayList, RecyclerViewClickListener recyclerViewClickListener) {
        this.messageModelArrayList = messageModelArrayList;
        this.recyclerViewClickListener = recyclerViewClickListener;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageHAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_h_list,parent,false),recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       MessageModel messageModel = messageModelArrayList.get(position);
       holder.setValue(messageModel);
    }

    @Override
    public int getItemCount() {
        return messageModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView message_name, message_text;
        RecyclerViewClickListener recyclerViewClickListener;
        public ViewHolder(@NonNull View itemView,RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            message_name= itemView.findViewById(R.id.message_name_item);
            message_text= itemView.findViewById(R.id.message_text_item);
            this.recyclerViewClickListener = recyclerViewClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.recyclerViewListClicked(getAdapterPosition());
        }

        public void setValue(MessageModel messageModel){
            message_name.setText(messageModel.getMessage_name());
            message_text.setText(messageModel.getMessage_text());
        }
    }

}
