package com.example.salescontroll.Repository;

import android.app.Application;

import com.example.salescontroll.Dao.EntriesDao;
import com.example.salescontroll.DataBase.AppDataBase;
import com.example.salescontroll.entitys.Entries;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class EntryRepository {

    private EntriesDao entriesDao;

    public EntryRepository(Application application) {
        AppDataBase db = AppDataBase.getDataBase(application);
        entriesDao = db.entriesDAO();
    }

    public Single<Long> addNewEntry(final Entries entries) {
       return entriesDao.insertEntries(entries);
    }

    public Flowable<List<Entries>> getAlEntriesForClient(int clientId) {
        return entriesDao.getAllEntries(clientId);
    }

}
