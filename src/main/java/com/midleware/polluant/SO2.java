package com.midleware.polluant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "so2_data")
@XmlRootElement(name ="so2_data")
public class SO2 {

    @Id
    private int id;

    @Column(name = "so2")
    private String so2;

    @Column(name = "station_id")
    private String station_id;


    @Column(name = "date")
    private String date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSo2() {
        return so2;
    }

    public void setSo2(String so2) {
        this.so2 = so2;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
