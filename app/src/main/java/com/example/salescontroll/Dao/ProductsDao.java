package com.example.salescontroll.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.salescontroll.entitys.Product;


import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ProductsDao {

    // Get All products of client
    @Transaction
    @Query("SELECT * FROM Product WHERE clientId = :clientId")
    LiveData<List<Product>> getProductsForClient(int clientId);

    // One product by one client
    @Transaction
    @Query("SELECT * FROM Client, product WHERE cid = :clientId AND pId = :productId")
    Single<Product> getOneProductOfClient(int clientId, int productId);

    // Insert new product on client
    @Insert
    void insertProducts(Product product);


}
