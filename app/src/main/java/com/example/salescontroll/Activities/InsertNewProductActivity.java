package com.example.salescontroll.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Product;
import com.example.salescontroll.viewModel.InsertNewProductViewModel;

import java.text.NumberFormat;
import java.util.Locale;



public class InsertNewProductActivity extends AppCompatActivity {

    private ImageView backButton = null;
    private ImageView addNewProductButton = null;
    private TextView productName = null;
    private EditText productValue = null;
    private TextView productQuantity = null;
    private TextView payDate = null;
    private InsertNewProductViewModel insertNewProductViewModel = null;
    private int actualClientId = -1;
    private Calendar calendar = Calendar.getInstance();
    private double valueInsertOnDbProduct;

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


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };


        payDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(InsertNewProductActivity.this, date, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void updateLabel(){
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        payDate.setText(dateFormat.format(calendar.getTime()));
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

        textValueParsed();

        backButton.setOnClickListener(view -> {
           Intent intent = new Intent(InsertNewProductActivity.this, ClientManagerActivity.class);
           startActivity(intent);
        });

        addNewProductButton.setOnClickListener(view -> {
            String productQuantityInsert = productQuantity.getText().toString();
            String productNameInsert = productName.getText().toString();
            String productValueInsert = Double.toString(valueInsertOnDbProduct);
            String productBuyDateInsert = payDate.getText().toString();


            if (productQuantityInsert.isEmpty() || productNameInsert.isEmpty() || productValueInsert.isEmpty() || productBuyDateInsert.isEmpty()) {
                Toast.makeText(InsertNewProductActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else if (productQuantityInsert.equals(String.valueOf(0))) {
                Toast.makeText(InsertNewProductActivity.this, "Quantidade não pode ser 0 ", Toast.LENGTH_SHORT).show();
            } else if (valueInsertOnDbProduct == 0) {
                Toast.makeText(InsertNewProductActivity.this, "Insira um valor para seu produto", Toast.LENGTH_SHORT).show();
            } else if (actualClientId != -1){
                Product product = new Product();
                product.setProductName(productNameInsert);
                product.setProductQuantity(productQuantityInsert);
                product.setProductValue(productValueInsert);
                product.setProductBuyDate(productBuyDateInsert);

                insertNewProductViewModel.addNewProduct(product, actualClientId);
                Intent intent = new Intent(InsertNewProductActivity.this, ClientManagerActivity.class);
                startActivity(intent);
                finish();
            }

        });
    }

    private void textValueParsed() {
        // fixed code
        productValue.addTextChangedListener(new TextWatcher() {

            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    productValue.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.\\s]", "");

                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }

                    valueInsertOnDbProduct = parsed;
                    String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed / 100));

                    current = formatted;
                    productValue.setText(formatted);
                    productValue.setSelection(formatted.length());

                    productValue.addTextChangedListener(this);
                }
            }
        });


    }






}