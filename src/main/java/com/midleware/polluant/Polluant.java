package com.midleware.polluant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "polluants")
@XmlRootElement(name ="polluants")
public class Polluant {

    @Id
    private int id;

    @Column(name = "station_id")
    private String station_id;

    @Column(name = "o3")
    private String o3;

    @Column(name = "no2")
    private String no2;

    @Column(name = "pm2_5")
    private String pm2_5;

    // correspond aux valeurs des PM des trois dernieres heures (inclusive)
    @Transient
    private List<PM> pm;

    @Column(name = "co")
    private String co;

    @Column(name = "date")
    private String date;
    // correspond aux valeurs des 10 minutes avant l'heure (inclusive)
    @Transient
    private List<SO2> so2;

    public Polluant (){
        pm = new ArrayList<>(0);
        so2 = new ArrayList<>(0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getNo2() {
        return no2;
    }

    public void setNo2(String no2) {
        this.no2 = no2;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SO2> getSo2() {
        return so2;
    }

    public void setSo2(List<SO2> so2) {
        this.so2 = so2;
    }
}
