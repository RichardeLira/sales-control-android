package com.example.salescontroll.viewModel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.Repository.ClientRepository;
import com.example.salescontroll.entitys.Client;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchClientsViewModel  extends AndroidViewModel {

    private ClientRepository clientRepository;

    public SearchClientsViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);

    }
    public Flowable<List<Client>> getAllClients() {
        return clientRepository.getAllClients().
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Flowable<List<Client>> searchFilter(String searchContent) {
        return clientRepository.searchClientByName(searchContent)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public String computeClientDebits(List<Client> clients) {
        if (clients != null) {

            float amountValueResult = 0;
            for (Client client : clients) {
                amountValueResult += StringToFloat.convertStringToFloat(client.getDebitValue());
            }
            return StringToFloat.convertFloatToString(amountValueResult);
        }

        return "R$ 0";
    }

}
