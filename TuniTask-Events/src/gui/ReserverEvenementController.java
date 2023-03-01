package gui;

import API.SendMsg;
import Entity.Evenement;
import Entity.Reservation;
import Service.ServiceEvenement;
import Service.ServiceReservation;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

public class ReserverEvenementController implements Initializable {

    DataSource ds = DataSource.getInstance();
    Connection cnx = ds.getCnx();
    ServiceEvenement serviceEvenement = new ServiceEvenement(cnx);

    ServiceReservation serviceReservation = new ServiceReservation(cnx);

    ObservableList<Evenement> list = FXCollections.observableArrayList();

    private int i=0;
    @FXML
    private Label LabelV;

    @FXML
    private VBox chosenOffreCard;

    @FXML
    private TableColumn<Evenement, String> date_event;

    @FXML
    private Label datemodif;
    @FXML
    private TextField nom_ev;

    @FXML
    private TextField lieu_ev;

    @FXML
    private ImageView fruitImg;

    @FXML
    private GridPane grid;

    @FXML
    private TextField keywordTextField;

    @FXML
    private TableColumn<Evenement, String> lieu_event;

    @FXML
    private Label lieu_modif;

    @FXML
    private TableColumn<Evenement, String> nom_event;

    @FXML
    private Label nom_modif;

    @FXML
    private TableView<Evenement> table_evenement;

    private Reservation1 reserv;


    @FXML
    void reserver(ActionEvent event) {
        int id_users = 2;
        Reservation reservation = new Reservation(i,id_users);
        serviceReservation.insert(reservation);
        serviceEvenement.readById(i);
        SendMsg.sendMail("yesmin.zaghden@esprit.tn");
    }

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
                reserv = new Reservation1() {
                    @Override
                    public void reserver(Evenement evenement) {
                        Reservierr(evenement);
                    }
                };
                FXMLLoader cards = new FXMLLoader();
                cards.setLocation(getClass().getResource("resversation_card.fxml"));

                AnchorPane anchorPane = cards.load();

                ReservationCardController cardController = cards.getController();


                //       offreservice.setData(offre, supp);


                cardController.setData1(serviceEvenement.readAll().get(i), reserv);

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
    private void addListenerEvent() {

        table_evenement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nom_event.setText(String.valueOf(newSelection.getNom()));
                lieu_event.setText(String.valueOf(newSelection.getLieu()));
                date_event.setText(String.valueOf(newSelection.getDate()));
            } else {
                date_event.setText("");
                date_event.setText(String.valueOf(newSelection.getDate()));
                nom_event.setText("");
                nom_event.setText(String.valueOf(newSelection.getNom()));
                lieu_event.setText("");
                lieu_event.setText(String.valueOf(newSelection.getLieu()));

            }
        });

    }

    private void Reservierr(Evenement evenement) {
        nom_ev.setText(evenement.getNom());
        lieu_ev.setText(evenement.getNom());
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
}
