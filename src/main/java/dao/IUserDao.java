package dao;

import entity.User;
import java.util.List;

public interface IUserDao {
    void addUser(User user);
    User findUserById(int id);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);
    boolean authenticate(String username, String password);
} 