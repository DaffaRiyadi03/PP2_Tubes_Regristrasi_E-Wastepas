package tubes.ewaste.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.ItemTypeMapper;
import tubes.ewaste.model.ItemType;

import java.util.List;

public class ItemTypeController {
    private final SqlSessionFactory factory;

    public ItemTypeController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    // Get item types based on categoryId
    public List<ItemType> getItemTypesByCategory(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            return mapper.getByCategoryId(categoryId);
        }
    }

    // Add a new item type
    public void addItemType(ItemType itemType) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            mapper.insert(itemType);
            session.commit();
        }
    }

    // Update an existing item type
    public void updateItemType(ItemType itemType) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            mapper.update(itemType);
            session.commit();
        }
    }

    // Delete an item type by ID
    public void deleteItemType(int typeId) {
        try (SqlSession session = factory.openSession()) {
            ItemTypeMapper mapper = session.getMapper(ItemTypeMapper.class);
            mapper.delete(typeId);
            session.commit();
        }
    }
}
