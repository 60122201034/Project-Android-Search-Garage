package com.example.findinggarage.model;

public class ModelMoto {
    private String name ;
    private double lati,longti;
    private String contact;

    public ModelMoto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLati() {
        return lati;
    }

    public void setLati(double lati) {
        this.lati = lati;
    }

    public double getLongti() {
        return longti;
    }

    public void setLongti(double longti) {
        this.longti = longti;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
