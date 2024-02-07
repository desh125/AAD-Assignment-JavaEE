package com.example.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {
    private String orderID;
    private String date;
    private ArrayList<Cart> cartArray;
    private String item_code;
    private double total;
    private int discount;
    private String cusTomerId;
}