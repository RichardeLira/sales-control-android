package com.example.salescontroll.Repository;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.salescontroll.Dao.ClientDao;
import com.example.salescontroll.DataBase.AppDataBase;
import com.example.salescontroll.entitys.Client;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


public class ClientRepository {

    private ClientDao clientDao;
    private LiveData<List<Client>> allClients;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public ClientRepository(Application application) {
        AppDataBase db = AppDataBase.getDataBase(application);
        clientDao = db.clientDao();
        allClients = clientDao.getAll();
    }

    public LiveData<List<Client>> getAllClients() {
        return allClients;
    }

    public long addNewClient(final Client client) {
        return clientDao.insert(client);
    }

    public LiveData<Client> getClientById(int clientId) {
        return clientDao.loadClientById(clientId);
    }



    public Flowable<Client> getClientByITest(int clientId) {
        return clientDao.loadClientByIdT(clientId);
    }


    public Completable UpdateClient(Client client) {
        return clientDao.updateClient(client);
    }



}
