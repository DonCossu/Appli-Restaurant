package com.cossu.myapplication;



public class Recette {

    private int id;
    private int note;
    private String title;
    private String photo;

    private double tarif;
    private int tempsPreparation;
    private int tempsCookRime;
    private int calories;
    private String ingredients;
    private String instructions;
    private Restaurant restaurant;

    public Recette() {

    }

    public Recette(int id, int note, String title, String photo) {
        this.setId(id);
        this.setNote(note);
        this.setTitle(title);
        this.setPhoto(photo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getTarif() {
        return tarif;
    }

    public void setTarif(double tarif) {
        this.tarif = tarif;
    }

    public int getTempsPreparation() {
        return tempsPreparation;
    }

    public void setTempsPreparation(int tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public int getTempsCookRime() {
        return tempsCookRime;
    }

    public void setTempsCookRime(int tempsCookRime) {
        this.tempsCookRime = tempsCookRime;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
