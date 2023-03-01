package gui;

import API.SendMsg;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationEvents extends Application{
    @Override
    public void start(Stage stage) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(ApplicationEvents.class.getResource("events.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1179, 775);
       stage.setTitle("TuniTask");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Button ger1;

    @FXML
    private Button ger2;

    @FXML
    void action1(ActionEvent event) throws IOException {
        Parent page2 = FXMLLoader.load(getClass().getResource("events.fxml"));
        Scene scene2 = new Scene(page2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();
    }


    @FXML
    void reseration(ActionEvent event) throws IOException {
        Parent page2 = FXMLLoader.load(getClass().getResource("events2.fxml"));
        Scene scene2 = new Scene(page2);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene2);
        window.show();

    }
    public static void main(String[] args) {
        launch();
    }
}
