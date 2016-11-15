package com.h2n.nihongorenshuu.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Huyen on 11/4/2016.
 */

public class Sentence {
    public static final String TABLE = "Sentences";

    // Labels Table Columns names
    public static final String KEY_Id = "id";
    public static final String KEY_GrammarExplainId = "grammarExplainId";
    public static final String KEY_JpSentence = "jpSentence";
    public static final String KEY_VnSentence = "vnSentence";

    private  int id;
    private  int grammarExplainId;
    private  String jpSentence;
    private  String vnSentence;

    public Sentence() {}

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

    public Sentence(JSONObject json) {
        try{
            this.id = Integer.parseInt(json.getString(Sentence.KEY_Id));
            this.grammarExplainId = Integer.parseInt(json.getString(Sentence.KEY_GrammarExplainId));
            this.jpSentence = json.getString(Sentence.KEY_JpSentence);
            this.vnSentence = json.getString(Sentence.KEY_VnSentence);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    @Override
    public String toString() {
        return "Sentence (" + id + ", " + grammarExplainId + id + ", " + jpSentence + id + ", " + vnSentence + ")\n";
    }

    public boolean compare(Sentence obj2) {
        return this.id == obj2.getId() && this.grammarExplainId == obj2.getGrammarExplainId()
                && this.jpSentence.equals(obj2.getJpSentence()) && this.vnSentence.equals(obj2.getVnSentence());
    }

    public boolean compareIgnoreId(Sentence obj2) {
        return this.grammarExplainId == obj2.getGrammarExplainId()
                && this.jpSentence.equals(obj2.getJpSentence()) && this.vnSentence.equals(obj2.getVnSentence());
    }
}
