package com.example.salescontroll.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.salescontroll.Helpers.StringToFloat;
import com.example.salescontroll.Repository.ClientRepository;
import com.example.salescontroll.Repository.EntryRepository;
import com.example.salescontroll.Repository.ProductsRepository;
import com.example.salescontroll.entitys.Client;
import com.example.salescontroll.entitys.Entries;
import com.example.salescontroll.entitys.Product;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ClientManagerViewModel extends AndroidViewModel {

    private ClientRepository clientRepository;
    private ProductsRepository productsRepository;
    private EntryRepository entryRepository;
    private static int lastUserIdAdded = -1;

    public ClientManagerViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
        productsRepository = new ProductsRepository(application);
        entryRepository = new EntryRepository(application);
    }

    public void setLastUserIdAdded(int lastUserIdAdded) {
        ClientManagerViewModel.lastUserIdAdded = lastUserIdAdded;
    }

    public int getLastUserIdAdded() {
        return lastUserIdAdded;
    }

    public LiveData<List<Product>> getAllProductsByUser(int clientId) {
        return productsRepository.getProductsForClient(clientId);
    }

    public Flowable<List<Entries>> getAllEntriesByUser(int clientId) {
        return entryRepository.getAlEntriesForClient(clientId).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }

    public String getAmountProductValue(List<Product> products) {
        // Computing value of all products
        if (products != null) {
            float amountValueResult = 0;
            for (Product product : products) {
                amountValueResult += StringToFloat.convertStringToFloat(product.getProductValue());
            }
            return StringToFloat.convertFloatToString(amountValueResult);

        }

        return "0";

    }

    public void updateClientValueDebit(Client client) {
        clientRepository.UpdateClient(client);
    }

    public LiveData<Client> getClientById(int clientId) {
        return clientRepository.getClientById(clientId);
    }

}
