package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class eventController {

    @FXML
    private Button btnListEvent;

    @FXML
    private Button btnajout;
    @FXML
    private Button btnReserv;
    @FXML
    private GridPane grid1;

    private Pane pnlCustomer;

    @FXML
    void ajouterEvent(ActionEvent event) {
        grid1.getChildren().clear();
        try {
            // TODO
            FXMLLoader cards = new FXMLLoader();
            cards.setLocation(getClass().getResource("ajouterEvent.fxml"));

            AnchorPane anchorPane = cards.load();

            grid1.add(anchorPane, 1, 1);

            GridPane.setMargin(anchorPane, new javafx.geometry.Insets(10));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    void modifEvent(ActionEvent event) {
        grid1.getChildren().clear();
        try {
            // TODO
            FXMLLoader cards = new FXMLLoader();
            cards.setLocation(getClass().getResource("gererEvenement.fxml"));

            AnchorPane anchorPane = cards.load();

            grid1.add(anchorPane, 1, 1);

            GridPane.setMargin(anchorPane, new javafx.geometry.Insets(10));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    void modifReserv(ActionEvent event) {
        grid1.getChildren().clear();
        try {
            // TODO
            FXMLLoader cards = new FXMLLoader();
            cards.setLocation(getClass().getResource("reserverEvenement.fxml"));

            AnchorPane anchorPane = cards.load();

            grid1.add(anchorPane, 1, 1);

            GridPane.setMargin(anchorPane, new javafx.geometry.Insets(10));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @FXML
    void ListReserv(ActionEvent event) {
        grid1.getChildren().clear();
        try {
            // TODO
            FXMLLoader cards = new FXMLLoader();
            cards.setLocation(getClass().getResource("ListReservation.fxml"));

            AnchorPane anchorPane = cards.load();

            grid1.add(anchorPane, 1, 1);

            GridPane.setMargin(anchorPane, new javafx.geometry.Insets(10));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
