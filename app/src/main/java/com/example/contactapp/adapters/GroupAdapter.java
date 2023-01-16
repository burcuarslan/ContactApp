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
import com.example.contactapp.models.GroupModel;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    ArrayList<GroupModel> groupModelArrayList;
    RecyclerViewClickListener recyclerViewClickListener;

    public GroupAdapter(ArrayList<GroupModel> groupModelArrayList, RecyclerViewClickListener recyclerViewClickListener) {
        this.groupModelArrayList = groupModelArrayList;
        this.recyclerViewClickListener = recyclerViewClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_h_list, parent, false), recyclerViewClickListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupModel groupModel = groupModelArrayList.get(position);
        holder.setValues(groupModel);
    }

    @Override
    public int getItemCount() {
        return groupModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView group_name, group_desc;
        ImageView group_image;

        RecyclerViewClickListener recyclerViewClickListener;

        public ViewHolder(@NonNull View itemView, RecyclerViewClickListener recyclerViewClickListener) {
            super(itemView);
            group_name = itemView.findViewById(R.id.group_name_item);
            group_desc = itemView.findViewById(R.id.group_desc_item);
            group_image = itemView.findViewById(R.id.group_image_item);
            this.recyclerViewClickListener = recyclerViewClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            recyclerViewClickListener.recyclerViewListClicked(getAdapterPosition());
        }

        public void setValues(GroupModel groupModel) {

            Glide.with(itemView.getContext()).load(groupModel.getGroup_image()).into(group_image);
            group_name.setText(groupModel.getGroup_name());
            group_desc.setText(groupModel.getGroup_description());

        }
    }
}
