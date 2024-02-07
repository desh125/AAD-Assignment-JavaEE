package com.example.backend.bo.custom.impl;

import com.example.backend.bo.custom.PurchaseOrderBO;
import com.example.backend.dao.DAOFactory;
import com.example.backend.dao.custom.OrderDetailsDAO;
import com.example.backend.dao.custom.OrdersDAO;
import com.example.backend.dto.*;
import com.example.backend.entity.Customer;
import com.example.backend.entity.Item;
import com.example.backend.entity.PurchaseOrder;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBoImpl implements PurchaseOrderBO {
    private final OrdersDAO ordersDAO;
    private final OrderDetailsDAO orderDetailsDAO;

    public PurchaseOrderBoImpl() {
        this.ordersDAO = (OrdersDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
        this.orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
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
    public boolean purchaseOrder(PurchaseOrderDTO purchaseOrderDTO) throws SQLException, ClassNotFoundException {
        // Insert into orders table
        PurchaseOrder order = new PurchaseOrder();
        order.setOrderID(purchaseOrderDTO.getOrderID());
        order.setDate(purchaseOrderDTO.getDate());
        order.setCusTomerId(purchaseOrderDTO.getCusTomerId());
        order.setDiscount(purchaseOrderDTO.getDiscount());
        order.setTotal(purchaseOrderDTO.getTotal());
        boolean orderSaved = ordersDAO.save(order);

        if (!orderSaved) {
            return false; // Return false if order insertion fails
        }
        if (!orderSaved) {
            ArrayList<CartDTO> cartArray = purchaseOrderDTO.getCartArray();
            for (CartDTO c : cartArray) {
                ItemDTO itemDTO = c.getItem();
                String cartItemCode = itemDTO.getCode();
                int cartQty = c.getQty();

                // save order details
                OrdersDTO orderDetails = new OrdersDTO(purchaseOrderDTO.getOrderID(), purchaseOrderDTO.getCusTomerId(), cartItemCode);
                boolean isOrderDetailsSaved = orderDetailsDAO.save(orderDetails);
                System.out.println(isOrderDetailsSaved);

            }
        }

        return true; // Return true if all operations are successful
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
