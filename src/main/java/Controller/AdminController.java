package Controller;

import entitie.Reclamation;
import entitie.Reponse;
import service.serviceReclamation;
import service.serviceReponse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminController implements Initializable {


    @FXML
    private Pane paneGraph;

    @FXML
    private Pane paneReclamation;

    @FXML
    private Pane paneReponse;


    @FXML
    private PieChart RECLAMATION;

    @FXML
    private PieChart REPONSE;


    @FXML
    private ListView<Reclamation> lv_reclamation;

    @FXML
    private ListView<Reponse> lv_reponse;


    @FXML
    private Label lblRatioReclamation;

    @FXML
    private Label lblRatioReponse;

    private serviceReclamation serviceReclamation;
    private serviceReponse serviceReponse;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        serviceReclamation = new serviceReclamation();
        serviceReponse = new serviceReponse();


        paneGraph.setVisible(true);
        paneReclamation.setVisible(false);
        paneReponse.setVisible(false);

        populateReclamations();
        populateReponses();


        lv_reclamation.setCellFactory (lv -> new ListCell<Reclamation>() {

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

        lv_reponse.setCellFactory(lv -> new ListCell<Reponse>() {

            @Override
            protected void updateItem(Reponse rep, boolean empty) {

                super.updateItem(rep, empty);

                if (empty || rep == null) {
                    setText(null);
                } else {

                    setText(String.format("%-30s %-30s %-50s",
                            rep.getContenu(),
                            rep.getDate_Reponse(),
                            rep.getStatut()));
                }
            }
        });
    }


    private void populateReclamations() {
        try {

            List <Reclamation> recs = serviceReclamation.afficher();
            Map <String, Long> typeCounts = recs.stream().collect(Collectors.groupingBy(Reclamation::getType_Reclamation, Collectors.counting()));

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            typeCounts.forEach((type, count) -> pieData.add(new PieChart.Data(type, count)));
            RECLAMATION.setData(pieData);

            for (PieChart.Data data : pieData) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {showReclamationsByType(data.getName());
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void populateReponses() {

        try {

            List <Reponse> reps = serviceReponse.afficher();

            Map <String, Long> statutCounts = reps.stream().collect(Collectors.groupingBy(Reponse::getStatut, Collectors.counting()));

            ObservableList <PieChart.Data> pieData = FXCollections.observableArrayList();
            statutCounts.forEach((statut, count) -> pieData.add(new PieChart.Data(statut, count)));
            REPONSE.setData(pieData);


            for (PieChart.Data data : pieData) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> { showReponsesByStatut(data.getName());
                });
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void showReclamationsByType(String type) {

        try {

            List <Reclamation> recs = serviceReclamation.afficher();

            int total = recs.size();

            List <Reclamation> filtered = recs.stream().filter(r -> r.getType_Reclamation().equals(type)).collect(Collectors.toList());

            int countFiltered = filtered.size();
            double ratio = (total > 0) ? (countFiltered * 100.0 / total) : 0;

            lv_reclamation.setItems(FXCollections.observableArrayList(filtered));
            lblRatioReclamation.setText(type + " Est : " + String.format("%.1f", ratio) + " %");

            paneGraph.setVisible(false);
            paneReclamation.setVisible(true);
            paneReponse.setVisible(false);
            lblRatioReclamation.setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void showReponsesByStatut(String statut) {

        try {

            List <Reponse> reps = serviceReponse.afficher();

            int total = reps.size();

            List <Reponse> filtered = reps.stream().filter(r -> r.getStatut().equals(statut)).collect(Collectors.toList());

            int countFiltered = filtered.size();
            double ratio = (total > 0) ? (countFiltered * 100.0 / total) : 0;

            lv_reponse.setItems(FXCollections.observableArrayList(filtered));
            lblRatioReponse.setText(statut + " Est : " + String.format("%.1f", ratio) + " %");

            paneGraph.setVisible(false);
            paneReclamation.setVisible(false);
            paneReponse.setVisible(true);
            lblRatioReponse.setVisible(true);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void refreshReclamations() {

        try {

            List <Reclamation> recs = serviceReclamation.afficher();
            lv_reclamation.setItems(FXCollections.observableArrayList(recs));

            lblRatioReclamation.setVisible(false);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void refreshReponses() {

        try {

            List <Reponse> reps = serviceReponse.afficher();
            lv_reponse.setItems(FXCollections.observableArrayList(reps));

            lblRatioReponse.setVisible(false);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void RetourReclamation() {

        paneReclamation.setVisible(false);
        paneGraph.setVisible(true);
    }

    @FXML
    public void RetourReponse() {

        paneReponse.setVisible(false);
        paneGraph.setVisible(true);
    }
}
