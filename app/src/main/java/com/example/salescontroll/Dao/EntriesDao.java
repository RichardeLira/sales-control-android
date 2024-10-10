package com.example.salescontroll.Dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


import io.reactivex.Flowable;
import java.util.List;


import com.example.salescontroll.entitys.Entries;


@Dao
public interface EntriesDao {

    // Get all entries from one product
    @Transaction
    @Query("SELECT * FROM Entries WHERE productId = :productId")
    Flowable<List<Entries>> getAllEntries(int productId);

    // Get one entry of a product by IDs
    @Transaction
    @Query("SELECT * FROM Entries WHERE productId = :productId AND eId = :enterId")
    Entries getOneEnterOfProduct(int productId, int enterId);

}
