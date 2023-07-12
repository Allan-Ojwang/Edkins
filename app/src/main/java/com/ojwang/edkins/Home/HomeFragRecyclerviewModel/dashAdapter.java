package com.ojwang.edkins.Home.HomeFragRecyclerviewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.R;

import java.util.ArrayList;

public class dashAdapter extends RecyclerView.Adapter<dashAdapter.MyViewHolder> {
    private final  DashBtnListener dashBtnListener;
    Context context;
    ArrayList<DashBtnModel> btnArrayList;

    public dashAdapter(Context context, ArrayList<DashBtnModel> btnArrayList, DashBtnListener dashBtnListener) {
        this.context = context;
        this.btnArrayList = btnArrayList;
        this.dashBtnListener = dashBtnListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.dashboard_card_btn,parent,false);
        return new MyViewHolder(v,dashBtnListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DashBtnModel dashBtnModel = btnArrayList.get(position);
        holder.tvheading.setText(dashBtnModel.heading);
        holder.btnDash.setImageResource(dashBtnModel.btnDash);

    }

    @Override
    public int getItemCount() {
        return btnArrayList.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{

        ImageButton btnDash;
        TextView tvheading;

        public MyViewHolder(@NonNull View itemView,DashBtnListener dashBtnListener) {
            super(itemView);
            btnDash = itemView.findViewById(R.id.dash_btn);
            tvheading = itemView.findViewById(R.id.bnt_heading);

            btnDash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dashBtnListener != null){

                        int pos =  getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            dashBtnListener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
