package com.example.backend.dao.custom.impl;

import com.example.backend.dao.custom.OrdersDAO;
import com.example.backend.dto.CartDTO;
import com.example.backend.entity.*;
import com.example.backend.listner.MyListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersDAOImpl implements OrdersDAO {

    @Override
    public ArrayList<PurchaseOrder> getAlls() {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("select * from orders");
            ResultSet rst = pstm.executeQuery();

            ArrayList<PurchaseOrder> itemArrayList = new ArrayList<>();
            while (rst.next()) {
                String orderId = rst.getString(1);
                String date = rst.getString(2);
                String customerId = rst.getString(3);
                int discount = rst.getInt(4);
                double total = rst.getDouble(5);
                System.out.println("this is OrderId"+rst.getString(1));
                PurchaseOrder item = new PurchaseOrder(orderId,date,customerId,discount,total);
                itemArrayList.add(item);
            }
            System.out.println("this is dao"+itemArrayList);

            return itemArrayList;

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return null;
    }

    @Override
    public ArrayList<CustomEntity> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(CustomEntity dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean save(PurchaseOrder order) {
        try (Connection connection = MyListener.pool.getConnection()) {
            // Insert the order into the orders table
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO orders (orderId, date, customerId, discount, total) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, order.getOrderID());
            statement.setString(2, order.getDate());
            statement.setString(3, order.getCusTomerId());
            statement.setInt(4, order.getDiscount());
            statement.setDouble(5, order.getTotal());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public Item getItemById(String itemId) {
        try (Connection connection = MyListener.pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM item WHERE item_code = ?");
            statement.setString(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Item item = new Item();
                item.setCode(resultSet.getString("item_code"));
                item.setName(resultSet.getString("item_name"));
                item.setPrice(resultSet.getDouble("price"));
                item.setQtyOnHand(resultSet.getInt("quantity"));
                // Set s item details as needed

                return item;
            } else {
                return null; // No item found with the given ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<String> getAllItemIds() {
        try (Connection connection = MyListener.pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT item_code FROM item");
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> itemIds = new ArrayList<>();
            while (resultSet.next()) {
                itemIds.add(resultSet.getString("item_code"));
            }

            return itemIds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ArrayList<String> getAllCustomerIds() {
        try (Connection connection = MyListener.pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT customerId FROM customers");
            ResultSet resultSet = statement.executeQuery();

            ArrayList<String> customerIds = new ArrayList<>();
            while (resultSet.next()) {
                String customerId = resultSet.getString("customerId");
                customerIds.add(customerId);
            }

            return customerIds;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
}


    @Override
    public Customer getCustomerById(String customerId) {
        try (Connection connection = MyListener.pool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM customers WHERE customerId = ?");
            statement.setString(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getString("customerId"));
                customer.setName(resultSet.getString("name"));
                customer.setSalary(resultSet.getDouble("salary"));
                customer.setTp(resultSet.getString("tp"));
                // Set other customer details as needed

                return customer;
            } else {
                return null; // No customer found with the given ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(CustomEntity dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update2(CustomEntity dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public CustomEntity search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("delete from orders where orderId=?");
            pstm.setObject(1, s);

            if (pstm.executeUpdate() > 0) {
                System.out.println("Deleted");
                return true;
            } else {
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }
}
