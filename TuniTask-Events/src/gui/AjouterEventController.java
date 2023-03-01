package gui;

import Entity.Evenement;
import Service.ServiceEvenement;
import Util.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AjouterEventController {

    DataSource ds = DataSource.getInstance();
    Connection cnx = ds.getCnx();
    ServiceEvenement serviceEvenement = new ServiceEvenement(cnx);

    @FXML
    private Button btnAjouteEven;

    @FXML
    private DatePicker date_event;

    @FXML
    private TextField lieu_event;

    @FXML
    private TextField nom_event;

    @FXML
    void ajouterEvenement(ActionEvent event) {
        if (validtext(lieu_event) & (validtext(nom_event) & (TestDate()))) {
            String lieuE = lieu_event.getText();
            String nomE = nom_event.getText();
            java.sql.Date dateE = java.sql.Date.valueOf(date_event.getValue());
            Integer id_users = 2;
            Evenement evenement = new Evenement(lieuE, dateE, nomE, List.of(id_users));
            serviceEvenement.insert(evenement);
        }
    }
    private boolean validtext(TextField tf) {
        Pattern p = Pattern.compile("[a-zA-Z0-9 ]+");
        Matcher m = p.matcher(tf.getText());
        if ((m.find() && m.group().equals(tf.getText()))) {
            tf.setEffect(null);
            return true;
        } else {
            new animatefx.animation.Shake(tf).play();
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));
            tf.setEffect(in);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("lieu ou nom invalides");
            alert.showAndWait();
            return false;
        }
    }

    private boolean TestDate() {
        java.sql.Date dateEvent = java.sql.Date.valueOf(date_event.getValue());
        long millis = System.currentTimeMillis();
        java.sql.Date now = new java.sql.Date(millis);
        if (dateEvent.compareTo(now) > 0) {
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#52FF00"));

            date_event.setEffect(in);
            return true;
        } else {
            InnerShadow in = new InnerShadow();
            in.setColor(Color.web("#f80000"));

            date_event.setEffect(in);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("S'il vous plait saisir une date valide");
            alert.showAndWait();
        }
        return false;
    }


}
