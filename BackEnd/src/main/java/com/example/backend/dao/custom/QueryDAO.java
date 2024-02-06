package com.example.backend.dao.custom;

import com.example.backend.dao.SuperDAO;
import com.example.backend.entity.CustomEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> searchOrderByOrderID(String id)throws SQLException,ClassNotFoundException;
}
