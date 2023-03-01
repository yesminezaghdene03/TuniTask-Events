package gui;

import Entity.Evenement;
import Entity.Reservation;
import Service.ServiceEvenement;
import Service.ServiceReservation;
import Util.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
import java.util.ResourceBundle;

public class ListReservationController implements Initializable {

    ObservableList<Reservation> listR = FXCollections.observableArrayList();

    DataSource ds = DataSource.getInstance();
    Connection cnx = ds.getCnx();
    ServiceEvenement serviceEvenement = new ServiceEvenement(cnx);

    ServiceReservation serviceReservation = new ServiceReservation(cnx);

    @FXML
    private VBox chosenOffreCard;
    private ISuppReser iSuppReser;
    @FXML
    private GridPane grid;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        int column = 1;
        int row = 0;

        for (int i = 0; i < serviceReservation.readAll().size(); i++) {

            try {

                iSuppReser = new ISuppReser() {

                    @Override
                    public void supprimer(Reservation reservation) {
                        supprimerres(reservation);
                    }
                };
                FXMLLoader cards = new FXMLLoader();
                cards.setLocation(getClass().getResource("CardDeleteReservation.fxml"));

                AnchorPane anchorPane = cards.load();

                CardDeleteReservation cardController = cards.getController();

                cardController.setData(serviceReservation.readReservEvent(i).get(i),serviceReservation.readAll().get(i), iSuppReser);

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

    private void supprimerres(Reservation reservation) {
        DataSource ds = DataSource.getInstance();
        Connection cnx = ds.getCnx();
        ServiceReservation reservation2 = new ServiceReservation(cnx);
        reservation2.delete(reservation);

    }


    private ObservableList<Evenement> getEvenList() {
        ObservableList<Evenement> EventListe = FXCollections.observableArrayList();
        DataSource ds = DataSource.getInstance();
        Connection cnx = ds.getCnx();
        ServiceReservation s = new ServiceReservation(cnx);
        EventListe = serviceReservation.readReservEvent(2);

        return EventListe;
    }
}
