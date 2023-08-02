package com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StoreModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;

public class NewStoreAdapter extends RecyclerView.Adapter<NewStoreAdapter.NewStockHolder> {

    private static List<StoreModel> storeModels = new ArrayList<>();
    private static OnItemClickListener listener;

    @NonNull
    @Override
    public NewStoreAdapter.NewStockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_card,parent,false);
        return new NewStoreAdapter.NewStockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewStoreAdapter.NewStockHolder holder, int position) {
        StoreModel currentStore = storeModels.get(position);
        holder.name.setText(currentStore.getStoreName());

    }

    @Override
    public int getItemCount() {
        return storeModels.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setStoreModels(List<StoreModel> storeModels) {
        NewStoreAdapter.storeModels = storeModels;
        notifyDataSetChanged();
    }

    public StoreModel getStoreAt(int postion){
        return storeModels.get(postion);
    }

    public static class NewStockHolder extends RecyclerView.ViewHolder {
        private final TextView name;


        public NewStockHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.storeName);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.OnClick(storeModels.get(position),position);
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnClick(StoreModel storeModel, int position);
    }
    public void setOnClickListener(OnItemClickListener listener) {
        NewStoreAdapter.listener = listener;
    }
}
