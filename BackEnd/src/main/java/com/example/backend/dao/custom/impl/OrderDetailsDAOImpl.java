package com.example.backend.dao.custom.impl;

import com.example.backend.dao.custom.OrderDetailsDAO;
import com.example.backend.dto.OrdersDTO;
import com.example.backend.entity.Orders;
import com.example.backend.listner.MyListener;

import java.sql.*;
import java.util.ArrayList;

public class OrderDetailsDAOImpl implements OrderDetailsDAO {
    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("select * from order_details");
            ResultSet rst = pstm.executeQuery();

            ArrayList<Orders> itemArrayList = new ArrayList<>();
            while (rst.next()) {
                String orderId = rst.getString(1);
                String customerId = rst.getString(2);
                String item_code = rst.getString(3);
               String address = "galle";

                Orders item = new Orders(orderId,customerId,item_code);
                itemArrayList.add(item);
            }

            return itemArrayList;

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return null;
    }

    @Override
    public boolean save(Orders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(OrdersDTO dto) {
        System.out.println(dto);

        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO order_details (orderID, customerId, item_code) VALUES (?, ?, ?)");
            pstm.setString(1, dto.getOrderId());
            pstm.setString(2, dto.getCustomerId());
            pstm.setString(3, dto.getItem_code());

            if (pstm.executeUpdate() <= 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Connection failed"+e);
            return false;
        }
    }

    @Override
    public boolean update(Orders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update2(Orders dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Orders search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
