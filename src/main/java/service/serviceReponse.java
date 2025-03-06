package service;

import entitie.Reponse;
import utils.LoggerUtil;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class serviceReponse  implements Iservice <Reponse> {
    //Déclaration et Initialisation de la Connexion
    Connection connection;

    public serviceReponse(){
        connection= MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Reponse reponse) throws SQLException {

        String req = "INSERT INTO reponse (ID_Reclamation, Contenu, Date_Reponse, Statut) VALUES (?, ?, ?, ?)";


        PreparedStatement ps = connection.prepareStatement(req);

        ps.setInt(1, reponse.getID_Reclamation());
        ps.setString(2, reponse.getContenu());


        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
        ps.setTimestamp(3, currentTimestamp);

        ps.setString(4, reponse.getStatut());

        ps.executeUpdate();
        System.out.println("Reponse ajoutée !");

        LoggerUtil.logAction("Ajout", "Reponse Ajouter : Reclamation " + reponse.getContenu() + " est " + reponse.getStatut());

    }

    @Override
    public void modifier(Reponse reponse) throws SQLException {

        String req = "UPDATE reponse SET ID_Reclamation=?, Contenu=?, Date_Reponse=?, Statut=? WHERE ID_Reponse=?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, reponse.getID_Reclamation());
        ps.setString(2, reponse.getContenu());

        if (reponse.getDate_Reponse() != null) {
            ps.setTimestamp(3, new java.sql.Timestamp(reponse.getDate_Reponse().getTime()));
        } else {
            ps.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
        }

        ps.setString(4, reponse.getStatut());
        ps.setInt(5, reponse.getID_Reponse());

        ps.executeUpdate();
        System.out.println("Reponse modifiée !");

        LoggerUtil.logAction("Modification ", "Reponse Modifier: (ID:" + reponse.getID_Reclamation()+ ")" + " Reclamation " + reponse.getContenu() + " est " + reponse.getStatut());

    }


    @Override
    public void supprimer(String Contenu) throws SQLException {

        String req = "DELETE FROM reponse WHERE Contenu = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,Contenu);

        ps.executeUpdate();
        System.out.println("Reponse supprimée !");

        LoggerUtil.logAction("Suppression", "Reclamation supprimer (Sujet: "+ Contenu + ")");

    }


    @Override
    public  List <Reponse> afficher() throws SQLException {

        List<Reponse> reponses = new ArrayList<>();
        String req = "SELECT * FROM reponse";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);

        while (rs.next()) {

            Reponse rep = new Reponse();
            rep.setID_Reponse(rs.getInt("ID_Reponse"));
            rep.setID_Reclamation(rs.getInt("ID_Reclamation"));
            rep.setContenu(rs.getString("Contenu"));
            rep.setDate_Reponse(rs.getDate("Date_Reponse"));
            rep.setStatut(rs.getString("Statut"));
            reponses.add(rep);
        }
        return reponses;
    }

    public List <String> getDistinctStatuts() throws SQLException {

        List <String> statuts = new ArrayList<>();

        String req = "SELECT DISTINCT Statut FROM reponse";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);

        while(rs.next()){
            statuts.add(rs.getString("Statut"));
        }
        return statuts;
    }


    public List<Reponse> afficherByReclamation(int idReclamation) throws SQLException {

        List <Reponse> reponses = new ArrayList<>();

        String req = "SELECT * FROM reponse WHERE ID_Reclamation = ?";

        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, idReclamation);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Reponse rep = new Reponse();
            rep.setID_Reponse(rs.getInt("ID_Reponse"));
            rep.setID_Reclamation(rs.getInt("ID_Reclamation"));
            rep.setContenu(rs.getString("Contenu"));
            rep.setDate_Reponse(rs.getTimestamp("Date_Reponse"));
            rep.setStatut(rs.getString("Statut"));
            reponses.add(rep);
        }
        return reponses;
    }


}
