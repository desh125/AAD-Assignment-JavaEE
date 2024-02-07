package com.example.backend.dao.custom;

import com.example.backend.dao.CrudDAO;
import com.example.backend.dto.OrdersDTO;
import com.example.backend.entity.Orders;
import com.example.backend.entity.PurchaseOrder;

public interface OrderDetailsDAO extends CrudDAO<Orders, String> {
    boolean save(OrdersDTO dto);
}
