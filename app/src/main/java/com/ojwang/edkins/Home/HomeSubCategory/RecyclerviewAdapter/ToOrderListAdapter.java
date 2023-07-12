package com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.Home.HomeSubCategory.Model.ToOrderListModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;

public class ToOrderListAdapter extends RecyclerView.Adapter<ToOrderListAdapter.ToOrderListHolder> {

    private static List<ToOrderListModel> orderNotes = new ArrayList<>();
    private static OnItemClickListener listener;
    @NonNull
    @Override
    public ToOrderListAdapter.ToOrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_order_sub_card,parent,false);
        return new ToOrderListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToOrderListAdapter.ToOrderListHolder holder, int position) {
        ToOrderListModel currentOrder = orderNotes.get(position);
        holder.tvId.setText(String.valueOf(currentOrder.getId()));
        holder.tvItem.setText(currentOrder.getItem());
        holder.tvQuantity.setText(String.valueOf(currentOrder.getQuantity()));

        if (currentOrder.isoStatus()){
            holder.tvId.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardBg));
            holder.tvItem.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardBg));
            holder.tvQuantity.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardBg));
        } else {
            holder.tvId.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.red));
            holder.tvItem.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.red));
            holder.tvQuantity.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return orderNotes.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setOrderNotes(List<ToOrderListModel> toOrderListNotes) {
        ToOrderListAdapter.orderNotes = toOrderListNotes;
        notifyDataSetChanged();
    }

    public ToOrderListModel getOrderListAt(int postion){
        return orderNotes.get(postion);
    }
    public static class ToOrderListHolder extends RecyclerView.ViewHolder {

        private final TextView tvId,tvItem,tvQuantity;
        public ToOrderListHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.orderId);
            tvItem = itemView.findViewById(R.id.item);
            tvQuantity = itemView.findViewById(R.id.quantity);

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
        void OnClick(ToOrderListModel toOrderListModel, int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        ToOrderListAdapter.listener= listener;
    }
}
