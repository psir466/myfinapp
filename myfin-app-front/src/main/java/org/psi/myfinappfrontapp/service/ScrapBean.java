package org.psi.myfinappfrontapp.service;

public class ScrapBean {

    String cours;
    String variation;

     public ScrapBean() {
        //TODO Auto-generated constructor stub
    }

    public ScrapBean(String text, String text2) {
        this.cours = text;
        this.variation = text2;
    }

    public String getCours() {
        return cours;
    }

    public void setCours(String cours) {
        this.cours = cours;
    }

    public String getVariation() {
        return variation;
    }

    public void setVariation(String variation) {
        this.variation = variation;
    }

}
