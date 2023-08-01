package com.ojwang.edkins.Home.HomeSubCategory.RecyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.Home.HomeSubCategory.Model.CreditorModel;
import com.ojwang.edkins.Home.HomeSubCategory.Model.StockModel;
import com.ojwang.edkins.R;

import java.util.ArrayList;
import java.util.List;

public class NewStockAdapter extends RecyclerView.Adapter<NewStockAdapter.NewStockHolder> {

    private static List<StockModel> stockModels = new ArrayList<>();
    private static OnItemClickListener listener;

    @NonNull
    @Override
    public NewStockAdapter.NewStockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_card,parent,false);
        return new NewStockAdapter.NewStockHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewStockAdapter.NewStockHolder holder, int position) {
        StockModel currentStock = stockModels.get(position);
        holder.name.setText(currentStock.getProductName());

    }

    @Override
    public int getItemCount() {
        return stockModels.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setStockModels(List<StockModel> stockModels) {
        NewStockAdapter.stockModels = stockModels;
        notifyDataSetChanged();
    }

    public StockModel getCreditorAt(int postion){
        return stockModels.get(postion);
    }

    public static class NewStockHolder extends RecyclerView.ViewHolder {
        private final TextView name;


        public NewStockHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.stockName);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.OnClick(stockModels.get(position),position);
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnClick(StockModel stockModel, int position);
    }
    public void setOnClickListener(OnItemClickListener listener) {
        NewStockAdapter.listener = listener;
    }
}
