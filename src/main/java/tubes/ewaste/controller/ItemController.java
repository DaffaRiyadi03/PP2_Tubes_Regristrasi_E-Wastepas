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

    public List<Item> getItemsByType(int typeId) {
        try (SqlSession session = factory.openSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            return mapper.getByTypeId(typeId);
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

    public void deleteItem(int itemId) {
        try (SqlSession session = factory.openSession()) {
            ItemMapper mapper = session.getMapper(ItemMapper.class);
            mapper.delete(itemId);
            session.commit();
        }
    }
    
}
