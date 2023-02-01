package com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter;

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

public class PaybillAdapter extends RecyclerView.Adapter<PaybillAdapter.PaybillHolder>{
    private List<PaybillModel> paybillNotes = new ArrayList<>();
    @NonNull
    @Override
    public PaybillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paybill_card,parent,false);
        return new PaybillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaybillHolder holder, int position) {
        PaybillModel currentNote = paybillNotes.get(position);
        holder.tvTitle.setText(currentNote.getTitle());
        holder.tvBody.setText(currentNote.getBody());
    }

    @Override
    public int getItemCount() {
        return paybillNotes.size();
    }
    public void setPaybillNotes(List<PaybillModel> paybillNotes){
        this.paybillNotes = paybillNotes;
        notifyDataSetChanged();
    }
    class PaybillHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private TextView tvBody;

        public PaybillHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.pay_heading);
            tvBody = itemView.findViewById(R.id.pay_body);

        }
    }

}
