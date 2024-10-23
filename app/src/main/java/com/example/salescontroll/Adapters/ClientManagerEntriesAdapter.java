package com.example.salescontroll.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Entries;

import java.util.ArrayList;
import java.util.List;


public class ClientManagerEntriesAdapter extends RecyclerView.Adapter<ClientManagerEntriesAdapter.EntryItemHolder> {

    private List<Entries> entries = new ArrayList<>();

    @NonNull
    @Override
    public EntryItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View entryView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_adapter, parent, false);
        return new EntryItemHolder(entryView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryItemHolder holder, int position) {
        Entries entry = entries.get(position);
        holder.entryValue.setText(entry.getEnterValue());
        holder.productName.setText(entry.getProductName());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setEntries( List<Entries> newEntries) {
        entries = newEntries;
        notifyDataSetChanged();
    }

    static class EntryItemHolder extends  RecyclerView.ViewHolder {
        TextView productName;
        TextView entryValue;

        public EntryItemHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.client_name_product);
            entryValue = itemView.findViewById(R.id.value_product);
        }
    }





}
