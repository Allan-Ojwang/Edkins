package com.ojwang.edkins.home.homeSubCategory.recyclerviewAdapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.home.homeSubCategory.model.ToOrderListModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;

public class ToOrderListAdapter extends RecyclerView.Adapter<ToOrderListAdapter.ToOrderListHolder> {

    private static List<ToOrderListModel> orderNotes = new ArrayList<>();

    private static OnItemClickListener listener;

    public ToOrderListAdapter(List<ToOrderListModel> toOrderListModel){
        orderNotes = toOrderListModel;
    }
    @NonNull
    @Override
    public ToOrderListAdapter.ToOrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_order_sub_card,parent,false);
        return new ToOrderListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToOrderListAdapter.ToOrderListHolder holder, int position) {
        ToOrderListModel currentOrder = orderNotes.get(position);
        holder.itemEv.setText(currentOrder.getItem());
        holder.statusCb.setChecked(currentOrder.isoStatus());

        holder.itemEv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentOrder.setItem(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.statusCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentOrder.setoStatus(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderNotes.size();
    }


    public ToOrderListModel getOrderListAt(int postion){
        return orderNotes.get(postion);
    }
    public List<ToOrderListModel> getOrderNotes() {
        return orderNotes;
    }
    public static class ToOrderListHolder extends RecyclerView.ViewHolder {
        private final CheckBox statusCb;
        private final EditText itemEv;
        public ToOrderListHolder(@NonNull View itemView) {
            super(itemView);
            statusCb = itemView.findViewById(R.id.oStatus);
            itemEv = itemView.findViewById(R.id.evTitle);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.OnClick(orderNotes.get(position),position);
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
