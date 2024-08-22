package com.example.salescontroll.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.salescontroll.Repository.ClientRepository;
import com.example.salescontroll.entitys.Client;

import java.util.List;

public class SearchClientsViewModel  extends AndroidViewModel {

    private ClientRepository clientRepository;
    private LiveData<List<Client>> clientLiveData;

    public SearchClientsViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
        clientLiveData = clientRepository.getAllClients();
    }


    public LiveData<List<Client>> getAllClients() {
        return clientLiveData;
    }
}
