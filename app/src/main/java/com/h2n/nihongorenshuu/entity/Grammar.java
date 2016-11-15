package com.h2n.nihongorenshuu.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Huyen on 11/4/2016.
 */

public class Grammar {
    public static final String TABLE = "Grammars";

    // Labels Table Columns names
    public static final String KEY_Id = "id";
    public static final String KEY_Level = "grammarLevel";
    public static final String KEY_Name = "name";
    public static final String KEY_Unit = "unit";

    private int id;
    private int level;
    private String name;
    private int unit;

    public Grammar(){}

    public Grammar(int id, int level, String name, int unit) {
        this.id = id;
        this.level = level;
        this.name = name;
        this.unit = unit;
    }

    public Grammar(int level, String name, int unit) {
        this.level = level;
        this.name = name;
        this.unit = unit;
    }

    public Grammar (JSONObject json) {
        try {
            this.id = Integer.parseInt(json.getString(Grammar.KEY_Id));
            this.level = Integer.parseInt(json.getString(Grammar.KEY_Level));
            this.name = json.getString(Grammar.KEY_Name).toString();
            this.unit = Integer.parseInt(json.getString(Grammar.KEY_Unit));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
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

    @Override
    public String toString() {
        return "Grammar (" + id + ", " + level + ", " + name + ", " + unit + ")\n";
    }

    public boolean compare(Grammar obj2){
        return this.getId() == obj2.getId() && this.getLevel() == obj2.getLevel() && this.getName().equals(obj2.getName())
                && this.getUnit() == obj2.getUnit();
    }

    public boolean compareIgnoreId(Grammar obj2) {
        return this.getLevel() == obj2.getLevel() && this.getName().equals(obj2.getName())
                && this.getUnit() == obj2.getUnit();
    }
}
