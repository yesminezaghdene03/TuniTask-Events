package gui;

import Service.ServiceEvenement;
import Util.DataSource;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import Entity.Evenement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class GererEvenementController implements Initializable {
    DataSource ds = DataSource.getInstance();
    Connection cnx = ds.getCnx();
    ServiceEvenement serviceEvenement = new ServiceEvenement(cnx);

    ObservableList<Evenement> list = FXCollections.observableArrayList();


    @FXML
    private TextField nom_modif;
    @FXML
    private TextField lieu_modif;
    @FXML
    private DatePicker datemodif;
    @FXML
    private VBox chosenOffreCard;



    private SupprimerCard sup;

    private Modifier modif;

    @FXML
    private TableColumn<Evenement, String> date_event;

    @FXML
    private ImageView fruitImg;

    @FXML
    private GridPane grid;

    Evenement evenement1;

    private int i=0;

    @FXML
    private TextField keywordTextField;

    private Button btnmodif;
    private Button btnsupprimer;

    @FXML
        private TableColumn<Evenement, String> lieu_event;

    @FXML
    private TableColumn<Evenement, String> nom_event;

    @FXML
    private TableView<Evenement> table_evenement;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        for (int i = 0; i < serviceEvenement.readAll().size(); i++) {

            list.addAll(serviceEvenement.readAll().get(i));
        }

        FilteredList<Evenement> filteredData = new FilteredList<>(list, b -> true);

        keywordTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {

            filteredData.setPredicate(e -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                if (e.getNom().toLowerCase().startsWith(searchKeyword)) {
                    return true;
                } else if (e.getLieu().toLowerCase().startsWith(searchKeyword)) {
                    return true;
                } else if (String.valueOf(e.getDate()).toLowerCase().startsWith(searchKeyword)) {
                    return true;
                } else {
                    return false;
                }

            });

            SortedList<Evenement> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(table_evenement.comparatorProperty());

            table_evenement.setItems(sortedData);
        });

        addListenerEvent();
        showEventTable();
        int column = 1;
        int row = 0;

        for (int i = 0; i < serviceEvenement.readAll().size(); i++) {

            try {

                sup = new SupprimerCard() {
                    @Override
                    public void supprimer(Evenement evenement) {
                        supprimerr(evenement);
                    }
                };
                modif = new Modifier() {
                    @Override
                    public void modifier(Evenement evenement) {
                        modifierr(evenement);
                    }

                };
                FXMLLoader cards = new FXMLLoader();
                cards.setLocation(getClass().getResource("event_card.fxml"));

                AnchorPane anchorPane = cards.load();

                eventCardController cardController = cards.getController();

                cardController.setData(serviceEvenement.readAll().get(i), sup, modif);

                //       offreservice.setData(offre, supp);
                if (column == 3) {
                    column = 1;
                    row++;

                }
                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new javafx.geometry.Insets(20));

////
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    @FXML
    void Modif(ActionEvent event) {

        String lieuE = lieu_modif.getText();
        String nomE = nom_modif.getText();
        java.sql.Date dateE = java.sql.Date.valueOf(datemodif.getValue());
        Integer id_users = 2;
        Evenement evenement = new Evenement(lieuE, dateE, nomE, List.of(id_users));
        serviceEvenement.update(i, evenement);
    }


    private void modifierr(Evenement evenement) {

       // LocalDate localDate1 = evenement.getDate()z;
       // datemodif.setValue(localDate1);

        lieu_modif.setText(evenement.getLieu());
        nom_modif.setText(evenement.getNom());
        i=evenement.getId();
    }

    public void showEventTable(){
        ObservableList<Evenement> list = getEvenList();
        nom_event.setCellValueFactory(new PropertyValueFactory<Evenement, String>("nom"));
        date_event.setCellValueFactory(new PropertyValueFactory<Evenement, String>("date"));
        lieu_event.setCellValueFactory(new PropertyValueFactory<Evenement, String>("lieu"));
        table_evenement.setItems(list);
    }
    private ObservableList<Evenement> getEvenList() {
        ObservableList<Evenement> EventListe = FXCollections.observableArrayList();
        DataSource ds = DataSource.getInstance();
        Connection cnx = ds.getCnx();
        ServiceEvenement serviceEvenement = new ServiceEvenement(cnx);
          EventListe = serviceEvenement.readAll();


        return EventListe;
    }

    private void supprimerr(Evenement evenement) {
        DataSource ds = DataSource.getInstance();
        Connection cnx = ds.getCnx();
    ServiceEvenement evenement3 = new ServiceEvenement(cnx);
        evenement3.delete(evenement.getId());

    }

    private void addListenerEvent() {

        table_evenement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
               btnsupprimer.setDisable(false);
               btnmodif.setDisable(false);
                nom_event.setText(String.valueOf(newSelection.getNom()));
                lieu_event.setText(String.valueOf(newSelection.getLieu()));
                date_event.setText(String.valueOf(newSelection.getDate()));
            } else {
                btnsupprimer.setDisable(true);
                btnmodif.setDisable(true);
                date_event.setText("");
                date_event.setText(String.valueOf(newSelection.getDate()));
                nom_event.setText("");
                nom_event.setText(String.valueOf(newSelection.getNom()));
                lieu_event.setText("");
                lieu_event.setText(String.valueOf(newSelection.getLieu()));

            }
        });

    }


}
