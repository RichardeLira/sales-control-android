package com.example.salescontroll.Repository;

import android.app.Application;

import com.example.salescontroll.Dao.EntriesDao;
import com.example.salescontroll.DataBase.AppDataBase;
import com.example.salescontroll.entitys.Entries;

public class EntryRepository {

    private EntriesDao entriesDao;

    public EntryRepository(Application application) {
        AppDataBase db = AppDataBase.getDataBase(application);
        entriesDao = db.entriesDAO();
    }

    public void addNewEntry(final Entries entries) {
        entriesDao.insertEntries(entries);
    }

}
