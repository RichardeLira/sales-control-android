package com.example.salescontroll.entitys;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Client {

    @PrimaryKey(autoGenerate = true)
    public int cid;

    @ColumnInfo(name = "full_name")
    public String fullName;

    @ColumnInfo(name = "number_phone")
    public String numberPhone;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "debit")
    public String debitValue;

    public void setDebitValue(String debitValue) {
        this.debitValue = debitValue;
    }

    public String getDebitValue() {
        return debitValue;
    }

    public int getCid() {
        return cid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getAddress() {
        return address;
    }
}
