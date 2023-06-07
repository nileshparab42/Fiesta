package com.example.fiesta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerTicketpreAdapter extends RecyclerView.Adapter<RecyclerTicketpreAdapter.ViewHolder> {

    private final RecyclerTicketpreInterface recyclerTicketpreInterface;

    Context context;
    ArrayList<PreticketModel> arrPreticket;


    RecyclerTicketpreAdapter(Context context, ArrayList<PreticketModel> arrPreticket, RecyclerTicketpreInterface recyclerTicketpreInterface){
        this.context=context;
        this.arrPreticket=arrPreticket;
        this.recyclerTicketpreInterface=recyclerTicketpreInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_ticketpre,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,recyclerTicketpreInterface);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textEname.setText(arrPreticket.get(position).ename);
        holder.textPrice.setText(arrPreticket.get(position).price);
        holder.textUname.setText(arrPreticket.get(position).uname);
        holder.textUemail.setText(arrPreticket.get(position).uemail);
        holder.textTid.setText(arrPreticket.get(position).tid);
    }

    @Override
    public int getItemCount() {
        return arrPreticket.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView textEname,textPrice,textUname,textUemail,textTid;
        Button tktbtn;

        public ViewHolder(@NonNull View itemView, RecyclerTicketpreInterface recyclerTicketpreInterface) {
            super(itemView);

            textEname = itemView.findViewById(R.id.ename);
            textPrice = itemView.findViewById(R.id.price);
            textUname = itemView.findViewById(R.id.uname);
            textUemail = itemView.findViewById(R.id.uemail);
            textTid = itemView.findViewById(R.id.tid);
            tktbtn = itemView.findViewById(R.id.tktbtn);

            tktbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerTicketpreInterface!=null){
                        int pos = getAdapterPosition();

                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerTicketpreInterface.onTktbtnClick(pos);
                        }
                    }
                }
            });

        }
    }
}
