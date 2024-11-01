package com.example.salescontroll.viewModel;

import android.annotation.SuppressLint;
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
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class EntryViewModel extends AndroidViewModel {

    private EntryRepository entryRepository;
    private ProductsRepository productsRepository;
    private ClientRepository clientRepository;


    public EntryViewModel(@NonNull Application application) {
        super(application);
        entryRepository = new EntryRepository(application);
        productsRepository = new ProductsRepository(application);
        clientRepository = new ClientRepository(application);

    }

    public @io.reactivex.rxjava3.annotations.NonNull Single<Long> addNewEntry(Entries entry, int productId) {
        entry.setProductId(productId);
        return entryRepository.addNewEntry(entry).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());

    }

    public LiveData<List<Product>> getAllProductsForEntry(int clientId) {
        return productsRepository.getProductsForClient(clientId);
    }

    public Single<Client> getClientByIdSingle(int clientId) {
        return clientRepository.getClientByIdRoom(clientId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Product> getProductByIdSingle(int clientId, int productId) {
        return productsRepository.getOneProduct(clientId, productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    @SuppressLint("CheckResult")
    public double computeAllEntriesForOneProduct(int productId) {
        Flowable<List<Entries>> entries = entryRepository.getAlEntriesForClient(productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


        final double[] amountEntriesValue = {0.00};

        entries.subscribe(entriesData  -> {
            if (entriesData != null) {
                for (Entries actualEntry : entriesData ) {
                    amountEntriesValue[0] += Double.parseDouble(actualEntry.getEnterValue());
                }

            }

        }, throwable -> {

        });

        return amountEntriesValue[0];

    }


}
