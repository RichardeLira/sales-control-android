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
import androidx.lifecycle.ViewModelProvider;
import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Product;
import com.example.salescontroll.viewModel.InsertNewProductViewModel;
import com.example.salescontroll.viewModel.ProductsViewModel;

public class InsertNewProductActivity extends AppCompatActivity {

    ImageView backButton = null;
    ImageView addNewProductButton = null;
    TextView productName = null;
    TextView productValue = null;
    TextView productQuantity = null;
    TextView payDate = null;
    InsertNewProductViewModel insertNewProductViewModel = null;
    private int actualClientId = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert_new_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.insert_new_product), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        insertNewProductViewModel = new ViewModelProvider(this).get(InsertNewProductViewModel.class);
        actualClientId = getIntent().getIntExtra("CLIENT_ID", -1);
        Toast.makeText(getApplicationContext(), "CLIENT ID RECEBIDO" + actualClientId, Toast.LENGTH_SHORT).show();
        screenInitializerButtons();
        onClickEventListener();

    }

    private void screenInitializerButtons() {
        backButton = findViewById(R.id.back_button_on_add_product);
        addNewProductButton = findViewById(R.id.add_new_product_on_button);
        productQuantity = findViewById(R.id.new_insert_product_quantity);
        productName = findViewById(R.id.new_insert_product_name);
        productValue = findViewById(R.id.new_insert_product_value);
        payDate = findViewById(R.id.new_insert_product_buy_date);

    }

    private void onClickEventListener() {
        backButton.setOnClickListener(view -> {
           Intent intent = new Intent(InsertNewProductActivity.this, ClientManagerActivity.class);
           startActivity(intent);
        });

        addNewProductButton.setOnClickListener(view -> {
            String productQuantityInsert = productQuantity.getText().toString();
            String productNameInsert = productName.getText().toString();
            String productValueInsert = productValue.getText().toString();
            String productBuyDateInsert = payDate.getText().toString();


            if (productQuantityInsert.isEmpty() || productNameInsert.isEmpty() || productValueInsert.isEmpty() || productBuyDateInsert.isEmpty()) {
                Toast.makeText(InsertNewProductActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (actualClientId != -1){
                Product product = new Product();
                product.setProductName(productNameInsert);
                product.setProductQuantity(productQuantityInsert);
                product.setProductValue(productValueInsert);
                product.setProductBuyDate(productBuyDateInsert);

                insertNewProductViewModel.addNewProduct(product, actualClientId);
                Intent intent = new Intent(InsertNewProductActivity.this, ClientManagerActivity.class);
                startActivity(intent);
            }

        });
    }



}