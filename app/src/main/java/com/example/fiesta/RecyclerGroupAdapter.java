package com.example.fiesta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerGroupAdapter extends RecyclerView.Adapter<RecyclerGroupAdapter.ViewHolder> {


    Context context;
    ArrayList<GroupModel> arrGroups;
    RecyclerGroupAdapter(Context context, ArrayList<GroupModel> arrGroups){
        this.context=context;
        this.arrGroups=arrGroups;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.group_card,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(arrGroups.get(position).img).into(holder.ivImg);
        holder.tvName.setText(arrGroups.get(position).name);
        holder.tvDes.setText(arrGroups.get(position).des);
    }

    @Override
    public int getItemCount() {
        return arrGroups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        TextView tvName,tvDes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImg=itemView.findViewById(R.id.ivImg);
            tvName=itemView.findViewById(R.id.tvName);
            tvDes=itemView.findViewById(R.id.tvDes);
        }
    }
}
