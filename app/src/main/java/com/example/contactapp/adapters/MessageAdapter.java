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

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<MessageModel> messageModelArrayList;
    private Context context;

    public MessageAdapter(){

    }

    public MessageAdapter(Context context, ArrayList<MessageModel> messageModelArrayList) {
        this.messageModelArrayList = messageModelArrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.message_name.setText(messageModelArrayList.get(position).getMessage_name());
        holder.message_text.setText(messageModelArrayList.get(position).getMessage_text());
    }

    @Override
    public int getItemCount() {
        return messageModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message_name, message_text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message_name= itemView.findViewById(R.id.message_name);
            message_text= itemView.findViewById(R.id.message_text);

        }
    }

}
