package com.midleware.polluant;

import java.io.Serializable;

public class PM implements Serializable {

    // correspond a la valeur des pm
    private String pm;
    // correspond a l'heure (3 dernieres heures)
    private String date;


    public PM(String pm, String date) {
        this.pm = pm;
        this.date = date;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
