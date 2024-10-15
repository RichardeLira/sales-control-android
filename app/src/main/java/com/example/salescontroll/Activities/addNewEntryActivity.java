package com.example.salescontroll.Activities;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;

import com.example.salescontroll.R;
import com.example.salescontroll.Repository.EntryRepository;
import com.example.salescontroll.Repository.ProductsRepository;
import com.example.salescontroll.entitys.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class addNewEntryActivity extends AppCompatActivity {

    // paymentsMethods const
    private final String paymentsMethods[] = {"pix", "crédito", "dinheiro", "outro"};
    private AutoCompleteTextView autoCompleteTextViewPaymentType;
    private AutoCompleteTextView autoCompleteTextViewProductChoice;
    private EditText valueEntry;
    private Date dateEntry;
    private ArrayAdapter<String> adapterPaymentType;
    private ArrayAdapter<Product> adapterProductSelect;
    // Data Repository
    private EntryRepository entryRepository;
    private ProductsRepository productsRepository;
    // Control ID
    private int clientId = -1;
    // Screen fields
    private EditText entryValue;
    private TextView entryDateText;
    private TextView userName;
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

        this.clientId = getIntent().getIntExtra("CLIENT_ID", -1);

        dropDownConfig();
    }

    private void UiInitializer() {
        // Text and input elements initializer
        entryValue = findViewById(R.id.insert_entry_value);
        entryDateText = findViewById(R.id.insert_entry_date);
        userName = findViewById(R.id.client_name_on_entry_screen);

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

    }

    private void dropDownConfig() {
        // Payment type drop down config
        autoCompleteTextViewPaymentType = findViewById(R.id.select_product_for_entry_auto_complete);
        adapterPaymentType = new ArrayAdapter<String>(this, R.layout.list_item_adapter, paymentsMethods);
        autoCompleteTextViewPaymentType.setAdapter(adapterPaymentType);

        // Product select drop down config
        autoCompleteTextViewProductChoice = findViewById(R.id.select_product_for_entry_auto_complete);
        productsRepository.getProductsForClient(this.clientId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (products != null) {
                    // set adapter data config
                    autoCompleteTextViewProductChoice.setAdapter(adapterProductSelect);
                    adapterProductSelect = new ArrayAdapter<Product>(getApplicationContext(), R.layout.list_item_adapter, products);
                }
            }
        });

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
   }



   private void UserInformation() { }





}
