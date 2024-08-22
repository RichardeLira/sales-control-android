package com.example.salescontroll.viewModel;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.salescontroll.Repository.ProductsRepository;
import com.example.salescontroll.entitys.Product;
import com.example.salescontroll.entitys.Relations.ClientWithProducts;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductsViewModel extends AndroidViewModel {

    private LiveData<List<ClientWithProducts>> productsLiveData;
    private  ProductsRepository productsRepository;

    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    public ProductsViewModel(@NonNull Application application) {
        super(application);

        productsRepository = new ProductsRepository(application);
    }

    public LiveData<List<Product>> getAllProducts(int clientId) {
        return productsRepository.getProductsForClient(clientId);
    }

    public void insertNewProduct(Product product) {
        databaseWriteExecutor.execute(() -> {
            productsRepository.addNewProduct(product);
        });
    }





}
