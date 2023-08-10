package com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ojwang.edkins.home.homeSubCategory.model.WorkerModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerHolder> {

    private static List<WorkerModel> workerNotes = new ArrayList<>();
    private static OnItemClickListener listener;
    @NonNull
    @Override
    public WorkerAdapter.WorkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_card,parent,false);
        return new WorkerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerAdapter.WorkerHolder holder, int position) {
        WorkerModel currentWorker = workerNotes.get(position);
        holder.tvName.setText(currentWorker.getWorker_name());
        holder.tvNumber.setText(String.valueOf(currentWorker.getNumber()));
        holder.tvIdNo.setText(String.valueOf(currentWorker.getId_number()));
    }

    @Override
    public int getItemCount() {
        return workerNotes.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setWorkerNotes(List<WorkerModel> workerNotes) {
        WorkerAdapter.workerNotes = workerNotes;
        notifyDataSetChanged();
    }

    public WorkerModel getWorkerAt(int position) {
        return workerNotes.get(position);
    }

    public static class WorkerHolder extends RecyclerView.ViewHolder{

        private final TextView tvName;
        private final TextView tvIdNo;
        private final TextView tvNumber;
        public WorkerHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.worker_name);
            tvIdNo = itemView.findViewById(R.id.worker_id);
            tvNumber = itemView.findViewById(R.id.worker_no);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.OnClick(workerNotes.get(position),position);
                    }
                }
            });

        }
    }
    public interface OnItemClickListener {
        void OnClick(WorkerModel workerModel, int position);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        WorkerAdapter.listener = listener;
    }

}
