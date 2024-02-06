package com.example.backend.dao.custom.impl;

import com.example.backend.dao.custom.ItemDAO;
import com.example.backend.entity.Item;
import com.example.backend.listner.MyListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("select * from item");
            ResultSet rst = pstm.executeQuery();

            ArrayList<Item> itemArrayList = new ArrayList<>();
            while (rst.next()) {
                String code = rst.getString(1);
                String itemName = rst.getString(2);
                int qty = rst.getInt(4);
                double unitPrice = rst.getDouble(3);

                Item item = new Item(code, itemName, unitPrice,qty);
                itemArrayList.add(item);
            }

            return itemArrayList;

        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
        return null;
    }

    @Override
    public boolean save(Item dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("insert into item values(?,?,?,?)");
            pstm.setObject(1, dto.getCode());
            pstm.setObject(2, dto.getName());
            pstm.setObject(4, dto.getQtyOnHand());
            pstm.setObject(3, dto.getPrice());

            if (pstm.executeUpdate() > 0) {
                System.out.println("Saved");
                return true;
            } else {
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    @Override
    public boolean update(Item dto) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("update Item set item_name=?,quantity=?,price=? where item_code=?");
            pstm.setObject(1, dto.getName());
            pstm.setObject(2, dto.getQtyOnHand());
            pstm.setObject(3, dto.getPrice());
            pstm.setObject(4, dto.getCode());

            if (pstm.executeUpdate() > 0) {
                System.out.println("Updated");
                return true;
            } else {
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }

    public boolean update2(Item dto) {
        System.out.println(dto);

        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("UPDATE item SET quantity = quantity - ? WHERE item_code = ?");
            pstm.setObject(1, dto.getQtyOnHand());
            pstm.setObject(2, dto.getCode());

            if (pstm.executeUpdate() > 0) {
                System.out.println("item Updated");
                return true;
            } else {
                System.out.println("item update failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }
    @Override
    public boolean delete(String code) {
        try (Connection connection = MyListener.pool.getConnection();) {
            PreparedStatement pstm = connection.prepareStatement("delete from item where item_code=?");
            pstm.setObject(1, code);

            if (pstm.executeUpdate() > 0) {
                System.out.println("Deleted");
                return true;
            } else {
                System.out.println("failed");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }

    }

    @Override
    public Item search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Item> getItemFromPrice(double price) throws ClassNotFoundException, SQLException {
        return null;
    }
}
