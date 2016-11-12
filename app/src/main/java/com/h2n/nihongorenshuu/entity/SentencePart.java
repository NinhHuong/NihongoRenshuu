package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class SentencePart {
    public static final String TABLE = "SentenceParts";

    // Labels Table Columns names
    public static final String KEY_Id = "id";
    public static final String KEY_SentenceId = "sentenceId";
    public static final String KEY_PartContent = "partContent";
    public static final String KEY_PartIndex = "partIndex";

    private  int id;
    private  int sentenceId;
    private  String partContent;
    private  int partIndex;

    public SentencePart() {}

    public SentencePart(int id, int sentenceId, String partContent, int partIndex) {
        this.id = id;
        this.sentenceId = sentenceId;
        this.partContent = partContent;
        this.partIndex = partIndex;
    }

    public SentencePart(int sentenceId, String partContent, int partIndex) {
        this.sentenceId = sentenceId;
        this.partContent = partContent;
        this.partIndex = partIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }

    public String getPartContent() {
        return partContent;
    }

    public void setPartContent(String partContent) {
        this.partContent = partContent;
    }

    public int getPartIndex() {
        return partIndex;
    }

    public void setPartIndex(int partIndex) {
        this.partIndex = partIndex;
    }

    @Override
    public String toString() {
        return "Sentence Part (" + id + ", " + sentenceId + ", " + partContent + ", " + partIndex + ")\n";
    }

    public boolean compare(SentencePart obj2) {
        return this.getId() == obj2.getId() && this.getSentenceId() == obj2.getSentenceId() &&
                this.getPartContent().equals(obj2.getPartContent()) && this.getPartIndex() == obj2.getPartIndex();
    }

    public  boolean compareIgnoreId(SentencePart obj2) {
        return this.getSentenceId() == obj2.getSentenceId() &&
                this.getPartContent().equals(obj2.getPartContent()) && this.getPartIndex() == obj2.getPartIndex();
    }
}
