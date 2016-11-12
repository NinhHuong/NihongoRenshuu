package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class GrammarExplain {
    public static final String TABLE = "GrammarExplains";

    // Labels Table Columns names
    public static final String KEY_Id = "id";
    public static final String KEY_StructureId = "grammarStructureId";
    public static final String KEY_Explaination = "explaination";
    public static final String KEY_Note = "note";

    private int id;
    private  int grammarStructureId;
    private  String explanation;
    private  String note;

    public GrammarExplain() {
    }

    public GrammarExplain(int id, int grammarStructureId, String explaination) {
        this.id = id;
        this.grammarStructureId = grammarStructureId;
        this.explanation = explaination;
    }

    public GrammarExplain(int grammarStructureId, String explaination) {
        this.grammarStructureId = grammarStructureId;
        this.explanation = explaination;
    }

    public GrammarExplain(int id, int grammarStructureId, String explaination, String note) {
        this.id = id;
        this.grammarStructureId = grammarStructureId;
        this.explanation = explaination;
        this.note = note;
    }

    public GrammarExplain(int grammarStructureId, String explaination, String note) {
        this.grammarStructureId = grammarStructureId;
        this.explanation = explaination;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrammarStructureId() {
        return grammarStructureId;
    }

    public void setGrammarStructureId(int grammarStructureId) {
        this.grammarStructureId = grammarStructureId;
    }

    public String getExplaination() {
        return explanation;
    }

    public void setExplaination(String explaination) {
        this.explanation = explaination;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Grammar Explain (" + id + ", " + grammarStructureId + ", " + explanation + ", " + note + ")\n";
    }

    public boolean compare(GrammarExplain obj2) {
        if(this.getNote() != null && obj2.getNote() != null) {
            return this.getId() == obj2.getId() && this.getGrammarStructureId() == obj2.getGrammarStructureId()
                    && this.getExplaination().equals(obj2.getExplaination()) && this.getNote().equals(obj2.getNote());
        } else {
            return this.getId() == obj2.getId() && this.getGrammarStructureId() == obj2.getGrammarStructureId()
                    && this.getExplaination().equals(obj2.getExplaination());
        }
    }

    public boolean compareIgnoreId(GrammarExplain obj2) {
        if(this.getNote() != null && obj2.getNote() != null) {
            return this.getGrammarStructureId() == obj2.getGrammarStructureId()
                    && this.getExplaination().equals(obj2.getExplaination()) && this.getNote().equals(obj2.getNote());
        } else {
            return this.getGrammarStructureId() == obj2.getGrammarStructureId()
                    && this.getExplaination().equals(obj2.getExplaination());
        }
    }
}
