package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class SentencePart {
    private  int id;
    private  int sentenceId;
    private  String partContent;
    private  int partIndex;

    public static final String TABLE = "SentenceParts";

    // Labels Table Columns names
    public static final String KEY_Id = "id";
    public static final String KEY_SentenceId = "sentenceId";
    public static final String KEY_PartContent = "partContent";
    public static final String KEY_PartIndex = "partIndex";

    public SentencePart() {
    }

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
}
