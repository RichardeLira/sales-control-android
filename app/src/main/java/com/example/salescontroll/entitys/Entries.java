package com.example.salescontroll.entitys;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Product.class,
                parentColumns = "pId",
                childColumns = "productId",
                onDelete = ForeignKey.CASCADE))
public class Entries {

    @PrimaryKey(autoGenerate = true)
    public int eId;

    @ColumnInfo(name = "enter_date")
    public String enterDate;

    @ColumnInfo(name = "pay_method")
    public String payMethod;

    @ColumnInfo(name = "enter_value")
    public String enterValue;

    @ColumnInfo(name = "productId")
    public int productId;

    @ColumnInfo(name = "product_name")
    public String productName;

    // Set
    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public void setEnterValue(String enterValue) {
        this.enterValue = enterValue;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    //Gets
    public String getEnterDate() {
        return enterDate;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public String getEnterValue() {
        return enterValue;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

}
