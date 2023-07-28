package com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.Home.HomeSubCategory.Model.PaybillModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaybillAdapter extends RecyclerView.Adapter<PaybillAdapter.PaybillHolder> {
    private static List<PaybillModel> paybillNotes = new ArrayList<>();
    private static OnItemClickListener listener;

    @NonNull
    @Override
    public PaybillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paybill_card, parent, false);
        return new PaybillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaybillHolder holder, int position) {
        PaybillModel currentNote = paybillNotes.get(position);
        String status = currentNote.getStatus();
        if (Objects.equals(status, "TILL")){
            holder.tvName.setText(currentNote.getName());
            holder.tvTillPay.setText(currentNote.getTillPay());
            holder.tvAcc.setVisibility(View.GONE);
        } else if (Objects.equals(status, "PAYBILL")) {
            holder.tvName.setText(currentNote.getName());
            holder.tvTillPay.setText(currentNote.getTillPay());
            holder.tvAcc.setVisibility(View.VISIBLE);
            holder.tvAcc.setText(currentNote.getAccNo());
        }
    }

    @Override
    public int getItemCount() {
        return paybillNotes.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPaybillNotes(List<PaybillModel> paybillNotes) {
        PaybillAdapter.paybillNotes = paybillNotes;
        notifyDataSetChanged();
    }

    public PaybillModel getPaybillAt(int position) {
        return paybillNotes.get(position);
    }

    public static class PaybillHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvTillPay;
        private final TextView tvAcc;

        public PaybillHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.pay_heading);
            tvTillPay = itemView.findViewById(R.id.pay_till);
            tvAcc = itemView.findViewById(R.id.pay_acc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnClick(paybillNotes.get(position),position);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void OnClick(PaybillModel paybillModel, int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        PaybillAdapter.listener = listener;
    }

}
