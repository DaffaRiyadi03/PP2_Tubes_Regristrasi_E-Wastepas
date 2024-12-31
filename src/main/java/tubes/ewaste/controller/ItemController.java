package tubes.ewaste.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.ItemMapper;
import tubes.ewaste.model.Item;

import java.util.List;

public class ItemController {
    private final SqlSessionFactory factory;

    public ItemController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    public List<Item> getAllItems() {
        try (SqlSession session = factory.openSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            return mapper.getAll();
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
