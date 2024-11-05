package com.example.salescontroll.Activities;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.salescontroll.Adapters.ClientManagerEntriesAdapter;
import com.example.salescontroll.Adapters.ClientManagerProductsAdapter;

import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Client;
import com.example.salescontroll.entitys.Entries;
import com.example.salescontroll.entitys.Product;

import com.example.salescontroll.viewModel.ClientManagerViewModel;

import java.util.List;

public class ClientManagerActivity extends AppCompatActivity {

    // Componentes e variáveis da tela
    private ImageView backButton = null;
    private TextView clientName = null;
    private ImageView insertNewProductButton = null;
    private ImageView InsertNewProductButtonBackGroundImage = null;
    private ImageView productsButton = null;
    private ImageView enterButton = null;
    private TextView productOrEnterText = null;
    // View model
    private ClientManagerViewModel clientManagerViewModel = null;
    // Adapters
    private ClientManagerProductsAdapter clientManagerProductsAdapter;
    private ClientManagerEntriesAdapter clientManagerEntriesAdapter;
    private RecyclerView recyclerView;
    private TextView clientDebitValueAmount;
    private String amountValue = null;
    // Variables controller
    private int clientId = -1;
    private boolean buttonSelectEntryOrProduct = false;
    // Product data
    private  List<Product> products;
    // Entry data
    private List<Entries> entries;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.client_info), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializando o ViewModel
        clientManagerViewModel = new ViewModelProvider(this).get(ClientManagerViewModel.class);

        // Recuperando o ID do cliente da Intent e carregando os dados
        clientId = getIntent().getIntExtra("CLIENT_ID", -1);
        Toast.makeText(this, "ID" + clientId, Toast.LENGTH_SHORT).show();

        if (clientId != -1) {
            clientManagerViewModel.setLastUserIdAdded(clientId);
            loadClientData(clientId);
        } else {
            loadClientData(clientManagerViewModel.getLastUserIdAdded());
        }


        setUpClientViewModel();
        setUpRecyclerView();
        screenInitializerButtons();
        onClickEventListener();
        onClickListenerProduct();
    }

    public void screenInitializerButtons() {
        backButton = findViewById(R.id.back_button_on_manager_client);
        clientName = findViewById(R.id.client_name_on_screen_manager);
        enterButton = findViewById(R.id.cache_button_on_screen_client);
        productsButton = findViewById(R.id.products_button_on_screen_client);
        productOrEnterText = findViewById(R.id.product_or_enter_name_screen_client);
        insertNewProductButton = findViewById(R.id.on_new_product_insert_button);
        InsertNewProductButtonBackGroundImage =  findViewById(R.id.button_product_or_entry_add);
        clientDebitValueAmount = findViewById(R.id.client_debit_value);
    }

    public void onClickEventListener() {

        enterButton.setOnClickListener(view -> {
            productOrEnterText.setText("Entradas");
            recyclerView.setAdapter(clientManagerEntriesAdapter);
            clientManagerEntriesAdapter.setEntries(this.entries);
            InsertNewProductButtonBackGroundImage.setImageResource(R.drawable.card_tick);
            buttonSelectEntryOrProduct = false;
        });

        productsButton.setOnClickListener(view -> {
            productOrEnterText.setText("Produtos");
            recyclerView.setAdapter(clientManagerProductsAdapter);
            clientManagerProductsAdapter.setProducts(this.products);
            InsertNewProductButtonBackGroundImage.setImageResource(R.drawable.cart_add_solid);
            buttonSelectEntryOrProduct = true;
        });

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(ClientManagerActivity.this, MainActivity.class);
            intent.putExtra("CLIENT_AMOUNT_VALUE", this.amountValue);
            intent.putExtra("CLIENT_ID", this.clientId);
            startActivity(intent);
            finish();

        });

        insertNewProductButton.setOnClickListener(view -> {
            Intent intent;
            if (buttonSelectEntryOrProduct) {
                intent = new Intent(ClientManagerActivity.this, InsertNewProductActivity.class);
            } else {
                intent = new Intent(ClientManagerActivity.this, AddNewEntryActivity.class);
            }
            intent.putExtra("CLIENT_ID", clientManagerViewModel.getLastUserIdAdded());
            startActivity(intent);
            finish();


        });
    }


    public void loadClientData(int clientId) {
        clientManagerViewModel.getClientById(clientId).observe(this, new Observer<Client>() {
            @Override
            public void onChanged(Client client) {
                if (client != null) {
                    String name = client.getFullName();
                    clientName.setText(name);
                } else {
                    Toast.makeText(getApplicationContext(), "Cliente não encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpRecyclerView() {
        clientManagerProductsAdapter = new ClientManagerProductsAdapter();
        clientManagerEntriesAdapter  = new ClientManagerEntriesAdapter();
        recyclerView = findViewById(R.id.items_products_recycler_view_client_manager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(clientManagerProductsAdapter);
        clientManagerProductsAdapter.setProducts(this.products);

    }

    private void setValueAmount(String amountValue) {
        clientDebitValueAmount.setText(StringToFloat.editStringValueWithDollar(amountValue));
        this.amountValue = amountValue;

    }

    @SuppressLint("CheckResult")
    private void setUpClientViewModel() {

        clientManagerViewModel.getAllProductsByUser(clientManagerViewModel.getLastUserIdAdded()).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> newProduct) {
                setValueAmount(clientManagerViewModel.getAmountProductValue(newProduct));
                products = newProduct;
            }
        });

         clientManagerViewModel.getAllEntriesByUser(clientManagerViewModel.getLastUserIdAdded())
                 .subscribe(entries -> {
                     if (entries != null) {
                         this.entries = entries;
                     }
                 }, throwable -> {
                     Toast.makeText(getApplicationContext(), "Error fetching entries for client: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                 });
    }




    private void onClickListenerProduct() {
        clientManagerProductsAdapter.setOptionListener(new ClientManagerProductsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product, int position) {
                Intent intent = new Intent(ClientManagerActivity.this, AddNewEntryActivity.class);
                intent.putExtra("CLIENT_ID", product.getClientId());
                intent.putExtra("PRODUCT_ID", product.getpId());
                startActivity(intent);
            }
        });
    }


}
