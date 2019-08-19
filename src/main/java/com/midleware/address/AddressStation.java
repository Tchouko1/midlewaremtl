package com.midleware.address;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "Address_Station")
@XmlRootElement(name ="Address_Station")
public class AddressStation {

    @Id
    private int id;

    @Column(name = "Numro_station")
    private int stationNumber;

    @Column(name = "Nom")
    private String nom;

    @Column(name = "Adresse")
    private String address;

    @Column(name = "ArrondissementVille")
    private String arrondissementVille;

    @Column(name = "Latitude")
    private String latitude;

    @Column(name = "Longitude")
    private String longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArrondissementVille() {
        return arrondissementVille;
    }

    public void setArrondissementVille(String arrondissementVille) {
        this.arrondissementVille = arrondissementVille;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
