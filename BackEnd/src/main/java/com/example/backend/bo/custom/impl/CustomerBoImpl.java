package com.example.backend.bo.custom.impl;

import com.example.backend.bo.custom.CustomerBO;
import com.example.backend.dao.DAOFactory;
import com.example.backend.dao.custom.CustomerDAO;
import com.example.backend.dto.CustomerDTO;
import com.example.backend.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBoImpl implements CustomerBO {

    private final CustomerDAO customerDAO;

    public CustomerBoImpl() {
        this.customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : allCustomers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setTp(customer.getTp());
            customerDTO.setAge(customer.getAge());
            customerDTO.setSalary(customer.getSalary());
            customerDTOs.add(customerDTO);
        }
        return customerDTOs;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setTp(dto.getTp());
        customer.setAge(dto.getAge());
        customer.setSalary(dto.getSalary());
        return customerDAO.save(customer);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setTp(dto.getTp());
        customer.setAge(dto.getAge());
        customer.setSalary(dto.getSalary());
        return customerDAO.update(customer);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }


    @Override
    public boolean customerExist(String id) throws SQLException, ClassNotFoundException {
        // Implement logic to check if customer exists
        return false;
    }


    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        // Implement logic to generate new customer ID
        return null;
    }
}
