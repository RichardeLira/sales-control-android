package com.example.salescontroll.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.salescontroll.Repository.ClientRepository;
import com.example.salescontroll.entitys.Client;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientViewModel extends AndroidViewModel {

    private ClientRepository clientRepository;
    private LiveData<List<Client>> clientLiveData;
    private MutableLiveData<Client> lastUserAddedLiveData = new MutableLiveData<>();
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    static private int clientId;


    public ClientViewModel(Application application) {
        super(application);
        clientRepository = new ClientRepository(application);
//        clientLiveData = clientRepository.getAllClients();
    }

//    public void addNewClient(final Client client, OnClientAddedListener listener) {
//        databaseWriteExecutor.execute(() -> {
//            long clientId = clientRepository.addNewClient(client);
//            mainThreadHandler.post(() -> {
//                clientRepository.getClientById((int) clientId).observeForever(newClient -> {
//                    lastUserAddedLiveData.setValue(newClient);
//                    listener.onClientAdded(newClient);
//                });
//            });
//        });
//    }

    public LiveData<Client> getLastUserAdded() {
        return lastUserAddedLiveData;
    }

    public LiveData<List<Client>> getAllClients() {
        return clientLiveData;
    }

    public void saveLastClientIdAdded(int clientId) {
        ClientViewModel.clientId = clientId;
    }


    public int getLastClientIdAdded() {
        return ClientViewModel.clientId;
    }

    public LiveData<Client> getClientById(int clientId) {
        return clientRepository.getClientById(clientId);
    }

    public interface OnClientAddedListener {
        void onClientAdded(Client client);
    }
}
