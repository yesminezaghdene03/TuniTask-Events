package Service;
import java.sql.*;
import java.util.List;

import Entity.Evenement;
import Entity.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class ServiceReservation implements IReservation<Reservation> {
    private Connection connection;

    public ServiceReservation(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void insert(Reservation e)
    {try {
        // Vérification que l'événement existe
        PreparedStatement checkEvenementStatement = connection.prepareStatement("SELECT id FROM evenement WHERE id = ?");
        checkEvenementStatement.setInt(1, e.getEvenementId());
        ResultSet checkEvenementResult = checkEvenementStatement.executeQuery();

        if (!checkEvenementResult.next()) {
            System.out.println("Erreur : l'événement spécifié n'existe pas.");
            return;
        }


        // Ajout de la réservation
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO reservation (evenementId, idUsers) VALUES ( ?, ?)", Statement.RETURN_GENERATED_KEYS);
        insertStatement.setInt(1, e.getEvenementId());
        insertStatement.setInt(2, e.getIdUsers());

        int affectedRows = insertStatement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("L'ajout de la réservation a échoué, aucune ligne ajoutée.");
        }

        // Récupération de l'id auto-généré
        ResultSet generatedKeys = insertStatement.getGeneratedKeys();

        if (generatedKeys.next()) {
            e.setId(generatedKeys.getInt(1));
        } else {
            throw new SQLException("L'ajout de la réservation a échoué, aucun id auto-généré retourné.");
        }

    } catch (SQLException ex) {
        System.out.println("Erreur lors de l'ajout de la réservation : " + ex.getMessage());
    }
    }

    @Override
    public void delete(Reservation e) {
        if (e == null) {
            System.out.println("Erreur : réservation nulle.");
            return;
        }

        Reservation reservation = (Reservation) e;
        int reservationId = reservation.getId();
        int evenementId = reservation.getEvenementId();

        try {
            // Vérification que la réservation existe
            PreparedStatement checkReservationStatement = connection.prepareStatement("SELECT id FROM reservation WHERE id = ?");
            checkReservationStatement.setInt(1, reservationId);
            ResultSet checkReservationResult = checkReservationStatement.executeQuery();

            if (!checkReservationResult.next()) {
                System.out.println("Erreur : réservation inexistante.");
                return;
            }

            // Suppression de la réservation
            PreparedStatement deleteReservationStatement = connection.prepareStatement("DELETE FROM reservation WHERE id = ?");
            deleteReservationStatement.setInt(1, reservationId);
            int affectedRows = deleteReservationStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La suppression de la réservation a échoué, aucune ligne supprimée.");
            }

            // Suppression de l'association de la réservation à l'événement
            PreparedStatement deleteAssociationStatement = connection.prepareStatement("DELETE FROM reservation WHERE id = ?");
            deleteAssociationStatement.setInt(1, reservationId);
            deleteAssociationStatement.executeUpdate();

            System.out.println("La réservation a été supprimée avec succès.");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de la réservation : " + ex.getMessage());
        }


    }

    @Override
    public void update(Reservation e) {
        try {
            // Vérification que la réservation existe
            PreparedStatement checkReservationStatement = connection.prepareStatement("SELECT id FROM reservation WHERE id = ?");
            checkReservationStatement.setInt(1, e.getId());
            ResultSet checkReservationResult = checkReservationStatement.executeQuery();

            if (!checkReservationResult.next()) {
                System.out.println("Erreur : la réservation spécifiée n'existe pas.");
                return;
            }

            // Vérification que l'événement existe
            PreparedStatement checkEvenementStatement = connection.prepareStatement("SELECT id FROM evenement WHERE id = ?");
            checkEvenementStatement.setInt(1, e.getEvenementId());
            ResultSet checkEvenementResult = checkEvenementStatement.executeQuery();

            if (!checkEvenementResult.next()) {
                System.out.println("Erreur : l'événement spécifié n'existe pas.");
                return;
            }

            // Mise à jour de la réservation
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE reservation SET evenementId = ?, idUsers = ? WHERE id = ?");
            updateStatement.setInt(1, e.getEvenementId());
            updateStatement.setInt(2, e.getIdUsers());
            updateStatement.setInt(3, e.getId());

            int affectedRows = updateStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La mise à jour de la réservation a échoué, aucune ligne modifiée.");
            }

            try {
                // Mise à jour de l'association entre la réservation et l'événement
                PreparedStatement updateAssociationStatement = connection.prepareStatement("UPDATE reservation SET evenementId = ? WHERE id = ?");
                updateAssociationStatement.setInt(1, e.getEvenementId());
                updateAssociationStatement.setInt(2, e.getId());
                updateAssociationStatement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la mise à jour de la réservation : " + ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour de la réservation : " + ex.getMessage());
        }
    }



    @Override
    public ObservableList<Reservation> readAll() {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservation");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getInt("id"));
                reservation.setEvenementId(resultSet.getInt("evenementId"));
                reservation.setIdUsers(resultSet.getInt("idUsers"));
                reservations.add(reservation);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des réservations : " + ex.getMessage());
        }
        return reservations;
    }
    @Override
    public ObservableList<Evenement> readReservEvent(int iduser) {
        ObservableList<Evenement> evenements = FXCollections.observableArrayList();
        try {
            String query = "SELECT R.id,E.nom,E.date,E.lieu FROM reservation R ,evenement E WHERE idUsers = ? AND R.evenementId = E.id  ";
            PreparedStatement statement = connection.prepareStatement(query);

            // Set the id parameter of the query
            statement.setInt(1, 2);

            // Execute the query and retrieve the result set
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nom = resultSet.getString("nom");
                int id_res = resultSet.getInt("id");
                Date date = resultSet.getDate("date");
                String lieu = resultSet.getString("lieu");
                Evenement evenement = new Evenement( nom, date, lieu);
                evenements.add(evenement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenements;
    }


    @Override
    public Reservation readById(int id) {
        try {
            // Define the SQL query to select the reservation by id
            String query = "SELECT * FROM reservation WHERE id = ?";

            // Create a PreparedStatement with the query
            PreparedStatement statement = connection.prepareStatement(query);

            // Set the id parameter of the query
            statement.setInt(1, id);

            // Execute the query and retrieve the result set
            ResultSet resultSet = statement.executeQuery();

            // If the result set is not empty, create a Reservation object and return it
            if (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getInt("id"));
                reservation.setEvenementId(resultSet.getInt("evenementId"));
                reservation.setIdUsers(resultSet.getInt("idUsers"));
                return reservation;
            }


            // If the result set is empty, return null
            return null;
        } catch (SQLException ex) {
            System.out.println("Error while retrieving reservation by id: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public ObservableList<Reservation> readByUserId(int id) {
        ObservableList<Reservation> reservations = FXCollections.observableArrayList();

        try {
            // Define the SQL query to select the reservation by id
            String query = "SELECT E.nom,E.date,E.lieu FROM reservation R ,evenement E WHERE idUsers = ?;";

            // Create a PreparedStatement with the query
            PreparedStatement statement = connection.prepareStatement(query);

            // Set the id parameter of the query
            statement.setInt(1, id);

            // Execute the query and retrieve the result set
            ResultSet resultSet = statement.executeQuery();

            // If the result set is not empty, create a Reservation object and return it
            if (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(resultSet.getInt("id"));
                reservation.setEvenementId(resultSet.getInt("evenementId"));
                reservation.setIdUsers(resultSet.getInt("idUsers"));
                return reservations;
            }


            // If the result set is empty, return null
            return null;
        } catch (SQLException ex) {
            System.out.println("Error while retrieving reservation by id: " + ex.getMessage());
            return null;
        }
    }



}


