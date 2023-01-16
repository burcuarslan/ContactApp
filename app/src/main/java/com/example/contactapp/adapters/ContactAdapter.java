package com.example.contactapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.contactapp.R;
import com.example.contactapp.models.ContactModel;
import com.example.contactapp.models.GroupModel;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    ArrayList<ContactModel> contactModelArrayList;
    RecyclerViewClickListener recyclerViewClickListener;
    public ContactAdapter(ArrayList<ContactModel> contactModelArrayList,RecyclerViewClickListener recyclerViewClickListener) {
        this.contactModelArrayList = contactModelArrayList;
        this.recyclerViewClickListener = recyclerViewClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list,parent,false),recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactModel contactModel = contactModelArrayList.get(position);
        holder.setValues(contactModel);
    }

    @Override
    public int getItemCount() {
        return contactModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView contact_name, contact_number;
        ImageView contact_image;

        RecyclerViewClickListener recyclerViewClickListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
            contact_image = itemView.findViewById(R.id.contact_image);
            this.recyclerViewClickListener = recyclerViewClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            recyclerViewClickListener.recyclerViewListClicked(getAdapterPosition());
        }

        public void setValues(ContactModel contactModel) {

            Glide.with(itemView.getContext()).load(R.drawable.ic_menu_camera).into(contact_image);
            contact_name.setText(contactModel.getContact_name());
            contact_number.setText(contactModel.getContact_number());

        }
    }
}
