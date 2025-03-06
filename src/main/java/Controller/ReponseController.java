package Controller;

import entitie.Reponse;
import entitie.Reclamation;
import service.serviceReponse;
import service.serviceReclamation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ReponseController implements Initializable {


    @FXML private Pane paneRepAjout, paneRepModifier, paneRepAffichage, paneRecAffichage;

    @FXML
    private TextField tf_rep_sujet;

    @FXML
    private TextField tf_rep_contenu;

    @FXML
    private ComboBox<String> combo_rep_statut;

    @FXML
    private TextField tf_rep_contenu_modif;

    @FXML
    private ComboBox<String> combo_rep_statut_modif;

    @FXML
    private ListView<Reponse> lv_reponse;

    @FXML
    private ListView<Reclamation> lv_reclamation;

    private
    serviceReponse serviceReponse;

    private
    serviceReclamation serviceReclamation;

    private
    Reponse currentResponse;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        serviceReponse = new serviceReponse();
        serviceReclamation = new serviceReclamation();


        try {
            List <String> statuts = serviceReponse.getDistinctStatuts();

            ObservableList<String> observableStatuts = FXCollections.observableArrayList(statuts);
            combo_rep_statut.setItems(observableStatuts);
            combo_rep_statut_modif.setItems(observableStatuts);

        } catch (SQLException ex) {
            System.err.println("Erreur lors du chargement des statuts : " + ex.getMessage());
        }

        refreshReponses();
        refreshReclamations();


        lv_reclamation.setCellFactory(lv -> new ListCell <Reclamation>() {
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

        lv_reponse.setCellFactory(lv -> new ListCell <Reponse>() {

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

    private void refreshReponses() {

        try {

            List <Reponse> reponses = serviceReponse.afficher();
            ObservableList<Reponse> observableList = FXCollections.observableArrayList(reponses);
            lv_reponse.setItems(observableList);

        } catch (SQLException ex) {
            System.err.println("Erreur lors du chargement des réponses : " + ex.getMessage());
        }
    }

    private void refreshReclamations() {

        try {

            List<Reclamation> reclamations = serviceReclamation.afficher();
            ObservableList<Reclamation> observableList = FXCollections.observableArrayList(reclamations);
            lv_reclamation.setItems(observableList);

        } catch (SQLException ex) {
            System.err.println("Erreur lors du chargement des réclamations : " + ex.getMessage());
        }
    }

    @FXML
    private void AfficherPaneRepAjout() {
        paneRepAjout.setVisible(true);
        paneRepModifier.setVisible(false);
        paneRepAffichage.setVisible(false);
        paneRecAffichage.setVisible(false);
    }

    @FXML
    private void AfficherPaneRepModifier() {
        paneRepAjout.setVisible(false);
        paneRepModifier.setVisible(true);
        paneRepAffichage.setVisible(false);
        paneRecAffichage.setVisible(false);
    }

    @FXML
    private void AfficherPaneRepAffichage() {
        paneRepAjout.setVisible(false);
        paneRepModifier.setVisible(false);
        paneRepAffichage.setVisible(true);
        paneRecAffichage.setVisible(false);
        refreshReponses();
    }


    @FXML
    public void AjouterReponse(ActionEvent event) {

        String repContenu = tf_rep_contenu.getText().trim();
        String repStatut = combo_rep_statut.getValue();
        String repSujet = tf_rep_sujet.getText().trim();

        if (repSujet.isEmpty() || repContenu.isEmpty() || repStatut == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs (Sujet, Contenu, Statut) !");
            alert.showAndWait();
            return;
        }

        Reclamation associatedRec = null;

        try {

            List <Reclamation> reclamations = serviceReclamation.afficher();

            for (Reclamation rec : reclamations) {
                if (rec.getSujet().equalsIgnoreCase(repSujet)) {
                    associatedRec = rec;
                    break;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la recherche de la réclamation : " + ex.getMessage());
        }

        if (associatedRec == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Réclamation non trouvée");
            alert.setHeaderText(null);
            alert.setContentText("Aucune réclamation trouvée avec le sujet : " + repSujet);
            alert.showAndWait();
            return;
        }

        int idReclamation = associatedRec.getID_Reclamation();

        Reponse reponse = new Reponse(idReclamation, repContenu, null, repStatut);

        try {

            serviceReponse.ajouter(reponse);
            serviceReclamation.ActualiserDate(idReclamation);

            SMSController.sendSMS("+21692581168", "Votre Réclamation '" + repSujet + "' est : " + repStatut );

            if (repStatut.equalsIgnoreCase("En Cours")) {

                String organisateurEmail = "medinishyheb11@gmail.com";
                String emailSubject = "Rappel : Veuillez Mettre à jour la réclamation";
                String emailContent = "Votre réponse pour la réclamation '" + repSujet + "' est actuellement en statut 'En Cours'. "
                        + "Merci de mettre à jour le statut dès que possible.";

                EmailController.sendScheduledEmail(organisateurEmail, emailSubject, emailContent, 60);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Réponse ajoutée avec succès !");
            alert.showAndWait();

            tf_rep_sujet.clear();
            tf_rep_contenu.clear();
            combo_rep_statut.getSelectionModel().clearSelection();

            refreshReponses();
            AfficherPaneRepAffichage();

        } catch (SQLException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de l'ajout");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public void SupprimerReponse(ActionEvent event) {

        Reponse selected = lv_reponse.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réponse à supprimer.");
            alert.showAndWait();
            return;
        }

        try {

            serviceReponse.supprimer(selected.getContenu());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Réponse supprimée avec succès !");
            alert.showAndWait();
            refreshReponses();

        } catch (SQLException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la suppression");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public void PreparerModificationRep(ActionEvent event) {

        Reponse selected = lv_reponse.getSelectionModel().getSelectedItem();

        if (selected == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réponse à modifier.");
            alert.showAndWait();
            return;
        }

        tf_rep_contenu_modif.setText(selected.getContenu());
        combo_rep_statut_modif.setValue(selected.getStatut());
        currentResponse = selected;
        AfficherPaneRepModifier();

    }


    @FXML
    public void ModifierReponse(ActionEvent event) {

        if (currentResponse == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Aucune réponse sélectionnée pour modification.");
            alert.showAndWait();
            return;
        }

        String newContenu = tf_rep_contenu_modif.getText().trim();
        String newStatut = combo_rep_statut_modif.getValue();

        if (newContenu.isEmpty() || newStatut == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs manquants");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.showAndWait();
            return;
        }

        currentResponse.setContenu(newContenu);
        currentResponse.setStatut(newStatut);

        try {

            serviceReponse.modifier(currentResponse);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Réponse modifiée avec succès !");
            alert.showAndWait();
            refreshReponses();
            AfficherPaneRepAffichage();

            paneRepModifier.setVisible(false);
            paneRepAffichage.setVisible(true);

        } catch (SQLException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la modification");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public void AfficherReclamation(ActionEvent event) {

        Reponse selectedResponse = lv_reponse.getSelectionModel().getSelectedItem();

        if (selectedResponse == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une réponse pour afficher sa réclamation associée.");
            alert.showAndWait();
            return;
        }

        try {

            List <Reclamation> reclamations = serviceReclamation.afficher();
            ObservableList<Reclamation> associatedList = FXCollections.observableArrayList();

            for (Reclamation rec : reclamations) {
                if (rec.getID_Reclamation() == selectedResponse.getID_Reclamation()) {
                    associatedList.add(rec);
                }
            }

            lv_reclamation.setItems(associatedList);
            paneRepAffichage.setVisible(false);
            paneRecAffichage.setVisible(true);

        } catch (SQLException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors du chargement de la réclamation associée");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    public void RetourAuList(ActionEvent actionEvent) {
        paneRecAffichage.setVisible(false);
        paneRepAffichage.setVisible(true);
    }
}
