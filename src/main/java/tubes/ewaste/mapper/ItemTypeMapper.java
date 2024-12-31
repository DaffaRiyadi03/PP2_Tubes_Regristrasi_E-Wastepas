package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.ItemType;

import java.util.List;

public interface ItemTypeMapper {
    @Select("SELECT * FROM item_types WHERE category_id = #{categoryId}")
    List<ItemType> getByCategoryId(int categoryId);

    @Insert("INSERT INTO item_types(category_id, name, description) VALUES(#{categoryId}, #{name}, #{description})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ItemType itemType);

    @Update("UPDATE item_types SET name=#{name}, description=#{description} WHERE id=#{id}")
    void update(ItemType itemType);

    @Delete("DELETE FROM item_types WHERE id = #{id}")
    void delete(int id);
}
