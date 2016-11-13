package com.h2n.nihongorenshuu.extendObject;

/**
 * Created by ninhh on 11/13/2016.
 */

public class CbGrammarUnit {
    private String unit;
    private boolean isChecked;

    public CbGrammarUnit(){}

    public CbGrammarUnit(String unit, boolean isCheck) {
        this.isChecked = isCheck;
        this.unit = unit;
    }
    public CbGrammarUnit(String unit) {
        this.isChecked = false;
        this.unit = unit;
    }

    public void setChecked(boolean check) {
        isChecked = check;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean compareUnit(CbGrammarUnit obj2) {
        return this.unit.equals(obj2.getUnit());
    }

    public boolean equals(Object obj2) {
        return obj2 instanceof CbGrammarUnit && this.unit.equals(((CbGrammarUnit)obj2).getUnit());
    }

    public int hashCode() {
        return this.unit.length();
    }
}
