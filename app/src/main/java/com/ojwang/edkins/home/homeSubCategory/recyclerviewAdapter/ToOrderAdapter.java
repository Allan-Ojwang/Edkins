package com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.home.homeSubCategory.model.ToOrderModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToOrderAdapter extends RecyclerView.Adapter<ToOrderAdapter.ToOrderHolder> {

    private static List<ToOrderModel> orderNotes = new ArrayList<>();
    private static OnItemClickListener listener;

    @NonNull
    @Override
    public ToOrderAdapter.ToOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_order_card,parent,false);
        return new ToOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToOrderAdapter.ToOrderHolder holder, int position) {
        ToOrderModel currentOrder = orderNotes.get(position);
        holder.tvYear.setText(String.valueOf(currentOrder.getYear()));
        holder.tvDate.setText(String.valueOf(currentOrder.getDate()));
        holder.tvMonth.setText(String.valueOf(currentOrder.getMonth()));
        String formattedID = "#" + currentOrder.getOrderId();
        holder.tvId.setText(formattedID);
        holder.tvStatus.setText(currentOrder.getStatus());

        if (Objects.equals(currentOrder.getStatus(), "Processing")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.text));
        } else if (Objects.equals(currentOrder.getStatus(), "Done")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardBg));
        } else if (Objects.equals(currentOrder.getStatus(), "NA")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.white));
        }        else if (Objects.equals(currentOrder.getStatus(), "Over Due")) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return orderNotes.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setOrderNotes(List<ToOrderModel> toOrderNotes) {
        ToOrderAdapter.orderNotes = toOrderNotes;
        notifyDataSetChanged();
    }

    public ToOrderModel getOrderAt(int postion){
        return orderNotes.get(postion);
    }
    public static class ToOrderHolder extends RecyclerView.ViewHolder {
        private final TextView tvYear,tvDate,tvMonth,tvId,tvStatus;
        public ToOrderHolder(@NonNull View itemView) {
            super(itemView);

            tvYear = itemView.findViewById(R.id.year);
            tvDate = itemView.findViewById(R.id.date);
            tvMonth = itemView.findViewById(R.id.month);
            tvId = itemView.findViewById(R.id.orderId);
            tvStatus = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnClick(orderNotes.get(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnClick(ToOrderModel toOrderModel,int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        ToOrderAdapter.listener= listener;
    }
}
