package com.example.salescontroll.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.salescontroll.Repository.ClientRepository;
import com.example.salescontroll.entitys.Client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.core.Single;

public class NewClientViewModel extends AndroidViewModel {
    private ClientRepository clientRepository;

    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public NewClientViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
    }

    public Single<Long> addNewClient(final Client client) {
        return clientRepository.addNewClient(client).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }



    public interface OnClientAddedListener {
        void onClientAdded(Client client);
    }
}
