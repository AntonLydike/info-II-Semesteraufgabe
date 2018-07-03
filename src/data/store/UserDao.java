package data.store;

import data.User;
import data.dto.UserDTO;
import exception.LoginFailedException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class UserDao extends BaseDao<UserDTO>{

    private static final String table = "users";

    public UserDao() throws SQLException, ClassNotFoundException {
        super(UserDTO.class, table);
    }

    public UserDTO loginUser(String name, String password) throws LoginFailedException {
        try {
            List<UserDTO> userDTOList = search(1, 1, new HashMap<String, String>(){{put("name", name); put("password", password);}}, null);
            if (userDTOList.size() != 1) {
                throw new LoginFailedException("Wrong credentials, please try with correct username and password again.");
            }
            return userDTOList.get(0);
        } catch (SQLException e) {
            throw new LoginFailedException("Could not connect to the database.");
        }
    }




}
