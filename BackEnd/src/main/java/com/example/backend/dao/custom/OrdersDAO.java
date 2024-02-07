package com.example.backend.dao.custom;

import com.example.backend.dao.CrudDAO;
import com.example.backend.entity.CustomEntity;
import com.example.backend.entity.Customer;
import com.example.backend.entity.Item;
import com.example.backend.entity.PurchaseOrder;

import java.util.ArrayList;

public interface OrdersDAO extends CrudDAO<CustomEntity,String> {
    boolean save(PurchaseOrder order);

    Item getItemById(String itemId);

    ArrayList<String> getAllItemIds();

    ArrayList<String> getAllCustomerIds();

    Customer getCustomerById(String customerId);
    ArrayList<PurchaseOrder> getAlls();
}
