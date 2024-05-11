package com.example.vizoralejelento;

public class Vizora {

    private String ID;
    private String azonosito;
    private String vizoraAllas;
    private String ugyfelNeve;
    private String email;
    private String befizetett;

    public Vizora() {

    }

    public Vizora(String ID,String azonosito, String vizoraAllas, String ugyfelNeve, String email, String befizetett) {
        this.ID = ID;
        this.azonosito = azonosito;
        this.vizoraAllas = vizoraAllas;
        this.ugyfelNeve = ugyfelNeve;
        this.email = email;
        this.befizetett = befizetett;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBefizetett() {
        return befizetett;
    }

    public void setBefizetett(String befizetett) {
        this.befizetett = befizetett;
    }

    public String getAzonosito() {
        return azonosito;
    }

    public void setAzonosito(String azonosito) {
        this.azonosito = azonosito;
    }

    public String getVizoraAllas() {
        return vizoraAllas;
    }

    public void setVizoraAllas(String vizoraAllas) {
        this.vizoraAllas = vizoraAllas;
    }

    public String getUgyfelNeve() {
        return ugyfelNeve;
    }

    public void setUgyfelNeve(String ugyfelNeve) {
        this.ugyfelNeve = ugyfelNeve;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
