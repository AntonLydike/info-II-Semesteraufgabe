package data.store.user;

import data.dto.UserDTO;
import data.store.common.BaseDao;
import exception.LoginFailedException;
import exception.RegisterFailedException;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class UserDao extends BaseDao<UserDTO> {

    private static final String table = "users";
    private static final Logger LOGGER = Logger.getLogger(UserDao.class.getName());

    public UserDao() throws SQLException, ClassNotFoundException {
        super(UserDTO.class, table);
    }

    public UserDTO loginUser(String name, String password) throws LoginFailedException {
        try {
            List<UserDTO> userDTOList = search(1, 1, new HashMap<String, String>(){{put("username", name); put("password", password);}}, null);
            if (userDTOList.size() != 1) {
                throw new LoginFailedException("Wrong credentials, please try with correct username and password again.");
            }
            return userDTOList.get(0);
        } catch (SQLException e) {
            LOGGER.severe("Failed to execute request to database: " + e.getMessage());
            throw new LoginFailedException("Request to the database failed. Please see the logs for more details.");
        }
    }

    public boolean register(String name, String password) throws RegisterFailedException {
        try {
            return executeSQL(UserConstants.SQL_REGISTER_QUERY, Arrays.asList(name, password));
        } catch (SQLException e) {
            LOGGER.severe("Failed to register user (" +name+"): " + e.getMessage());
            if( e.getMessage().contains("username_UNIQUE")) {
                throw new RegisterFailedException("Username is already taken, please use another username.");
            }
            if( e.getMessage().contains("Data too long") && e.getMessage().contains("username")) {
                throw new RegisterFailedException("Username is too long.");
            }
            if( e.getMessage().contains("Data too long") && e.getMessage().contains("password")) {
                throw new RegisterFailedException("Password is too long.");
            }
            throw new RegisterFailedException("Failed to register user. Please provide correct credentials. For more details see logfiles.");
        }
    }




}
