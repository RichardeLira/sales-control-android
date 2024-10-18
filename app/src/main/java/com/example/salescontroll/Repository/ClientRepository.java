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

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.Completable;

public class ClientRepository {

    private ClientDao clientDao;

    public ClientRepository(Application application) {
        AppDataBase db = AppDataBase.getDataBase(application);
        clientDao = db.clientDao();

    }

    public Flowable<List<Client>> getAllClients() {
        return clientDao.getAll();
    }

    public Single<Long> addNewClient(final Client client) {
        return clientDao.insert(client);
    }


    // Live data use (deprecated) Use Single with room db
    public LiveData<Client> getClientById(int clientId) {
        return clientDao.loadClientById(clientId);
    }

    // Use this one
    public Single<Client> getClientByIdRoom(int clientId) {
        return clientDao.loadClientByIdT(clientId);
    }

    public Flowable<List<Client>> searchClientByName(String clientName) {
        return clientDao.findByName(clientName);
    }


    public Completable UpdateClient(Client client) {
        return clientDao.updateClient(client);
    }



}
