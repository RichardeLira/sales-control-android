package com.example.salescontroll.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.salescontroll.Dao.ClientDao;
import com.example.salescontroll.Dao.ProductsDao;
import com.example.salescontroll.entitys.Client;
import com.example.salescontroll.entitys.Product;

@Database(entities = {Client.class, Product.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ClientDao clientDao();
    public abstract ProductsDao productsDao();

    private static volatile AppDataBase INSTANCE;

    public static AppDataBase getDataBase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDataBase.class,
                                    "client-database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
