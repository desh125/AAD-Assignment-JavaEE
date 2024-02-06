package com.example.backend.bo;

import com.example.backend.bo.custom.impl.CustomerBoImpl;
import com.example.backend.bo.custom.impl.ItemBoImpl;
import com.example.backend.bo.custom.impl.OrdersBoImpl;
import com.example.backend.bo.custom.impl.PurchaseOrderBoImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBoImpl(); // SuperBO bo =new CustomerBOImpl();
            case ITEM:
                return new ItemBoImpl(); // SuperBO bo = new ItemBOImpl();
            case ORDERS:
              //  return new OrdersBoImpl(); // SuperBO bo = new OrdersBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBoImpl(); //SuperBO bo = new PurchaseOrderBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, ORDERS, PURCHASE_ORDER
    }
}
