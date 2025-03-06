package service;

import entitie.Reclamation;
import utils.LoggerUtil;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class serviceReclamation implements Iservice <Reclamation> {
    Connection connection;

    public serviceReclamation(){
        connection= MyDatabase.getInstance().getConnection();
    }


    @Override
    public void ajouter(Reclamation reclamation) throws SQLException {

        String req = "INSERT INTO reclamation (Cin_Utilisateur,Sujet, Description, Date_Creation, Date_Resolution, Type_Reclamation) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(req);

        ps.setInt(1, reclamation.getCin_Utilisateur());
        ps.setString(2, reclamation.getSujet());
        ps.setString(3, reclamation.getDescription());

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        ps.setTimestamp(4, currentTimestamp);

        if (reclamation.getDate_Resolution() == null) {
            ps.setTimestamp(5, new Timestamp(0));
        } else {
            ps.setTimestamp(5, new Timestamp(reclamation.getDate_Resolution().getTime()));
        }

        ps.setString(6, reclamation.getType_Reclamation());

        ps.executeUpdate();
        System.out.println("Reclamation ajoutée !");

        LoggerUtil.logAction("Ajout", "Reclamation ajouter: " + reclamation.getSujet());
    }


    @Override
    public void modifier(Reclamation reclamation) throws SQLException {

        String req = "UPDATE reclamation SET Sujet=?, Description=?, Type_Reclamation=? WHERE ID_Reclamation=?";

        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, reclamation.getSujet());
        ps.setString(2, reclamation.getDescription());
        ps.setString(3, reclamation.getType_Reclamation());
        ps.setInt(4, reclamation.getID_Reclamation());

        ps.executeUpdate();
        System.out.println("Reclamation modifiée !");

        LoggerUtil.logAction("Modification", "Reclamation modifier: " + reclamation.getSujet() + " (ID: " + reclamation.getID_Reclamation() + ")");
    }


    @Override
    public void supprimer(String Sujet) throws SQLException {
        
        String req = "DELETE FROM reclamation WHERE Sujet = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1,Sujet);

        ps.executeUpdate();
        System.out.println("Reclamation supprimée !");

        LoggerUtil.logAction("Suppression", "Reclamation supprimer (Sujet: "+ Sujet + ")");
    }


    @Override
    public List <Reclamation> afficher() throws SQLException {

        List <Reclamation> reclamations = new ArrayList<>();
        String req= "SELECT * FROM reclamation";
        Statement statement= connection.createStatement();
        ResultSet rs= statement.executeQuery(req);

        while (rs.next()){

            Reclamation reclamation = new Reclamation();
            reclamation.setID_Reclamation(rs.getInt("ID_Reclamation"));
            reclamation.setCin_Utilisateur(rs.getInt("Cin_Utilisateur"));
            reclamation.setSujet(rs.getString("Sujet"));
            reclamation.setDescription(rs.getString("Description"));
            reclamation.setDate_Creation(rs.getTimestamp("Date_Creation"));
            reclamation.setDate_Resolution(rs.getTimestamp("Date_Resolution"));
            reclamation.setType_Reclamation(rs.getString("Type_Reclamation"));

            reclamations.add(reclamation);
        }
        return reclamations;
    }

    public List <String> getDistinctTypes() throws SQLException {

        List <String> types = new ArrayList<>();

        String req = "SELECT DISTINCT Type_Reclamation FROM reclamation";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);

        while(rs.next()){
            types.add(rs.getString("Type_Reclamation"));
        }
        return types;
    }

    public void ActualiserDate (int idReclamation) throws SQLException {

        String req = "UPDATE reclamation SET Date_Resolution = ? WHERE ID_Reclamation = ?";
        PreparedStatement ps = connection.prepareStatement(req);

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        ps.setTimestamp(1, currentTimestamp);
        ps.setInt(2, idReclamation);

        ps.executeUpdate();
        System.out.println("Date de résolution mise à jour pour la réclamation ID : " + idReclamation);
    }

}






