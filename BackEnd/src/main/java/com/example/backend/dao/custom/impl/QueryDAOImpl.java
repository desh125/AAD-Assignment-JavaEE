package com.example.backend.dao.custom.impl;

import com.example.backend.dao.custom.QueryDAO;
import com.example.backend.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> searchOrderByOrderID(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
