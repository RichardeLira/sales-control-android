package com.example.salescontroll.viewModel;

import android.app.Application;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.salescontroll.Helpers.SingleLiveEvent;
import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.Repository.ClientRepository;
import com.example.salescontroll.entitys.Client;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.core.Completable;


public class MainViewModel extends AndroidViewModel {

    private ClientRepository clientRepository;
    private LiveData<List<Client>> clientLiveData;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public MainViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
        clientLiveData = clientRepository.getAllClients();
    }

    public LiveData<List<Client>> getAllClients() {
        return clientLiveData;
    }

    public String getValueForAllClients(List<Client> clients, Context context) {
        if (clients != null) {

            float amountValueResult = 0;
            for (Client client : clients) {
                amountValueResult += StringToFloat.convertStringToFloat(client.getDebitValue());
            }
            return StringToFloat.convertFloatToString(amountValueResult);
        }

        return "R$ 0";
    }

    public Completable updateClientValueDebit(Client client) {
        return clientRepository.UpdateClient(client)
                .subscribeOn(Schedulers.io())  // Operação em I/O thread
                .observeOn(AndroidSchedulers.mainThread());  // Observa na thread principal
    }

    public LiveData<Client> getClientById(int clientId) {
        return clientRepository.getClientById(clientId);
    }


    public Flowable<Client> getClientByIdTest(int clientId) {
        return clientRepository.getClientByITest(clientId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}












