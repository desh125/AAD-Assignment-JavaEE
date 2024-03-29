package com.example.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderDTO {
    private String orderID;
    private String date;
    private ArrayList<CartDTO> cartArray;
    private double total;
    private int discount;
    private String cusTomerId;
}