package com.example.salescontroll.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.salescontroll.Repository.ClientRepository;
import com.example.salescontroll.entitys.Client;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.core.Single;

public class NewClientViewModel extends AndroidViewModel {
    private ClientRepository clientRepository;

    public NewClientViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
    }

    public Single<Long> addNewClient(final Client client) {
        return clientRepository.addNewClient(client).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }

}
