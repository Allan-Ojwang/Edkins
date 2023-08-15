package com.ojwang.edkins.search.recyclerviewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ojwang.edkins.R;
import com.ojwang.edkins.home.homeSubCategory.model.StockWithQuantityModel;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private static List<StockWithQuantityModel> stockModels = new ArrayList<>();
    private static OnItemClickListener listener;

    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_stock_card,parent,false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
        StockWithQuantityModel currentStock = stockModels.get(position);
        holder.name.setText(currentStock.getProductName());
        holder.quantity.setText(String.valueOf(currentStock.getTotalQuantity()));
        holder.buyingPrice.setText(String.format("KSH %s",currentStock.getSellingPrice()));
    }

    @Override
    public int getItemCount() {
        return stockModels.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setStockModels(List<StockWithQuantityModel> stockModels) {
        SearchAdapter.stockModels = stockModels;
        notifyDataSetChanged();
    }

    public StockWithQuantityModel getStockAt(int postion){
        return stockModels.get(postion);
    }

    public static class SearchHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView buyingPrice;
        private final TextView quantity;

        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.stockName);
            buyingPrice = itemView.findViewById(R.id.stockSp);
            quantity = itemView.findViewById(R.id.stockQuantity);


            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.OnClick(stockModels.get(position),position);
                }
            });
        }
    }
    public interface OnItemClickListener{
        void OnClick(StockWithQuantityModel stockModel, int position);
    }
    public void setOnClickListener(OnItemClickListener listener) {
        SearchAdapter.listener = listener;
    }
}
