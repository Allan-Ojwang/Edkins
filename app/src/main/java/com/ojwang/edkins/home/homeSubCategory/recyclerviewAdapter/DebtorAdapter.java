package com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.home.homeSubCategory.model.DebtorModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;

public class DebtorAdapter extends RecyclerView.Adapter<DebtorAdapter.DebtorHolder> {

    private static List<DebtorModel> debtorModels = new ArrayList<>();
    private static OnItemClickListener listener;

    @NonNull
    @Override
    public DebtorAdapter.DebtorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.debt_tracker_card,parent,false);
        return new DebtorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtorAdapter.DebtorHolder holder, int position) {
        DebtorModel currentDebtor = debtorModels.get(position);
        holder.name.setText(currentDebtor.getName());
        holder.reason.setText(currentDebtor.getReason());
        holder.amount.setText(String.format("KSH %s", currentDebtor.getAmount()));


        // check the pstatus and set the color of the TextView accordingly
        if (currentDebtor.ispStatus()) {
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.cardBg));
        } else {
            holder.amount.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.red));
        }

    }

    @Override
    public int getItemCount() {
        return debtorModels.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDebtorModels(List<DebtorModel> debtorModels) {
        DebtorAdapter.debtorModels = debtorModels;
        notifyDataSetChanged();
    }

    public DebtorModel getDebtorAt(int postion){
        return debtorModels.get(postion);
    }

    public static class DebtorHolder extends RecyclerView.ViewHolder  {
        private final TextView name;
        private final TextView reason;
        private final TextView amount;

        public DebtorHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cd_db_name);
            reason= itemView.findViewById(R.id.cd_db_reason);
            amount = itemView.findViewById(R.id.cd_db_amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnClick(debtorModels.get(position),position);
                    }
                }
            });

        }
    }
    public interface OnItemClickListener{
        void OnClick(DebtorModel debtorModel, int position);
    }
    public void setOnClickListener(OnItemClickListener listener) {
        DebtorAdapter.listener = listener;
    }
}
