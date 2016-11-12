package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class History {
    private  int id;
    private  int sentenceId;
    private  String userTrans;
    private  String time;
    private  boolean isGame;
    private  boolean isCorrect;

    public History() {
    }

    public History(int id, int sentenceId, String userTrans, String time, boolean isGame, boolean isCorrect) {
        this.id = id;
        this.sentenceId = sentenceId;
        this.userTrans = userTrans;
        this.time = time;
        this.isGame = isGame;
        this.isCorrect = isCorrect;
    }

    public History(int sentenceId, String userTrans, String time, boolean isGame, boolean isCorrect) {
        this.sentenceId = sentenceId;
        this.userTrans = userTrans;
        this.time = time;
        this.isGame = isGame;
        this.isCorrect = isCorrect;
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

    public String getUserTrans() {
        return userTrans;
    }

    public void setUserTrans(String userTrans) {
        this.userTrans = userTrans;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isGame() {
        return isGame;
    }

    public void setGame(boolean game) {
        isGame = game;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
