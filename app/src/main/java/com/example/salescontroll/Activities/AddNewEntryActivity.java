package com.example.salescontroll.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.R;
import com.example.salescontroll.entitys.Entries;
import com.example.salescontroll.entitys.Product;
import com.example.salescontroll.viewModel.EntryViewModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;




public class AddNewEntryActivity extends AppCompatActivity {

    // paymentsMethods const
    private final String[] paymentsMethods = {"pix", "crédito", "dinheiro", "outro"};
    private AutoCompleteTextView autoCompleteTextViewPaymentType;
    private AutoCompleteTextView autoCompleteTextViewProductChoice;
    private EditText valueEntry;
    private Date dateEntry;
    private ArrayAdapter<String> adapterPaymentType;
    private ArrayAdapter<String> adapterProductSelect;
    // View model
    private EntryViewModel entryViewModel;
    // Control ID
    private int clientId = -1;
    private int productId = -1;
    // Screen fields
    private EditText entryValue;
    private TextView entryDateText;
    private TextView clientProductName;
    private ImageView entryButtonConfirm;
    private ImageView backButtonActivity;
    private TextView remainingValue;
    // Client information
    private String productDebitValue;
    // Calendar config
    private Calendar entryDate = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_client_add_entry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.client_new_entry), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().getIntExtra("CLIENT_ID", -1) != -1 && getIntent().getIntExtra("PRODUCT_ID", -1) != -1) {
            this.clientId = getIntent().getIntExtra("CLIENT_ID", -1);
            this.productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        } else {
            Toast.makeText(getApplicationContext(), "Erro ao carregar dados do client", Toast.LENGTH_SHORT).show();
        }

        entryViewModel = new ViewModelProvider(this).get(EntryViewModel.class);

        // Start up application
        dropDownConfig();
        userInformation();
        UiInitializer();

    }

    private void UiInitializer() {
        // Text and input elements initializer
        entryValue = findViewById(R.id.insert_entry_value);
        entryDateText = findViewById(R.id.insert_entry_date);
        clientProductName = findViewById(R.id.client_product_on_entry_screen);
        entryButtonConfirm = findViewById(R.id.add_new_client_entry);
        backButtonActivity = findViewById(R.id.back_button_on_entry_screen);
        remainingValue = findViewById(R.id.client_debit_value_on_screen_entry);


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                entryDate.set(Calendar.YEAR, year);
                entryDate.set(Calendar.MONTH, month);
                entryDate.set(Calendar.DAY_OF_MONTH, day);

                String myFormat = "dd/MM/yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                entryDateText.setText(dateFormat.format(entryDate.getTime()));
            }
        };

        textValueParsed();

        entryDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddNewEntryActivity.this, date, entryDate.get(Calendar.YEAR),entryDate.get(Calendar.MONTH),entryDate.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        // Confirm button entry
        entryButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View view) {
                if (!entryDateText.getText().toString().isEmpty()
                        || !autoCompleteTextViewPaymentType.getText().toString().isEmpty()
                        || !autoCompleteTextViewProductChoice.getText().toString().isEmpty()
                        || !entryValue.getText().toString().isEmpty() ) {

                    Toast.makeText(getApplicationContext(), "Preencha todos os campos para você continuar ", Toast.LENGTH_SHORT).show();
                } else {
                    Entries newEntry = new Entries();
                    newEntry.setPayMethod(autoCompleteTextViewPaymentType.getText().toString());
                    newEntry.setEnterDate(entryDateText.getText().toString());
                    newEntry.setEnterValue(entryValue.getText().toString());

                    entryViewModel.addNewEntry(newEntry, productId).subscribe(LongId -> {
                        if (LongId != null) {
                            Intent intent = new Intent(AddNewEntryActivity.this, ClientManagerActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Não foi possível adicionar um novo cliente", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //Back button listening
        backButtonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewEntryActivity.this, ClientManagerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void dropDownConfig() {
        // Payment type drop down config
        autoCompleteTextViewPaymentType = findViewById(R.id.payment_type_auto_complete);
        adapterPaymentType = new ArrayAdapter<String>(this, R.layout.list_item_adapter, paymentsMethods);
        autoCompleteTextViewPaymentType.setAdapter(adapterPaymentType);

//        // Product select drop down config
//        autoCompleteTextViewProductChoice = findViewById(R.id.select_product_for_entry_auto_complete);
//
//        entryViewModel.getAllProductsForEntry(this.clientId).observe(this, new Observer<List<Product>>() {
//            @Override
//            public void onChanged(List<Product> products) {
//                if (products != null) {
//                    // set adapter data config
//                    List<String> productNames = products.stream()
//                            .map(Product::getProductName)
//                            .collect(Collectors.toList());
//
//                    adapterProductSelect = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_adapter, productNames);
//                    autoCompleteTextViewProductChoice.setAdapter(adapterProductSelect);
//                }
//            }
//        });

        // Drop Down Select listener
        dropDownListener();
    }


   private void dropDownListener() {
       autoCompleteTextViewPaymentType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String item = adapterView.getItemAtPosition(i).toString();
           }
       });


//       autoCompleteTextViewProductChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//           @Override
//           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//               String item = adapterView.getItemAtPosition(i).toString();
//               Toast.makeText(getApplicationContext(), "Integer ID" + Integer.toString(i), Toast.LENGTH_SHORT).show();
//
//           }
//       });
   }

    private void textValueParsed() {
        entryValue.addTextChangedListener(new TextWatcher() {

            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // on typing value
                Toast.makeText(getApplicationContext(), "Error fetching product data: " + s.toString(), Toast.LENGTH_SHORT).show();
                Float computedValue = entryViewModel.computeDebitValue(productDebitValue, s.toString());
                if (computedValue < 0) {
                    remainingValue.setText(StringToFloat.editStringValueWithDollar(productDebitValue));
                } else {
                    remainingValue.setText(StringToFloat.editStringValueWithDollar(StringToFloat.convertFloatToString(computedValue)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    entryValue.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.\\s]", "");
                    double parsed;
                    try {
                        parsed = Double.parseDouble(cleanString);
                    } catch (NumberFormatException e) {
                        parsed = 0.00;
                    }

                    String formatted = NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format((parsed / 100));

                    current = formatted;
                    entryValue.setText(formatted);
                    entryValue.setSelection(formatted.length());

                    entryValue.addTextChangedListener(this);
                }
            }
        });
    }


   @SuppressLint("CheckResult")
   private void userInformation() {

        entryViewModel.getProductByIdSingle(clientId,productId).subscribe(product -> {
            if (product != null) {
                clientProductName.setText(product.getProductName());
                remainingValue.setText(StringToFloat.editStringValueWithDollar(product.getProductValue()));
                productDebitValue = product.getProductValue();
            }
        }, throwable -> {
            Toast.makeText(getApplicationContext(), "Error fetching product data: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
        });


       entryViewModel.getClientByIdSingle(clientId)
               .subscribe(client -> {
                   if (client != null) {
//                        clientDebitValue = client.getDebitValue();
                   }
               }, throwable -> {
                   Toast.makeText(getApplicationContext(), "Error fetching client: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
               });
   }

}
