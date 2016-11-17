package com.h2n.nihongorenshuu.entity;

/**
 * Created by Huyen on 11/4/2016.
 */

public class History {
    public static final String TABLE = "History";

    // Labels Table Columns names
    public static final String KEY_Id = "id";
    public static final String KEY_SentenceId = "sentenceId";
    public static final String KEY_UserTrans = "userTrans";
    public static final String KEY_Time = "time";
    public static final String KEY_IsGame = "isGame";
    public static final String KEY_IsCorrect = "isCorrect";
    public static final String KEY_IsContinue = "isContinue";

    private  int id;
    private  int sentenceId;
    private  String userTrans;
    private  String time;
    private  boolean isGame;
    private  boolean isCorrect;
    private boolean isContinue;

    public History() {
    }

    public History(int id, int sentenceId, String userTrans, String time, boolean isGame, boolean isCorrect, boolean isContinue) {
        this.id = id;
        this.sentenceId = sentenceId;
        this.userTrans = userTrans;
        this.time = time;
        this.isGame = isGame;
        this.isCorrect = isCorrect;
        this.isContinue = isContinue;
    }

    public History(int sentenceId, String userTrans, String time, boolean isGame, boolean isCorrect, boolean isContinue) {
        this.sentenceId = sentenceId;
        this.userTrans = userTrans;
        this.time = time;
        this.isGame = isGame;
        this.isCorrect = isCorrect;
        this.isContinue = isContinue;
    }

    public History(int sentenceId, String userTrans, boolean isGame, boolean isCorrect, boolean isContinue) {
        this.sentenceId = sentenceId;
        this.userTrans = userTrans;
        this.isGame = isGame;
        this.isCorrect = isCorrect;
        this.isContinue = isContinue;
    }

    public History(int sentenceId, String userTrans, boolean isGame) {
        this.sentenceId = sentenceId;
        this.userTrans = userTrans;
        this.isGame = isGame;
        this.isCorrect = false;
        this.isContinue = true;
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

    public void setContinue(boolean aContinue) {
        isContinue = aContinue;
    }

    public boolean isContinue() {
        return isContinue;
    }

    @Override
    public String toString() {
        return "History (" + id + ", " + sentenceId + ", " + userTrans + ", " + time + ", " + isGame + ", " + isCorrect + ")\n";
    }

    public boolean compare(History obj2) {
        return this.id == obj2.getId() && this.sentenceId == obj2.getSentenceId() && this.userTrans == obj2.getUserTrans()
                && this.time.equals(obj2.getTime()) && this.isGame == obj2.isGame() && this.isCorrect == obj2.isCorrect()
                && this.isContinue == obj2.isContinue();
    }

    public boolean compareIgnoreId(History obj2) {
        return this.id == obj2.getId() && this.sentenceId == obj2.getSentenceId() && this.userTrans.equals(obj2.getUserTrans())
                && this.time.equals(obj2.getTime()) && this.isGame == obj2.isGame() && this.isCorrect == obj2.isCorrect()
                && this.isContinue == obj2.isContinue();
    }
}
