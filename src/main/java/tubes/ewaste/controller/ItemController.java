package tubes.ewaste.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.ItemMapper;
import tubes.ewaste.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemController {
    private final SqlSessionFactory factory;

    public ItemController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

public List<Item> getAllItems() {
    try (SqlSession session = factory.openSession()) {
        ItemMapper mapper = session.getMapper(ItemMapper.class);
        return mapper.getAllWithItemType(); // Panggil metode getAllWithItemType untuk mengambil data dengan ItemType
    } catch (Exception e) {
        e.printStackTrace(); // Log error
        return new ArrayList<>(); // Return list kosong jika terjadi error
    }
}


    public List<Item> getItemsByType(int itemTypeId) {
        try (SqlSession session = factory.openSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            return mapper.getByType(itemTypeId);
        }
    }

    public void addItem(Item item) {
        try (SqlSession session = factory.openSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.insert(item);
            session.commit();
        }
    }

    public void updateItem(Item item) {
        try (SqlSession session = factory.openSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.update(item);
            session.commit();
        }
    }

    public void deleteItem(int id) {
        try (SqlSession session = factory.openSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.delete(id);
            session.commit();
        }
    }
}
