package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomEntity {
    private String id;
    private String name;
    private String tp;
    private int age;
    private double salary;


    private String code;
    private String itemName;
    private int qtyOnHand;
    private double price;

    private String orderId;
    private String date;
    private String customerId;
    private String address;

    private String orderID;
    private String orderDate;
    private ArrayList<Cart> cartArray;
    private double total;
    private int discount;
    private String cusTomerId;

}
