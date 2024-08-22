package com.example.salescontroll.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.salescontroll.Repository.ClientRepository;
import com.example.salescontroll.entitys.Client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlinx.coroutines.android.AndroidDispatcherFactory;
import kotlinx.coroutines.internal.MainDispatcherFactory;

public class NewClientViewModel extends AndroidViewModel {
    private ClientRepository clientRepository;

    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public NewClientViewModel(@NonNull Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
    }

    public void addNewClient(final Client client, OnClientAddedListener listener) {

        databaseWriteExecutor.execute(() -> {
            long clientId = clientRepository.addNewClient(client);
            if (clientId != -1) {
                mainThreadHandler.post(() -> {
                    clientRepository.getClientById((int) clientId).observeForever(listener::onClientAdded);
                });
            }
        });
    }


    public interface OnClientAddedListener {
        void onClientAdded(Client client);
    }
}
