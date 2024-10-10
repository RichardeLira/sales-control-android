package com.example.salescontroll.entitys.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.example.salescontroll.entitys.Entries;
import com.example.salescontroll.entitys.Product;
import java.util.List;

public class ProductsWithEntries {

    @Embedded
    public Product product;

    @Relation(
            parentColumn = "pId",
            entityColumn = "productId"
    )
    public List<Entries> entries;

}
