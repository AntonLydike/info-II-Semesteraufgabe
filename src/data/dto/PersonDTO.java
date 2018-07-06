package data.dto;

import data.common.Col;
import data.common.Entity;


public class PersonDTO implements Entity {
    @Col(name="name")
    private String name;

    @Col(name="rtPath")
    private String rtPath; // path on rotten tomatoes, primary ID

    @Override
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
}
