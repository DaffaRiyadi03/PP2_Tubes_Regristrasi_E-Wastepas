package tubes.ewaste.mapper;

import org.apache.ibatis.annotations.*;
import tubes.ewaste.model.User;
import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM users")
    List<User> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User getById(Integer id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    User getByEmail(String email);

    @Insert("INSERT INTO users(name, email, password, address, birth_date) " +
            "VALUES(#{name}, #{email}, #{password}, #{address}, #{birthDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE users SET name=#{name}, email=#{email}, " +
            "address=#{address}, birth_date=#{birthDate} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Integer id);

    @Select("SELECT COUNT(*) FROM users WHERE email = #{email} AND password = #{password}")
    int validateLogin(@Param("email") String email, @Param("password") String password);


}