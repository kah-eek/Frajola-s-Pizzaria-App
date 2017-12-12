package br.com.creativesoftwares.frajolaspizzaria.app.model;

/**
 * Created by Caique M. Oliveira on 12/10/2017.
 */

public class Pizzaria {

    private String telefone;
    private String latitude;
    private String longitude;

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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
