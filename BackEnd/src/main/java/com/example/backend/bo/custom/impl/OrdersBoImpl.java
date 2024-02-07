package com.example.backend.bo.custom.impl;

import com.example.backend.bo.custom.OrdersBO;
import com.example.backend.dao.DAOFactory;
import com.example.backend.dao.custom.OrderDetailsDAO;
import com.example.backend.dto.OrdersDTO;
import com.example.backend.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersBoImpl implements OrdersBO {
    private final OrderDetailsDAO orderDetailsDAO;

    public OrdersBoImpl() {
        this.orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    }
    @Override
    public ArrayList<OrdersDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> allItems = orderDetailsDAO.getAll();
        ArrayList<OrdersDTO> orderDTOs = new ArrayList<>();
        for (Orders item : allItems) {
            OrdersDTO itemDTO = new OrdersDTO();
            itemDTO.setItem_code(item.getItem_code());
            itemDTO.setOrderId(item.getOrderId());
            itemDTO.setCustomerId(item.getCustomerId());
            orderDTOs.add(itemDTO);
        }
        return orderDTOs;
    }
}
