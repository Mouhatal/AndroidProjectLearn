package gaaynako.transpay.crudandroidretrofit.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Personne extends RealmObject {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey
    private int id;
    private int idPersonne;
    private String nomPrenomPersonne;
    private String telPersonne;
    private String emailPersonne;
    private String adressePersonne;
    private String dateNaissancePersonne;

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getNomPrenomPersonne() {
        return nomPrenomPersonne;
    }

    public void setNomPrenomPersonne(String nomPrenomPersonne) {
        this.nomPrenomPersonne = nomPrenomPersonne;
    }

    public String getTelPersonne() {
        return telPersonne;
    }

    public void setTelPersonne(String telPersonne) {
        this.telPersonne = telPersonne;
    }

    public String getEmailPersonne() {
        return emailPersonne;
    }

    public void setEmailPersonne(String emailPersonne) {
        this.emailPersonne = emailPersonne;
    }

    public String getAdressePersonne() {
        return adressePersonne;
    }

    public void setAdressePersonne(String adressePersonne) {
        this.adressePersonne = adressePersonne;
    }

    public String getDateNaissancePersonne() {
        return dateNaissancePersonne;
    }

    public void setDateNaissancePersonne(String dateNaissancePersonne) {
        this.dateNaissancePersonne = dateNaissancePersonne;
    }
}
