package com.example.backend.bo.custom.impl;

import com.example.backend.bo.custom.ItemBO;
import com.example.backend.dao.DAOFactory;
import com.example.backend.dao.custom.ItemDAO;
import com.example.backend.dto.ItemDTO;
import com.example.backend.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBoImpl implements ItemBO {

    private final ItemDAO itemDAO;

    public ItemBoImpl() {
        this.itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = itemDAO.getAll();
        ArrayList<ItemDTO> itemDTOs = new ArrayList<>();
        for (Item item : allItems) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setCode(item.getCode());
            itemDTO.setName(item.getName());
            itemDTO.setQtyOnHand(item.getQtyOnHand());
            itemDTO.setPrice(item.getPrice());
            itemDTOs.add(itemDTO);
        }
        return itemDTOs;
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        Item item = new Item();
        item.setCode(dto.getCode());
        item.setName(dto.getName());
        item.setQtyOnHand(dto.getQtyOnHand());
        item.setPrice(dto.getPrice());
        return itemDAO.save(item);
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        Item item = new Item();
        item.setCode(dto.getCode());
        item.setName(dto.getName());
        item.setQtyOnHand(dto.getQtyOnHand());
        item.setPrice(dto.getPrice());
        return itemDAO.update(item);
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean itemExist(String code) throws SQLException, ClassNotFoundException {
        // Implement logic to check if item exists
        return false;
    }

    @Override
    public String generateNewItemCode() throws SQLException, ClassNotFoundException {
        // Implement logic to generate new item code
        return null;
    }
}
