package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.Item;
import tubes.ewaste.model.ItemType;


import java.util.List;

public interface ItemMapper {

    @Select("SELECT * FROM items")
    List<Item> getAll();

    @Select("SELECT * FROM items WHERE item_type_id = #{itemTypeId}")
    List<Item> getByType(int itemTypeId);

    @Insert("INSERT INTO items (name, description, item_type_id) VALUES (#{name}, #{description}, #{itemTypeId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Item item);

    @Update("UPDATE items SET name = #{name}, description = #{description}, item_type_id = #{itemTypeId} WHERE id = #{id}")
    void update(Item item);

    @Delete("DELETE FROM items WHERE id = #{id}")
    void delete(int id);
}
