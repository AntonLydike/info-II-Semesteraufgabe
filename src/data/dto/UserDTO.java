package data.dto;


import data.common.Col;
import data.common.Entity;

/**
 * represents the an user DTO from the database
 */
public class UserDTO implements Entity {

    @Col(name="id")
    private Integer id;
    @Col(name="username")
    private String username;
    @Col(name="password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
