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
import com.example.salescontroll.entitys.Product;

import java.util.ArrayList;
import java.util.List;

public class ClientManagerProductsAdapter extends RecyclerView.Adapter<ClientManagerProductsAdapter.ProductItemHolder> {

    private List<Product> products = new ArrayList<>();

    @NonNull
    @Override
    public ProductItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_adapter, parent, false);
        return new ProductItemHolder(productView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItemHolder holder, int position) {
        Product product = products.get(position);
        holder.productValue.setText("-R$ " + product.getProductValue());
        holder.itemProductTextView.setText(product.getProductName());


    }

    @Override
    public int getItemCount() {
        return this.products.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProducts(List<Product> newProduct) {
        if (newProduct != null)  {
            this.products = newProduct;
        }
        notifyDataSetChanged();
    }

    static class ProductItemHolder extends RecyclerView.ViewHolder {
        TextView itemProductTextView;
        TextView productValue;

        public ProductItemHolder(@NonNull View productItemView) {
            super(productItemView);
            itemProductTextView = productItemView.findViewById(R.id.client_name_product);
            productValue = productItemView.findViewById(R.id.value_product);
        }
    }
}
