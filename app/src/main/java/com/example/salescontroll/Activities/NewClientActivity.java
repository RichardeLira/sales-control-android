package com.example.salescontroll.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.example.salescontroll.entitys.Client;
import com.example.salescontroll.viewModel.ClientViewModel;
import com.example.salescontroll.viewModel.NewClientViewModel;

public class NewClientActivity extends AppCompatActivity {

    private ImageView backButton;
    private ImageView addClient;
    private TextView clientName;
    private TextView clientPhone;
    private TextView clientAddress;
    private NewClientViewModel newClientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.newClient), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        screenInitializer();
        onClickEventListener();

        newClientViewModel = new ViewModelProvider(this).get(NewClientViewModel.class);
    }

    private void screenInitializer() {
        this.backButton = findViewById(R.id.backButtonOnAddClient);
        this.addClient = findViewById(R.id.userAddClientButton);
        this.clientName = findViewById(R.id.newClientName);
        this.clientPhone = findViewById(R.id.newClientPhone);
        this.clientAddress = findViewById(R.id.newClientAddress);
    }

    private void onClickEventListener() {
        backButton.setOnClickListener(view -> finish());

        addClient.setOnClickListener(view -> {
            String name = clientName.getText().toString();
            String phone = clientPhone.getText().toString();
            String address = clientAddress.getText().toString();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(NewClientActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                Client newClient = new Client();
                newClient.setAddress(address);
                newClient.setFullName(name);
                newClient.setNumberPhone(phone);

                newClientViewModel.addNewClient(newClient, new NewClientViewModel.OnClientAddedListener() {
                    @Override
                    public void onClientAdded(Client client) {
                        if (client != null) {
                            Intent intent = new Intent(NewClientActivity.this, ClientManagerActivity.class);
                            intent.putExtra("CLIENT_ID", client.getCid());
                            startActivity(intent);
                            finish();  // Close the current activity
                        }
                    }
                });
            }
        });
    }
}
