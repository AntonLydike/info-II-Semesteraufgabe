package service;

import data.dto.UserDTO;
import data.store.UserDao;
import exception.LoginFailedException;

import java.sql.SQLException;

public class UserService {

    UserDao userDao;

    public UserService() {
        try {
            userDao = new UserDao();
        } catch (SQLException e) {
            System.out.println("THROWS SQL EXCEPTION");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public UserDTO login(String name, String password) throws LoginFailedException {
        return userDao.loginUser(name, password);
    }
}
