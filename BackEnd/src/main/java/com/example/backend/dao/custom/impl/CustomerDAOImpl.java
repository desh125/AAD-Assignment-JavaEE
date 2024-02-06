package com.example.backend.dao.custom.impl;

import com.example.backend.dao.custom.CustomerDAO;
import com.example.backend.entity.Customer;
import com.example.backend.listner.MyListener;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("select * from customers");
            ResultSet rst = pstm.executeQuery();

            ArrayList<Customer> customers = new ArrayList<>();
            while (rst.next()) {
                String id = rst.getString(1);
                String name = rst.getString(2);
                String tp = rst.getString(3);
                int age = rst.getInt(4);
                double salary = rst.getDouble(5);

                Customer customer = new Customer(id, name, tp,age,salary);
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return null;
    }

    @Override
    public boolean save(Customer dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("insert into customers values(?,?,?,?,?)");
            pstm.setObject(1, dto.getId());
            pstm.setObject(2, dto.getName());
            pstm.setObject(3, dto.getTp());
            pstm.setObject(4, dto.getAge());
            pstm.setObject(5, dto.getSalary());


            if (pstm.executeUpdate() > 0) {
                System.out.println("Saved");
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
    public boolean update(Customer dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("update customers set name=?,tp=?, age=?, salary=? where customerId=?");

            pstm.setObject(1, dto.getName());
            pstm.setObject(2, dto.getTp());
            pstm.setObject(3, dto.getAge());
            pstm.setObject(4, dto.getSalary());
            pstm.setObject(5, dto.getId()); // Corrected index

            if (pstm.executeUpdate() > 0) {
                System.out.println("Updated");
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
    public boolean update2(Customer dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String cusID) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("delete from customers where customerId=?");
            pstm.setObject(1, cusID);

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
    public Customer search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Customer> getAllCustomersByAddress(String address) throws ClassNotFoundException, SQLException {
        return null;
    }
}
