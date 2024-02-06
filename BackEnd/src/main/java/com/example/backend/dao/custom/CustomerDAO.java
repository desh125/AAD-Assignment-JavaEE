package com.example.backend.dao.custom;

import com.example.backend.dao.CrudDAO;
import com.example.backend.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public ArrayList<Customer> getAllCustomersByAddress(String address)throws ClassNotFoundException, SQLException;
}
