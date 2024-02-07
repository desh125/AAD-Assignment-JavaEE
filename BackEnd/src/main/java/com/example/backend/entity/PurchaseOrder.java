package com.example.backend.entity;

import com.example.backend.dto.CartDTO;
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
    private ArrayList<CartDTO> cartArray;
    private double total;
    private int discount;
    private String cusTomerId;

    public PurchaseOrder(String orderID, String date, String cusTomerId, int discount, double total) {
        this.orderID = orderID;
        this.date = date;
        this.cusTomerId = cusTomerId;
        this.discount = discount;
        this.total=total;
    }
}