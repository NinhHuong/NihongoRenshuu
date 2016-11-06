package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class GrammarExplain {
    private int id;
    private  int grammarStructureId;
    private  String explanation;

    public GrammarExplain(int id, int grammarStructureId, String explaination) {
        this.id = id;
        this.grammarStructureId = grammarStructureId;
        this.explanation = explaination;
    }

    public GrammarExplain(int grammarStructureId, String explaination) {
        this.grammarStructureId = grammarStructureId;
        this.explanation = explaination;
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
}
