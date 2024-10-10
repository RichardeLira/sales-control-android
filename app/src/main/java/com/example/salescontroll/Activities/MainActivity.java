package com.example.salescontroll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salescontroll.Adapters.MainAdapter;
import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Client;
import com.example.salescontroll.viewModel.MainViewModel;

import java.util.List;



public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private MainViewModel mainViewModel;
    private ImageView newClientButton;
    private ImageView searchButton;
    private TextView finalValueOfDebitClients = null;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        String amountValue = getIntent().getStringExtra("CLIENT_AMOUNT_VALUE");
        int clientId = getIntent().getIntExtra("CLIENT_ID", -1);

        setUpRecyclerView();
        screenInitializerButtons();
        onClickEventListener();
        onClickListenerClient();
        setUpClientViewModel();

        if (amountValue != null && clientId != -1) {
            loadClientData(clientId, amountValue);
        }

    }

    private void setUpRecyclerView() {
        this.mainAdapter = new MainAdapter();
        recyclerView = findViewById(R.id.items_products_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.mainAdapter);
    }

    private void screenInitializerButtons() {
        newClientButton = findViewById(R.id.addNewClientButton);
        searchButton = findViewById(R.id.search_button_on_main_activity);
        finalValueOfDebitClients = findViewById(R.id.home_amount_value);


    }

    private void onClickEventListener() {
        newClientButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewClientActivity.class);
            startActivity(intent);
        });

        searchButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchClientsActivity.class);
            startActivity(intent);
        });
    }

    public void loadClientData(int clientId, String amountValue) {
        mainViewModel.getClientByIdSingle(clientId)
                .subscribe(client -> {
                    if (client != null) {
                        client.setDebitValue(amountValue);
                        mainViewModel.updateClientValueDebit(client)
                                .subscribe(() -> {
                                    Toast.makeText(getApplicationContext(), "Client Updated: " + client.getFullName(), Toast.LENGTH_SHORT).show();
                                }, throwable -> {
                                    Toast.makeText(getApplicationContext(), "Error updating client: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                }, throwable -> {
                    Toast.makeText(getApplicationContext(), "Error fetching client: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void setAmountValue(String amountValue){
        finalValueOfDebitClients.setText(StringToFloat.editStringValueWithDollar(amountValue));
    }

    private void setUpClientViewModel() {

        mainViewModel.getAllClients().subscribe((List<Client> clients) -> {
            if (clients != null) {
                mainAdapter.setClients(clients);
                setAmountValue(mainViewModel.getValueForAllClients(clients));
            }
        }, throwable -> {
            Toast.makeText(getApplicationContext(), "Error fetching client: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        } );

    }

    private void onClickListenerClient() {
        mainAdapter.setOptionListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Client client, int position) {
                Intent intent = new Intent(MainActivity.this, ClientManagerActivity.class);
                intent.putExtra("CLIENT_ID", client.getCid());
                startActivity(intent);
            }
        });
    }


}
