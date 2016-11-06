package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class GrammarStructure {
    private  int id;
    private int grammarId;
    private String structure;

    public GrammarStructure(int id, int grammarId, String structure) {
        this.id = id;
        this.grammarId = grammarId;
        this.structure = structure;
    }

    public GrammarStructure(int grammarId, String structure) {
        this.grammarId = grammarId;
        this.structure = structure;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrammarId() {
        return grammarId;
    }

    public void setGrammarId(int grammarId) {
        this.grammarId = grammarId;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }
}
