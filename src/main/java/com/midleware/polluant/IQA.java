package com.midleware.polluant;

public class IQA {

    private String polluant;

    private int value;


    public IQA(String polluant, int value) {
        this.polluant = polluant;
        this.value = value;
    }

    public String getPolluant() {
        return polluant;
    }

    public void setPolluant(String polluant) {
        this.polluant = polluant;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
