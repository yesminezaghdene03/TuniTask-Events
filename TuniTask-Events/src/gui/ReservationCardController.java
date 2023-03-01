package gui;


import Entity.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

public class ReservationCardController {


    @FXML
    private Label Lieu_event;

    @FXML
    private Label date_event;

    @FXML
    private Label nom_event;
    @FXML
    private ImageView hostimg;




    private Evenement evenement;

    private Reservation1 reserv;



    public void setData1(Evenement evenement, Reservation1 reser) throws FileNotFoundException {
        this.evenement=evenement;
        this.reserv=reser;
        Lieu_event.setText(evenement.getNom());
        date_event.setText(String.valueOf(evenement.getDate()));
        nom_event.setText(evenement.getLieu());
    }

    @FXML
    void reserver_event(ActionEvent event) {
        reserv.reserver(evenement);
    }



}
