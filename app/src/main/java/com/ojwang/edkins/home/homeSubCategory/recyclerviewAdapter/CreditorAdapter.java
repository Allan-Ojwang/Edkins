package com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.home.homeSubCategory.model.CreditorModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;

public class CreditorAdapter extends RecyclerView.Adapter<CreditorAdapter.CreditorHolder> {
    private static List<CreditorModel>  creditorModels = new ArrayList<>();
    private static OnItemClickListener listener;

    @NonNull
    @Override
    public CreditorAdapter.CreditorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_tracker_card,parent,false);
        return new CreditorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditorHolder holder, int position) {
        CreditorModel currentCreditor = creditorModels.get(position);
        holder.name.setText(currentCreditor.getName());
        holder.reason.setText(currentCreditor.getReason());
        holder.amount.setText(String.format("KSH %s", currentCreditor.getAmount()));


        // check the pstatus and set the color of the TextView accordingly
        if (currentCreditor.ispStatus()) {
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardBg));
        } else {
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return creditorModels.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCreditorModels(List<CreditorModel> creditorModels) {
        CreditorAdapter.creditorModels = creditorModels;
        notifyDataSetChanged();
    }

    public CreditorModel getCreditorAt(int postion){
        return creditorModels.get(postion);
    }

    public static class CreditorHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView reason;
        private final TextView amount;

        public CreditorHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cd_db_name);
            reason= itemView.findViewById(R.id.cd_db_reason);
            amount = itemView.findViewById(R.id.cd_db_amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnClick(creditorModels.get(position),position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnClick(CreditorModel creditorModel, int position);
    }
    public void setOnClickListener(OnItemClickListener listener) {
        CreditorAdapter.listener = listener;
    }
}
