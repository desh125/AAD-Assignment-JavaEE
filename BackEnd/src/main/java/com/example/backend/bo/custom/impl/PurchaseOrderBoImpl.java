package com.example.backend.bo.custom.impl;

import com.example.backend.bo.custom.PurchaseOrderBO;
import com.example.backend.dao.DAOFactory;
import com.example.backend.dao.custom.OrdersDAO;
import com.example.backend.dto.CustomerDTO;
import com.example.backend.dto.ItemDTO;
import com.example.backend.dto.PurchaseOrderDTO;
import com.example.backend.entity.Customer;
import com.example.backend.entity.Item;
import com.example.backend.entity.PurchaseOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBoImpl implements PurchaseOrderBO {
    private final OrdersDAO ordersDAO;

    public PurchaseOrderBoImpl() {
        this.ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    }

/*    @Override
    public ArrayList<PurchaseOrderDTO> getAllOrderS() throws SQLException, ClassNotFoundException, SQLException {
        ArrayList<CustomEntity> allOrders = ordersDAO.getAll();
        ArrayList<PurchaseOrderDTO> PurchaseOrderDTOs = new ArrayList<>();
        for (CustomEntity order : allOrders) {
            PurchaseOrderDTO PurchaseOrderDTO = new PurchaseOrderDTO();
           // PurchaseOrderDTO.setOrderId(order.getOrderID());
            PurchaseOrderDTO.setDate(order.getDate());
          //  PurchaseOrderDTO.setCustomerId(order.getCustomerId());
           // PurchaseOrderDTO.setItemIds(order.getItemIDs());
            PurchaseOrderDTO.setDiscount(order.getDiscount());
            PurchaseOrderDTO.setTotal(order.getTotal());
            PurchaseOrderDTOs.add(PurchaseOrderDTO);
        }
        return PurchaseOrderDTOs;
    }*/

    @Override
    public boolean purchaseOrder(PurchaseOrderDTO PurchaseOrderDTO) throws SQLException, ClassNotFoundException {
        PurchaseOrder order = new PurchaseOrder();
        order.setOrderID(PurchaseOrderDTO.getOrderID());
        order.setDate(PurchaseOrderDTO.getDate());
        order.setCusTomerId(PurchaseOrderDTO.getCusTomerId());
        order.setDiscount(PurchaseOrderDTO.getDiscount());
        order.setTotal(PurchaseOrderDTO.getTotal());
        return ordersDAO.save(order);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public PurchaseOrder getOrderById(String orderId) throws SQLException, ClassNotFoundException {
        return null;

    }

    @Override
    public ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllItems() throws SQLException, ClassNotFoundException {
        return ordersDAO.getAllItemIds();
    }

    @Override
    public Item getItemById(String itemId) throws SQLException, ClassNotFoundException {
        return ordersDAO.getItemById(itemId);
    }

    @Override
    public ArrayList<String> getAllCustomers() throws SQLException, ClassNotFoundException {
      return ordersDAO.getAllCustomerIds();
    }
@Override
    public Customer getCustomerById(String customerId) throws SQLException, ClassNotFoundException {
        return ordersDAO.getCustomerById(customerId);
    }

/*    @Override
    public boolean updateOrder(PurchaseOrderDTO PurchaseOrderDTO) throws SQLException, ClassNotFoundException {
        return false; // This method is not implemented yet
    }

    @Override
    public boolean deleteOrder(String orderId) throws SQLException, ClassNotFoundException {
        return false; // This method is not implemented yet
    }*/
}
