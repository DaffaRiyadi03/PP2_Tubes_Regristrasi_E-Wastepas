package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.Category;

import java.util.List;

public interface CategoryMapper {
    @Select("SELECT * FROM categories")
    List<Category> getAll();

    @Insert("INSERT INTO categories(name, description) VALUES(#{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Category category);

    @Update("UPDATE categories SET name=#{name}, description=#{description} WHERE id=#{id}")
    void update(Category category);

    @Delete("DELETE FROM categories WHERE id = #{id}")
    void delete(int id);
}
