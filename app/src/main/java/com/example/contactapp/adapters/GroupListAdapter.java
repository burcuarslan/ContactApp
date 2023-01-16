package com.example.contactapp.adapters;

import android.content.Context;
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

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    ArrayList<GroupModel> groupModelArrayList;
    Context context;
    RecyclerViewClickListener listener;
    public GroupListAdapter() {

    }

    public GroupListAdapter(Context context, ArrayList<GroupModel> groupModelArrayList) {
        this.groupModelArrayList = groupModelArrayList;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GroupListAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        if (groupModelArrayList.get(position).getGroup_image() != null) {
//
//        }
//        else{
//            holder.group_image.setImageResource(R.drawable.ic_menu_gallery);
//        }
        Glide.with(context).load(groupModelArrayList.get(position).getGroup_image()).into(holder.group_image);
        holder.group_name.setText(groupModelArrayList.get(position).getGroup_name());
        holder.group_desc.setText(groupModelArrayList.get(position).getGroup_description());
    }

    @Override
    public int getItemCount() {
        return groupModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView group_name, group_desc;
        ImageView group_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            group_name = itemView.findViewById(R.id.group_name);
            group_desc = itemView.findViewById(R.id.group_desc);
            group_image = itemView.findViewById(R.id.group_image);

        }


    }

}

