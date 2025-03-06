package entitie;

import java.util.Date;

public class Reclamation {
    private int ID_Reclamation;
    private int Cin_Utilisateur;
    private String Sujet,Description;
    private Date Date_Creation;
    private Date Date_Resolution;
    private String Type_Reclamation;

    public Reclamation() {}

    public Reclamation(int ID_Reclamation,int Cin_Utilisateur ,String Sujet, String Description,Date Date_Creation, Date Date_Resolution , String Type_Reclamation) {
        this.ID_Reclamation = ID_Reclamation;
        this.Cin_Utilisateur = Cin_Utilisateur;
        this.Sujet = Sujet;
        this.Description = Description;
        this.Date_Creation = Date_Creation;
        this.Date_Resolution = Date_Resolution;
        this.Type_Reclamation = Type_Reclamation;
    }

    public Reclamation(int cin_Utilisateur, String sujet, String description, Date date_Creation, Date date_Resolution, String type_Reclamation) {
        Cin_Utilisateur = cin_Utilisateur;
        Sujet = sujet;
        Description = description;
        Date_Creation = date_Creation;
        Date_Resolution = date_Resolution;
        Type_Reclamation = type_Reclamation;
    }

    public Reclamation(String Sujet, String Description, Date Date_Creation, Date Date_Resolution , String Type_Reclamation) {
        this.Sujet = Sujet;
        this.Description = Description;
        this.Date_Creation = Date_Creation;
        this.Date_Resolution = Date_Resolution;
        this.Type_Reclamation = Type_Reclamation;
    }



    public void setID_Reclamation(int ID_Reclamation) {
        this.ID_Reclamation = ID_Reclamation;
    }

    public int getID_Reclamation() {
        return ID_Reclamation;
    }

    public int getCin_Utilisateur() {
        return Cin_Utilisateur;
    }

    public void setCin_Utilisateur(int cin_Utilisateur) {
        Cin_Utilisateur = cin_Utilisateur;
    }

    public String getSujet() {
        return Sujet;
    }

    public void setSujet(String Sujet) {
        this.Sujet = Sujet;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Date getDate_Creation() {
        return Date_Creation;
    }

    public void setDate_Creation(Date date_Creation) {
        Date_Creation = date_Creation;
    }

    public String getType_Reclamation() {
        return Type_Reclamation;
    }

    public void setType_Reclamation(String type_Reclamation) {
        Type_Reclamation = type_Reclamation;
    }

    public Date getDate_Resolution() {
        return Date_Resolution;
    }

    public void setDate_Resolution(Date date_Resolution) {
        Date_Resolution = date_Resolution;
    }


    @Override
    public String toString() {
        return "Reclamation{" +
                //"ID_Reclamation=" + ID_Reclamation +
                //" Cin_Utilisateur=" + Cin_Utilisateur +
                " Sujet = " + Sujet +
                " || Description = " + Description +
                " || Date_Creation = " + Date_Creation +
                " || Date_Resolution = " + Date_Resolution +
                " || Type_Reclamation = " + Type_Reclamation +
                "}";
    }
}
