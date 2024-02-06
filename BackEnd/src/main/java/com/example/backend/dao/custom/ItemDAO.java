package com.example.backend.dao.custom;

import com.example.backend.dao.CrudDAO;
import com.example.backend.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,String> {
    public ArrayList<Item> getItemFromPrice(double price)throws ClassNotFoundException, SQLException;
}
