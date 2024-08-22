package com.example.salescontroll.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.salescontroll.Dao.ProductsDao;
import com.example.salescontroll.DataBase.AppDataBase;
import com.example.salescontroll.entitys.Product;
import java.util.List;

public class ProductsRepository {

    private ProductsDao productsDao;

    public ProductsRepository(Application application) {
        AppDataBase db = AppDataBase.getDataBase(application);
        productsDao = db.productsDao();
    }

    public LiveData<List<Product>> getProductsForClient(int clientId) {
        return productsDao.getProductsForClient(clientId);
    }

    public void addNewProduct(final Product product) {
         productsDao.insertProducts(product);
    }

    public Product getOneProduct(int clientId, int productId) {
        return productsDao.getOneProductOfClient(clientId, productId);
    }

}
