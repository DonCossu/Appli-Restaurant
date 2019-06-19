package com.cossu.myapplication;



public class Restaurant {

    private int id;
    private String nom;
    private String photo;
    private double lat;
    private double lng;
    private int tel;
    private String email;
    private String description;

    public Restaurant() {

    }

    public Restaurant(int id, String nom, String photo, double lat, double lng, int tel, String email, String description, String adresse, String ville, int cp) {
        this.id = id;
        this.nom = nom;
        this.photo = photo;
        this.lat = lat;
        this.lng = lng;
        this.tel = tel;
        this.email = email;
        this.description = description;
        this.adresse = adresse;
        this.ville = ville;
        this.cp = cp;
    }

    private String adresse;
    private String ville;
    private int cp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }
}
