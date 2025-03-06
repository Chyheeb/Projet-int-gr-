package entitie;

import java.util.Date;

public class Reponse {
    private int ID_Reponse;
    private int ID_Reclamation;
    private String Contenu;
    private Date Date_Reponse;
    private String Statut;


    public Reponse() {}

    public Reponse(int ID_Reponse, int ID_Reclamation, String Contenu, Date Date_Reponse, String Statut){
        this.ID_Reponse = ID_Reponse;
        this.ID_Reclamation = ID_Reclamation;
        this.Contenu = Contenu;
        this.Date_Reponse = Date_Reponse;
        this.Statut = Statut;
    }

    public Reponse(int ID_Reclamation, String Contenu, Date Date_Reponse, String Statut){
        this.ID_Reclamation = ID_Reclamation;
        this.Contenu = Contenu;
        this.Date_Reponse = Date_Reponse;
        this.Statut = Statut;
    }

    public Reponse(String contenu, Date date_Reponse, String statut) {
        Contenu = contenu;
        Date_Reponse = date_Reponse;
        Statut = statut;
    }

    public int getID_Reponse() {
        return ID_Reponse;
    }

    public void setID_Reponse(int ID_Reponse) {
        this.ID_Reponse = ID_Reponse;
    }

    public int getID_Reclamation() {
        return ID_Reclamation;
    }

    public void setID_Reclamation(int ID_Reclamation) {
        this.ID_Reclamation = ID_Reclamation;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String contenu) {
        Contenu = contenu;
    }

    public Date getDate_Reponse() {
        return Date_Reponse;
    }

    public void setDate_Reponse(Date date_Reponse) {
        Date_Reponse = date_Reponse;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String statut) {
        Statut = statut;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "Contenu='" + Contenu + '\'' +
                ", Date_Reponse=" + Date_Reponse +
                ", Statut='" + Statut + '\'' +
                '}';
    }
}