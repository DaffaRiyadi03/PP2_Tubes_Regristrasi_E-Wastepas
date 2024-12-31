package tubes.ewaste.controller;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.CategoryMapper;
import tubes.ewaste.model.Category;

import java.util.List;

public class CategoryController {
    private final SqlSessionFactory factory;

    public CategoryController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    public List<Category> getAllCategories() {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            return mapper.getAll();
        }
    }

    public void addCategory(Category category) {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            mapper.insert(category);
            session.commit();
        }
    }

    public void updateCategory(Category category) {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            mapper.update(category);
            session.commit();
        }
    }

    public void deleteCategory(int categoryId) {
        try (SqlSession session = factory.openSession()) {
            CategoryMapper mapper = session.getMapper(CategoryMapper.class);
            mapper.delete(categoryId);
            session.commit();
        }
    }
}
