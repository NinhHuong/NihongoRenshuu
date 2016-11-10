package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class GrammarStructure {
    public static final String TABLE = "GrammarStructures";

    // Labels Table Columns names
    public static final String KEY_Id = "id";
    public static final String KEY_GrammarId = "grammarId";
    public static final String KEY_Structure = "structure";
    public static final String KEY_Note = "note";

    private  int id;
    private int grammarId;
    private String structure;
    private String note;

    public GrammarStructure() {
    }

    public GrammarStructure(int id, int grammarId, String structure) {
        this.id = id;
        this.grammarId = grammarId;
        this.structure = structure;
    }

    public GrammarStructure(int grammarId, String structure) {
        this.grammarId = grammarId;
        this.structure = structure;
    }

    public GrammarStructure(int id, int grammarId, String structure, String note) {
        this.id = id;
        this.grammarId = grammarId;
        this.structure = structure;
        this.note = note;
    }

    public GrammarStructure(int grammarId, String structure, String note) {
        this.grammarId = grammarId;
        this.structure = structure;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Grammar Structure (" + id + ", " + grammarId + ", " + structure + ", " + note + ")";
    }

    public boolean compare(GrammarStructure obj2) {
        if(this.getNote() != null && obj2.getNote() != null) {
            return this.getId() == obj2.getId() && this.getGrammarId() == obj2.getGrammarId()
                    && this.getStructure().equals(obj2.getStructure()) && this.getNote().equals(obj2.getNote());
        } else {
            return this.getId() == obj2.getId() && this.getGrammarId() == obj2.getGrammarId()
                    && this.getStructure().equals(obj2.getStructure());
        }
    }

    public boolean compareIgnoreId(GrammarStructure obj2) {
        if(this.getNote() != null && obj2.getNote() != null) {
            return this.getId() == obj2.getId() && this.getGrammarId() == obj2.getGrammarId()
                    && this.getStructure().equals(obj2.getStructure()) && this.getNote().equals(obj2.getNote());
        } else {
            return this.getId() == obj2.getId() && this.getGrammarId() == obj2.getGrammarId()
                    && this.getStructure().equals(obj2.getStructure());
        }
    }
}
