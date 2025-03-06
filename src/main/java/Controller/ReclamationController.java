package Controller;

import entitie.Reclamation;
import entitie.Reponse;
import service.serviceReclamation;
import service.serviceReponse;

import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ReclamationController implements Initializable {


    @FXML
    private Pane paneAjout, paneModifier, paneAffichage,paneAffichage1;

    @FXML
    private TextField tf_sujet;

    @FXML
    private TextField tf_description;

    @FXML
    private ComboBox <String> tf_type;

    @FXML
    private ComboBox tf_type1;

    @FXML
    private TextField tf_description1;

    @FXML
    private TextField tf_sujet1;

    @FXML
    private ListView <Reclamation> lv_reclamation;

    @FXML
    private ListView lv_reponse;

    private
    serviceReclamation serviceReclamation;

    private
    serviceReponse serviceReponse;



    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        serviceReclamation = new serviceReclamation();
        serviceReponse = new serviceReponse();

        try {

            List <String> types = serviceReclamation.getDistinctTypes();

            ObservableList <String> observableTypes = FXCollections.observableArrayList(types);
            tf_type.setItems(observableTypes);
            tf_type1.setItems(observableTypes);

        } catch (SQLException ex) {
            System.err.println("Erreur lors du chargement des types : " + ex.getMessage());
        }

        refreshReclamations();

        lv_reclamation.setCellFactory (lv -> new ListCell <Reclamation>() {

            @Override
            protected void updateItem(Reclamation rec, boolean empty) {
                super.updateItem(rec, empty);
                if (empty || rec == null) {
                    setText(null);
                } else {

                    setText(String.format("%-15s %-25s %-35s %-35s %-30s",
                            rec.getSujet(),
                            rec.getDescription(),
                            rec.getDate_Creation(),
                            rec.getDate_Resolution(),
                            rec.getType_Reclamation()));
                }
            }
        });

        lv_reponse.setCellFactory (lv -> new ListCell <Reponse>() {

            @Override
            protected void updateItem(Reponse rec, boolean empty) {

                super.updateItem(rec, empty);

                if (empty || rec == null) {
                    setText(null);
                } else {

                    setText(String.format("%-30s %-30s %-50s",
                            rec.getContenu(),
                            rec.getDate_Reponse(),
                            rec.getStatut()));
                }
            }
        });
    }

    private void refreshReclamations() {

        try {

            ObservableList<Reclamation> list = FXCollections.observableArrayList(serviceReclamation.afficher());
            lv_reclamation.setItems(list);

        } catch (SQLException ex) {
            System.err.println("Erreur lors du chargement des réclamations : " + ex.getMessage());
        }
    }

    @FXML
    private void AfficherPaneAjout() {
        paneAjout.setVisible(true);
        paneModifier.setVisible(false);
        paneAffichage.setVisible(false);
        paneAffichage1.setVisible(false);
        System.out.println("Affichage du formulaire d'ajout.");
    }

    @FXML
    private void AfficherPaneUpdate() {
        paneAjout.setVisible(false);
        paneModifier.setVisible(true);
        paneAffichage.setVisible(false);
        paneAffichage1.setVisible(false);
        System.out.println("Affichage du formulaire de modification.");
    }

    @FXML
    private void AfficherPaneAffichage() {
        paneAjout.setVisible(false);
        paneModifier.setVisible(false);
        paneAffichage.setVisible(true);
        paneAffichage1.setVisible(false);
        System.out.println("Affichage du formulaire d'affichage.");
        refreshReclamations();
    }


    @FXML
    public void AjouterReclamation(ActionEvent actionEvent) {

        String sujet = tf_sujet.getText();
        String description = tf_description.getText();
        String type = tf_type.getValue();

        if (sujet.trim().isEmpty() || description.trim().isEmpty() || type == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        try {

            List <Reclamation> existingReclamations = serviceReclamation.afficher();

            for (Reclamation rec : existingReclamations) {

                if (rec.getSujet().equalsIgnoreCase(sujet) && rec.getDescription().equalsIgnoreCase(description) && rec.getType_Reclamation().equalsIgnoreCase(type)) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Doublon détecté");
                    alert.setHeaderText(null);
                    alert.setContentText("Cette réclamation existe déjà !");
                    alert.showAndWait();
                    return;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la vérification des doublons : " + ex.getMessage());
        }

        int cinUtilisateurConnecte = 14278077;

        Reclamation reclamation = new Reclamation(cinUtilisateurConnecte,sujet, description, null, null, type);

        try {

            serviceReclamation.ajouter(reclamation);

            SMSController.sendSMS("+21692581168", "Votre Réclamation '" + sujet + "' a été ajoutée avec succès !");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Réclamation ajoutée avec succès !");
            alert.showAndWait();

            tf_sujet.clear();
            tf_description.clear();
            tf_type.getSelectionModel().clearSelection();
            refreshReclamations();
            AfficherPaneAffichage();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'ajout");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public void SupprimerReclamation(ActionEvent actionEvent) {

        Reclamation selected = lv_reclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réclamation à supprimer.");
            alert.showAndWait();
            return;
        }

        try {

            serviceReclamation.supprimer(selected.getSujet());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Réclamation supprimée avec succès !");
            alert.showAndWait();
            refreshReclamations();

        } catch (SQLException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la suppression");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void PreparerModification(ActionEvent actionEvent) {

        Reclamation selected = lv_reclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réclamation à modifier.");
            alert.showAndWait();
            return;
        }

        tf_sujet1.setText(selected.getSujet());
        tf_description1.setText(selected.getDescription());
        tf_type1.setValue(selected.getType_Reclamation());

        paneAjout.setVisible(false);
        paneModifier.setVisible(true);
        paneAffichage.setVisible(false);
    }


    @FXML
    public void ModifierReclamation(ActionEvent actionEvent) {

        Reclamation selected = lv_reclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Aucune réclamation sélectionnée pour modification.");
            alert.showAndWait();
            return;
        }

        String newSujet = tf_sujet1.getText().trim();
        String newDescription = tf_description1.getText().trim();
        String newType = (String) tf_type1.getValue();

        if (newSujet.isEmpty() || newDescription.isEmpty() || newType == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs dans le formulaire de modification.");
            alert.showAndWait();
            return;
        }

        try {

            List <Reclamation> existingReclamations = serviceReclamation.afficher();

            for (Reclamation rec : existingReclamations) {

                if (rec.getID_Reclamation() != selected.getID_Reclamation() &&
                        rec.getSujet().equalsIgnoreCase(newSujet) &&
                        rec.getDescription().equalsIgnoreCase(newDescription) &&
                        rec.getType_Reclamation().equalsIgnoreCase(newType)) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Doublon détecté");
                    alert.setHeaderText(null);
                    alert.setContentText("Une réclamation avec ces valeurs existe déjà !");
                    alert.showAndWait();
                    return;
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la vérification des doublons : " + ex.getMessage());

        }

        try {

            Reclamation updated = new Reclamation(selected.getID_Reclamation(), selected.getCin_Utilisateur(), newSujet, newDescription, null, null, newType);
            serviceReclamation.modifier(updated);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Réclamation modifiée avec succès !");
            alert.showAndWait();
            refreshReclamations();
            AfficherPaneAffichage();

        } catch (SQLException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la modification");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void AfficherReponse(ActionEvent actionEvent) {

        Reclamation selected = lv_reclamation.getSelectionModel().getSelectedItem();

        if (selected == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réclamation pour afficher ses réponses.");
            alert.showAndWait();
            return;
        }
        try {

            List <Reponse> reponses = serviceReponse.afficherByReclamation(selected.getID_Reclamation());
            ObservableList <Reponse> observableReponses = FXCollections.observableArrayList(reponses);
            lv_reponse.setItems(observableReponses);

            paneAffichage.setVisible(false);
            paneAffichage1.setVisible(true);

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors du chargement des réponses");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }


    @Deprecated
    public void AfficherReclamation(ActionEvent actionEvent) {
        refreshReclamations();
    }

    @FXML
    public void RetourAuList(ActionEvent actionEvent) {

        paneAffichage.setVisible(true);
        paneAffichage1.setVisible(false);
    }
}
