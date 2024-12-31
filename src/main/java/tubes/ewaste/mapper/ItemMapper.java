package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.Item;

import java.util.List;

public interface ItemMapper {
    @Select("SELECT * FROM items WHERE type_id = #{typeId}")
    List<Item> getByTypeId(int typeId);

    @Insert("INSERT INTO items(type_id, name, description, price, created_at) " +
            "VALUES(#{typeId}, #{name}, #{description}, #{price}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Item item);

    @Update("UPDATE items SET name=#{name}, description=#{description}, price=#{price} WHERE id=#{id}")
    void update(Item item);

    @Delete("DELETE FROM items WHERE id = #{id}")
    void delete(int id);
}
