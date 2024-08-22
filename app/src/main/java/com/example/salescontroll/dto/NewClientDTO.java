package com.example.salescontroll.dto;

public class NewClientDTO {

    private String name;
    private String phone;
    private String address;


    public NewClientDTO(String name, String phone, String address) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getAddress() {
        return this.address;
    }
}


