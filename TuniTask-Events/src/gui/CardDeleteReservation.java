package gui;

import Entity.Evenement;
import Entity.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public class CardDeleteReservation {

    @FXML
    private Label Lieu_event;

    @FXML
    private Label date_event;

    @FXML
    private ImageView hostimg;

    @FXML
    private Label myprix;

    @FXML
    private Label myprix11;

    @FXML
    private Label nom_event;

    @FXML
    private Button supprreservation;

    private Reservation reservation1;
    private Evenement evenement1;
    private ISuppReser supprRes;

    @FXML
    void supprimerR(ActionEvent event) {
        supprRes.supprimer(reservation1);
    }

    public void setData(Evenement evenement,Reservation reservation, ISuppReser supp) throws FileNotFoundException {
        this.reservation1=reservation;
        this.evenement1=evenement;
        this.supprRes=supp;
        nom_event.setText(evenement.getNom());
        date_event.setText(evenement.getDate().toString());
        Lieu_event.setText(evenement.getLieu());

    }


}
