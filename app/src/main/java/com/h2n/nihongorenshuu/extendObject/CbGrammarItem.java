package com.h2n.nihongorenshuu.extendObject;

import com.h2n.nihongorenshuu.entity.Grammar;

/**
 * Created by ninhh on 11/13/2016.
 */

public class CbGrammarItem {
    private boolean isChecked;
    private Grammar grammar;

    public CbGrammarItem() {}

    public CbGrammarItem(Grammar gra, boolean isChecked) {
        this.isChecked = isChecked;
        this.grammar = gra;
    }

    public CbGrammarItem(Grammar gra) {
        this.isChecked = false;
        this.grammar = gra;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
