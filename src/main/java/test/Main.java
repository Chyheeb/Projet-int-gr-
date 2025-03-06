package test;

import entitie.Reclamation;
import service.serviceReclamation;
import entitie.Reponse;
import service.serviceReponse;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {

        serviceReclamation Servicereclamation = new serviceReclamation();
        Reclamation R1= new Reclamation(14278077,"dons","nouveau_Avis",null,null,"Avis");
        Reclamation R2= new Reclamation(11677720,"Welcome","EveryOne",null,null,"ProjetDons");
        Reclamation R3= new Reclamation(11675549,"Marketplace","nouveau_Produit",null,null,"Marketplace");
        Reclamation R4= new Reclamation(14526156,"GOODBye","nouveau_Demande",null,null,"Demande");
        Reclamation R5= new Reclamation(14309087,"Event","nouveau_Evenement",null,null,"Evenement");
        Reclamation R6= new Reclamation(14309087,"Hello","Medini",null,null,"Demande");


        serviceReponse Servicereponse = new serviceReponse();
        Reponse RP1 = new Reponse(61, "Okeey", null, "Traitée");
        Reponse RP2 = new Reponse(50, "Avis", null, "En Cours");
        Reponse RP3 = new Reponse(50, "Autre", null, "Rejetée");


        try {

            //Servicereclamation.ajouter(R1);
            //Servicereclamation.ajouter(R2);
            //Servicereclamation.ajouter(R3);
            //Servicereclamation.ajouter(R4);
            //Servicereclamation.ajouter(R5);
            //Servicereclamation.ajouter(R6);
            //Servicereclamation.modifier(R4);
            //Servicereclamation.modifier(R4);
            //Servicereclamation.supprimer(9);
            //Servicereclamation.supprimer(34);
            //Servicereponse.ajouter(RP1);
            //Servicereponse.ajouter(RP2);
            //Servicereponse.ajouter(RP3);
            //Servicereponse.modifier(RP3);
            //Servicereponse.supprimer(19);
            //Servicereponse.supprimer(21);

            // Affichier les Recl
            System.out.println("----------------------------|");
            System.out.println("# Tableaux des Reclamations # ");
            System.out.println("----------------------------|");
            Servicereclamation.afficher().forEach(R ->
                    System.out.println(R.getID_Reclamation() + "||" + R.getSujet() + "||" + R.getDescription() + "||" + R.getDate_Creation() + "||" + R.getDate_Resolution() + "||" + R.getType_Reclamation()));

            System.out.println("----------------------------------------------------------------------------------------------");

            // Affichier les Rep
            System.out.println("-------------------------|");
            System.out.println("# Tableaux des Reponses # ");
            System.out.println("-------------------------|");
            Servicereponse.afficher().forEach(RP ->
                    System.out.println(RP.getID_Reponse() + "||" + RP.getID_Reclamation() + "||" + RP.getContenu() + "||" + RP.getDate_Reponse() + "||" + RP.getStatut()));

            System.out.println("-----------------------------------------------------");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
