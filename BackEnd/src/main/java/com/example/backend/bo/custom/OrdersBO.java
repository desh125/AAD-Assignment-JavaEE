package com.example.backend.bo.custom;


import com.example.backend.bo.SuperBO;
import com.example.backend.dto.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBO extends SuperBO {
    ArrayList<OrdersDTO> getAllItems() throws SQLException, ClassNotFoundException;

}
