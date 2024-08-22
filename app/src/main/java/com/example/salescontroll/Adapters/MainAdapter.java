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

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ProductItemHolder> {
    private List<Client> clients;
    private OnItemClickListener optionListener;
    public MainAdapter() {
        this.clients = new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_adapter, parent, false);

        return new ProductItemHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder holder, int position) {
        holder.itemProductTextView.setText(this.clients.get(position).getFullName());
        holder.productValue.setText(StringToFloat.editStringValueWithDollar(this.clients.get(position).getDebitValue()));

        holder.buttonSelectClient.setOnClickListener(view -> {
            optionListener.onItemClick(clients.get(position), position);

        });

    }

    @Override
    public int getItemCount() {
        return this.clients.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setClients(List<Client> newClientsData) {
        this.clients = newClientsData;
        notifyDataSetChanged();
    }

    static class ProductItemHolder extends RecyclerView.ViewHolder {
        TextView itemProductTextView;
        FrameLayout buttonSelectClient;
        TextView productValue;


        public ProductItemHolder(@NonNull View productItemView) {
            super(productItemView);
            productValue = productItemView.findViewById(R.id.value_product);
            itemProductTextView = productItemView.findViewById(R.id.client_name_product);
            buttonSelectClient = productItemView.findViewById(R.id.client_button_on_recycle_view);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Client client, int position);
    }

    public void setOptionListener(OnItemClickListener optionListener) {
        this.optionListener = optionListener;
    }


}
