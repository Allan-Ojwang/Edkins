package com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerDebtModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.WorkerModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;

public class WorkerDebtAdapter extends RecyclerView.Adapter<WorkerDebtAdapter.WorkerDebtHolder> {

    private static List<WorkerDebtModel> workerDebtNotes = new ArrayList<>();
    private static OnItemClickListener listener;
    @NonNull
    @Override
    public WorkerDebtAdapter.WorkerDebtHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_debt_card,parent,false);
        return new WorkerDebtHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerDebtAdapter.WorkerDebtHolder holder, int position) {
        WorkerDebtModel currentWorkerDebt = workerDebtNotes.get(position);
        holder.tvDate.setText(currentWorkerDebt.getDate());
        holder.tvAmount.setText(String.format("KSH %s", currentWorkerDebt.getAmount()));
    }

    @Override
    public int getItemCount() {
        return workerDebtNotes.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setWorkerDebtNotes(List<WorkerDebtModel> workerDebtNotes) {
        WorkerDebtAdapter.workerDebtNotes = workerDebtNotes;
        notifyDataSetChanged();
    }

    public WorkerDebtModel getWorkerDebtAt(int position) {
        return workerDebtNotes.get(position);
    }

    public static class WorkerDebtHolder extends RecyclerView.ViewHolder{
        private final TextView tvDate;
        private final TextView tvAmount;


        public WorkerDebtHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.date);
            tvAmount = itemView.findViewById(R.id.amount);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnClick(workerDebtNotes.get(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnClick(WorkerDebtModel workerDebtModel, int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        WorkerDebtAdapter.listener = listener;
    }
}
