package service;

import data.User;
import data.dto.UserDTO;
import data.store.user.UserDao;
import exception.LoginFailedException;
import exception.RegisterFailedException;
import gui.LayoutController;

import java.sql.SQLException;

public class UserService {

    UserDao userDao;
    LayoutController layout = LayoutController.instance();

    public UserService() {
        try {
            userDao = new UserDao();
        } catch (SQLException e) {
            layout.error("Could not connect to Database! For more details see the log file or contact the administrator.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            layout.error("Unexpected error occured. Please see the logfiles for more details");
        }
    }

    public User login(String name, String password) throws LoginFailedException {
        UserDTO dto = userDao.loginUser(name, password);
        return new User(dto.getId(), dto.getUsername());
    }

    public boolean register(String name, String password) throws RegisterFailedException {
        return userDao.register(name, password);
    }
}
