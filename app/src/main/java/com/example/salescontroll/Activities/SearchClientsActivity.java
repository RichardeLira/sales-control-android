package com.example.salescontroll.Activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salescontroll.Adapters.MainAdapter;
import com.example.salescontroll.Adapters.SearchClientsAdapter;
import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Client;
import com.example.salescontroll.viewModel.MainViewModel;
import com.example.salescontroll.viewModel.SearchClientsViewModel;

import java.util.List;

public class SearchClientsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchClientsAdapter searchClientsAdapter;
    private SearchClientsViewModel searchClientsViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.client_search_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setUpRecyclerView();
        setUpSearchViewModel();

    }


    private void setUpRecyclerView() {
        this.searchClientsAdapter = new SearchClientsAdapter();
        recyclerView = findViewById(R.id.items_products_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.searchClientsAdapter);
    }


    private void setUpSearchViewModel() {
        searchClientsViewModel = new ViewModelProvider(this).get(SearchClientsViewModel.class);
        searchClientsViewModel.getAllClients().observe(this, new Observer<List<Client>>() {
            @Override
            public void onChanged(List<Client> newClient) {
//                setAmountValue(mainViewModel.getValueForAllClients(newClient, getApplicationContext()));
                searchClientsAdapter.setNewClientsSearch(newClient);
            }
        });
    }

}
