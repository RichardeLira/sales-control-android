package com.example.salescontroll.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.salescontroll.entitys.Client;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ClientDao {

    @Query("SELECT * FROM Client")
    Flowable<List<Client>> getAll();

    @Query("SELECT * FROM Client WHERE cid IN (:userIds)")
    LiveData<List<Client>> loadAllClientsByIds(int[] userIds);

    @Query("SELECT * FROM Client WHERE cid = :userId")
    LiveData<Client> loadClientById(int userId);

    @Query("SELECT * FROM Client WHERE cid = :userId")
    Single<Client> loadClientByIdT(int userId);

    @Query("SELECT * FROM Client WHERE LOWER(full_name) LIKE LOWER(:name || '%')")
    Flowable<List<Client>> findByName(String name);

    @Insert
    Single<Long> insert(Client client);

    @Delete
    void delete(Client client);

    @Update
    Completable updateClient(Client client);

}
