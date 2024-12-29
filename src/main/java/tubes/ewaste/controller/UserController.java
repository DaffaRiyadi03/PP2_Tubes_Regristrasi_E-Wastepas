package tubes.ewaste.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.UserMapper;
import tubes.ewaste.model.User;

public class UserController {
    private final SqlSessionFactory factory;

    public UserController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    public boolean login(String email, String password) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.validateLogin(email, password) > 0;
        }
    }

    public void register(User user) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.insert(user);
            session.commit();
        }
    }

    public User findUserByEmail(String email) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getByEmail(email);
        }
    }

    public void updateProfile(User user) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
            session.commit();
        }
    }

    public List<User> getAllUsers() { try (SqlSession session = factory.openSession()) { UserMapper mapper = session.getMapper(UserMapper.class); return mapper.getAll(); } } public void deleteUser(int userId) { try (SqlSession session = factory.openSession()) { UserMapper mapper = session.getMapper(UserMapper.class); mapper.delete(userId); session.commit(); }
}
}