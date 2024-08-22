package com.example.salescontroll.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.salescontroll.Repository.ProductsRepository;
import com.example.salescontroll.entitys.Product;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InsertNewProductViewModel extends AndroidViewModel {
    private ProductsRepository productsRepository;
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public InsertNewProductViewModel(@NonNull Application application) {
        super(application);
        productsRepository = new ProductsRepository(application);
    }


    public void addNewProduct(Product product, int clientId) {
        product.setClientId(clientId);
        databaseWriteExecutor.execute(() -> {
           productsRepository.addNewProduct(product);
        });
    }
}
