package com.example.salescontroll.entitys.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.salescontroll.entitys.Client;
import com.example.salescontroll.entitys.Product;

import java.util.List;

public class ClientWithProducts {

    @Embedded
    public Client client;

    @Relation(
            parentColumn = "cid",
            entityColumn = "client_id"
    )
    public List<Product> product;

}
