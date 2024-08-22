package com.example.salescontroll.entitys;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Client.class,
        parentColumns = "cid",
        childColumns = "clientId",
        onDelete = ForeignKey.CASCADE))
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int pId;

    @ColumnInfo(name = "product_name")
    public String productName;

    @ColumnInfo(name = "product_quantity")
    public String productQuantity;

    @ColumnInfo(name = "product_value")
    public String productValue;

    @ColumnInfo(name = "product_buy_date")
    public String productBuyDate;

    @ColumnInfo(name = "clientId")
    public int clientId; // Foreign key to Client

    // Getters and setters

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductValue() {
        return productValue;
    }

    public void setProductValue(String productValue) {
        this.productValue = productValue;
    }

    public String getProductBuyDate() {
        return productBuyDate;
    }

    public void setProductBuyDate(String productBuyDate) {
        this.productBuyDate = productBuyDate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
