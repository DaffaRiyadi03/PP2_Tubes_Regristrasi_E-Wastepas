package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.ItemType;
import tubes.ewaste.model.Category;

import java.util.List;

public interface ItemTypeMapper {

    @Select("SELECT * FROM item_types")
    @Results({
        @Result(property = "category", column = "category_id", 
                one = @One(select = "tubes.ewaste.mapper.CategoryMapper.getCategoryById")),
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
    })
    List<ItemType> getAll();

    @Select("SELECT * FROM item_types WHERE id = #{Id}")
    @Results({
        @Result(property = "category", column = "category_id", 
                one = @One(select = "tubes.ewaste.mapper.CategoryMapper.getCategoryById")),
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "description", column = "description"),
    })
    ItemType getById(@Param("id") int id);

    @Select("SELECT * FROM item_types WHERE category_id = #{categoryId}")
    List<ItemType> getByCategoryId(int categoryId);

    @Insert("INSERT INTO item_types(name, description, category_id) VALUES(#{name}, #{description}, #{categoryId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ItemType itemType);

    @Update("UPDATE item_types SET name=#{name}, description=#{description}, category_id=#{categoryId} WHERE id=#{id}")
    void update(ItemType itemType);

    @Delete("DELETE FROM item_types WHERE id = #{id}")
    void delete(int id);
}
