package com.example.backend.bo.custom;



import com.example.backend.bo.SuperBO;
import com.example.backend.dto.CustomerDTO;
import com.example.backend.dto.ItemDTO;
import com.example.backend.dto.PurchaseOrderDTO;
import com.example.backend.entity.Customer;
import com.example.backend.entity.Item;
import com.example.backend.entity.PurchaseOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    ArrayList<PurchaseOrderDTO> getAllOrderS() throws SQLException, ClassNotFoundException, SQLException;

    boolean purchaseOrder(PurchaseOrderDTO dto) throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;

    boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException;

    boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException;

    String generateNewOrderID() throws SQLException, ClassNotFoundException;

    Item getItemById(String itemId) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllCustomers() throws SQLException, ClassNotFoundException;

    PurchaseOrder getOrderById(String orderId) throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException;

    ArrayList<String> getAllItems() throws SQLException, ClassNotFoundException;

    Customer getCustomerById(String customerId) throws SQLException, ClassNotFoundException;
}