package gaaynako.transpay.crudandroidretrofit.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PersonneGson {

    @SerializedName("IdPersonne")
    @Expose
    private int idPersonne;

    @SerializedName("NomPrenomPersonne")
    @Expose
    private String name;

    @SerializedName("TelPersonne")
    @Expose
    private String telPersonne;

    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    @SerializedName("EmailPersonne")
    @Expose
    private String emailPersonne;

    @SerializedName("AdressePersonne")
    @Expose
    private String adressePersonne;

    @SerializedName("DateNaissancePersonne")
    @Expose
    private String dateNaissancePersonne;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
