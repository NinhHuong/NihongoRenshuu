package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class Grammar {
    private int id;
    private String level;
    private String name;
    private int unit;

    public Grammar(int id, String level, String name, int unit) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.unit = unit;
    }

    public Grammar(String level, String name, int unit) {
        this.level = level;
        this.name = name;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
}
