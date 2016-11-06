package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class Sentence {
    private  int id;
    private  int grammarExplainId;
    private  String jpSentence;
    private  String vnSentence;

    public Sentence(int id, int grammarExplainId, String jpSentence, String vnSentence) {
        this.id = id;
        this.grammarExplainId = grammarExplainId;
        this.jpSentence = jpSentence;
        this.vnSentence = vnSentence;
    }

    public Sentence(int grammarExplainId, String jpSentence, String vnSentence) {
        this.grammarExplainId = grammarExplainId;
        this.jpSentence = jpSentence;
        this.vnSentence = vnSentence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrammarExplainId() {
        return grammarExplainId;
    }

    public void setGrammarExplainId(int grammarExplainId) {
        this.grammarExplainId = grammarExplainId;
    }

    public String getJpSentence() {
        return jpSentence;
    }

    public void setJpSentence(String jpSentence) {
        this.jpSentence = jpSentence;
    }

    public String getVnSentence() {
        return vnSentence;
    }

    public void setVnSentence(String vnSentence) {
        this.vnSentence = vnSentence;
    }
}
