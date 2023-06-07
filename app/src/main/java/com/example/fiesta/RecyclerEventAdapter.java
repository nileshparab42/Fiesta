package com.example.fiesta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerEventAdapter extends RecyclerView.Adapter<RecyclerEventAdapter.ViewHolder> {

    private final RecyclerEventInterface recyclerEventInterface;

    Context context;
    ArrayList<EventModel> arrEvent;



    RecyclerEventAdapter(Context context, ArrayList<EventModel> arrEvent, RecyclerEventInterface recyclerEventInterface){
        this.context=context;
        this.arrEvent=arrEvent;
        this.recyclerEventInterface = recyclerEventInterface;
    }

    @NonNull
    @Override
    public RecyclerEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, recyclerEventInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerEventAdapter.ViewHolder holder, int position) {
        Picasso.get().load(arrEvent.get(position).img).into(holder.imgEvent);
        holder.textName.setText(arrEvent.get(position).name);
        holder.textShdes.setText(arrEvent.get(position).shdes);
        holder.textPrice.setText(arrEvent.get(position).Price);
    }

    @Override
    public int getItemCount() {
        return arrEvent.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textShdes,textName,textPrice;
        ImageView imgEvent;
        Button paybtn;
        public ViewHolder(@NonNull View itemView, RecyclerEventInterface recyclerEventInterface) {
            super(itemView);
            textName = itemView.findViewById(R.id.name);
            textShdes = itemView.findViewById(R.id.shdes);
            imgEvent = itemView.findViewById(R.id.imgEvent);
            textPrice = itemView.findViewById(R.id.textPrice);
            paybtn = itemView.findViewById(R.id.paybtn);

            paybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerEventInterface!=null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            recyclerEventInterface.onBtnClick(pos);
                        }
                    }
                }
            });
        }
    }
}
