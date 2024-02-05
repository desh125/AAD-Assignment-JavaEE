package com.example.backend.bo;

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
                return new CustomerBOImpl(); // SuperBO bo =new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl(); // SuperBO bo = new ItemBOImpl();
            case ORDERS:
                return new OrdersBOImpl(); // SuperBO bo = new OrdersBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl(); //SuperBO bo = new PurchaseOrderBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER, ITEM, ORDERS, PURCHASE_ORDER
    }
}
