package gui;

import Entity.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class eventCardController {

    @FXML
    private Label Lieu_event;

    @FXML
    private Label date_event;

    @FXML
    private ImageView hostimg;


    @FXML
    private Label nom_event;

    @FXML
    private Button supproffre;
    private Evenement evenement;

    private SupprimerCard supprimero;

    private Modifier modif;
    private Reservation1 reserv;


    public Button getSupproffre() {
        return supproffre;
    }

    @FXML
    void modifierEvent(ActionEvent event) {
        modif.modifier(evenement);
    }

    @FXML
    void supprimer(ActionEvent event) {
        supprimero.supprimer(evenement);
    }

    public void setData(Evenement evenement, SupprimerCard supp, Modifier modif) throws FileNotFoundException {
        this.evenement=evenement;
        this.supprimero=supp;
        this.modif=modif;
        nom_event.setText(evenement.getNom());
        date_event.setText(evenement.getDate().toString());
        Lieu_event.setText(evenement.getLieu());

    }







}
