package com.example.salescontroll.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Client;

import java.util.ArrayList;
import java.util.List;

public class SearchClientsAdapter  extends RecyclerView.Adapter<SearchClientsAdapter.SearchClientItemHolder> {

    private MainAdapter.OnItemClickListener optionListener;
    private List<Client> clientsSearch;

    public SearchClientsAdapter() {
        clientsSearch = new ArrayList<>();
    }


    @NonNull
    @Override
    public SearchClientItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_adapter, parent, false);

        return new SearchClientsAdapter.SearchClientItemHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchClientItemHolder holder, int position) {
        holder.itemProductTextView.setText(this.clientsSearch.get(position).getFullName());
        holder.productValue.setText(StringToFloat.editStringValueWithDollar(this.clientsSearch.get(position).getDebitValue()));

        holder.buttonSelectClient.setOnClickListener(view -> {
            optionListener.onItemClick(clientsSearch.get(position), position);

        });
    }

    @Override
    public int getItemCount() {
        return clientsSearch.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNewClientsSearch(List<Client> newClientsSearch) {
        this.clientsSearch = newClientsSearch;
        notifyDataSetChanged();
    }

    static class SearchClientItemHolder extends RecyclerView.ViewHolder {
        TextView itemProductTextView;
        FrameLayout buttonSelectClient;
        TextView productValue;


        public SearchClientItemHolder(@NonNull View productItemView) {
            super(productItemView);
            productValue = productItemView.findViewById(R.id.value_product);
            itemProductTextView = productItemView.findViewById(R.id.client_name_product);
            buttonSelectClient = productItemView.findViewById(R.id.client_button_on_recycle_view);

        }

    }

    public interface OnItemClickListener {
        void onItemClick(Client client, int position);
    }


    public void setOptionListener(MainAdapter.OnItemClickListener optionListener) {
        this.optionListener = optionListener;
    }
}
