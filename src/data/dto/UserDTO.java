package data.dto;


import data.common.Col;
import data.common.Entity;

/**
 * represents the an user DTO from the database
 */
public class UserDTO implements Entity {

    @Col(name="id")
    private Integer id;
    @Col(name="name")
    private String name;
    @Col(name="password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
