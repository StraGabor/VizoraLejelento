package com.example.vizoralejelento;

public class Vizora {

    private int azonosito;
    private int vizoraAllas;
    private String ugyfelNeve;
    private String email;
    private boolean befizetett;

    public Vizora(int azonosito, int vizoraAllas, String ugyfelNeve, String email, boolean befizetett) {
        this.azonosito = azonosito;
        this.vizoraAllas = vizoraAllas;
        this.ugyfelNeve = ugyfelNeve;
        this.email = email;
        this.befizetett = befizetett;
    }

    public boolean isBefizetett() {
        return befizetett;
    }

    public void setBefizetett(boolean befizetett) {
        this.befizetett = befizetett;
    }

    public int getAzonosito() {
        return azonosito;
    }

    public void setAzonosito(int azonosito) {
        this.azonosito = azonosito;
    }

    public int getVizoraAllas() {
        return vizoraAllas;
    }

    public void setVizoraAllas(int vizoraAllas) {
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
