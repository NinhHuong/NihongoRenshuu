package com.h2n.nihongorenshuu.senzzleGame;

/**
 * Created by Catbui on 11/11/2016.
 */

public class ShowAnswer {
    private int sentenceId;
    private String btA;
    private String btB;
    private String btC;
    private String btD;
    private String answer;

    public ShowAnswer() {
    }

    public ShowAnswer(int sentenceId, String btA, String btB, String btC, String answer, String btD) {
        this.sentenceId = sentenceId;
        this.btA = btA;
        this.btB = btB;
        this.btC = btC;
        this.answer = answer;
        this.btD = btD;

    }

    public int getSentenceId() {
        return sentenceId;
    }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getBtD() {
        return btD;
    }

    public void setBtD(String btD) {
        this.btD = btD;
    }

    public String getBtC() {
        return btC;
    }

    public void setBtC(String btC) {
        this.btC = btC;
    }

    public String getBtB() {
        return btB;
    }

    public void setBtB(String btB) {
        this.btB = btB;
    }

    public String getBtA() {
        return btA;
    }

    public void setBtA(String btA) {
        this.btA = btA;
    }

    @Override
    public String toString() {
        return "ShowAnswer{" +
                "sentenceId=" + sentenceId +
                ", btA='" + btA + '\'' +
                ", btB='" + btB + '\'' +
                ", btC='" + btC + '\'' +
                ", btD='" + btD + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
