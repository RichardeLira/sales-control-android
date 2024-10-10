package com.example.salescontroll.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Client;
import com.example.salescontroll.viewModel.MainViewModel;
import com.example.salescontroll.viewModel.SearchClientsViewModel;

import java.util.List;

public class SearchClientsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchClientsAdapter searchClientsAdapter;
    private SearchClientsViewModel searchClientsViewModel;
    private ImageView backButton;
    private EditText searchField;
    private TextView warningNotResultsFound;
    private TextView clientDebitValue;

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
        screenInitializerButtons();
        onClickEventListener();
        onClickListenerClient();
        searchEngineLogic();


    }

    private void setUpRecyclerView() {
        this.searchClientsAdapter = new SearchClientsAdapter();
        recyclerView = findViewById(R.id.items_products_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.searchClientsAdapter);
    }

    private void setUpSearchViewModel() {
        searchClientsViewModel = new ViewModelProvider(this).get(SearchClientsViewModel.class);
        searchClientsViewModel.getAllClients().subscribe((List<Client> clients) -> {
            if(clients != null) {
                searchClientsAdapter.setNewClientsSearch(clients);
                computeClientsDebitValue(clients);
            }
        });

    }

    private void screenInitializerButtons() {
        backButton = findViewById(R.id.back_button_on_search_client);
        searchField = findViewById(R.id.insert_client_name_to_search);
        warningNotResultsFound = findViewById(R.id.search_results_not_found_warning);
        clientDebitValue = findViewById(R.id.client_debit_value_on_search);


    }

    private void onClickEventListener() {
        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(SearchClientsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void onClickListenerClient() {
        searchClientsAdapter.setOptionListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Client client, int position) {
                Intent intent = new Intent(SearchClientsActivity.this, ClientManagerActivity.class);
                intent.putExtra("CLIENT_ID", client.getCid());
                startActivity(intent);
                finish();
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void searchEngineLogic() {
        searchField.setOnTouchListener((view, motionEvent) -> {
            searchField.setText("");
            return false;
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("CheckResult")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()) {
                    // Search clients if charSequence is not empty
                    System.out.println("TEXT EDIT NOW" + charSequence.toString());
                    searchClientsViewModel.searchFilter(charSequence.toString())
                            .subscribe((List<Client> searchResult) -> {
                                if (searchResult != null) {
                                    if (searchResult.isEmpty()) {
                                        warningNotResultsFound.setText("Nenhum resultado encontrado");
                                        searchClientsAdapter.setNewClientsSearch(searchResult);
                                    } else {
                                        warningNotResultsFound.setText("");
                                        searchClientsAdapter.setNewClientsSearch(searchResult);
                                    }
                                }
                            });
                } else {
                    // If search is empty
                    searchClientsViewModel.getAllClients().subscribe((List<Client> clients) -> {
                        if(clients != null) {
                            warningNotResultsFound.setText("");
                            searchClientsAdapter.setNewClientsSearch(clients);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void computeClientsDebitValue(List<Client> clients) {
        clientDebitValue.setText(StringToFloat.editStringValueWithDollar(searchClientsViewModel.computeClientDebits(clients)));
    }

}
