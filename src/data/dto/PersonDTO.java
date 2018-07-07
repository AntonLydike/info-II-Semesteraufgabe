package data.dto;

import data.common.Col;
import data.common.Entity;


public class PersonDTO implements Entity {
    @Col(name="id")
    private Integer id;

    @Col(name="name")
    private String name;

    @Col(name="rtPath")
    private String rtPath; // path on rotten tomatoes, primary ID
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRtPath() {
        return rtPath;
    }

    public void setRtPath(String rtPath) {
        this.rtPath = rtPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
