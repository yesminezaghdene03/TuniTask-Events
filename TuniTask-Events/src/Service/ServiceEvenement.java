
package Service;
import Entity.Evenement;
import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.stream.Collectors;
import  Util.DataSource;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Array;



public class ServiceEvenement implements IEvenement<Evenement> {
    private Connection connection;
    DataSource ds = DataSource.getInstance();


    public ServiceEvenement(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Evenement e) {

        if (e.getNom() == null || e.getDate() == null || e.getLieu() == null) {
            System.out.println("Erreur: nom, date ou lieu de l'événement manquant!");
            return;
        }
        if (e.getId_users() == null || e.getId_users().isEmpty()) {
            System.out.println("Erreur: aucun participant spécifié!");
            return;
        }
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO evenement (nom, date, lieu, id_users) VALUES (?, ?, ?, ?)");
            ps.setString(1, e.getNom());
            ps.setDate(2, new java.sql.Date(e.getDate().getTime()));
            ps.setString(3, e.getLieu());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < e.getId_users().size(); i++) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(e.getId_users().get(i));
            }
            ps.setString(4, sb.toString());
            ps.executeUpdate();
            System.out.println("L'événement a été inséré avec succès!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void delete(int e) {
        try {
            // Vérifier si l'id fourni est présent dans la table evenement
            String checkQuery = "SELECT id FROM evenement WHERE id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, e);
            ResultSet result = checkStatement.executeQuery();
            if (!result.next()) {
                System.err.println("L'événement n'existe pas dans la base de données.");
                return;
            }

            // Supprimer l'événement
            String deleteQuery = "DELETE FROM evenement WHERE id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, e);
            System.out.println(e);
            deleteStatement.executeUpdate();
            System.out.println("L'événement a été supprimé avec succès.");

        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'événement : " + ex.getMessage());
        }
    }


    @Override
    public void update(int i,Evenement evenement) {
        // Vérification des champs obligatoires
        if (evenement.getNom() == null || evenement.getNom().isEmpty()) {
            System.out.println("Le nom de l'événement est obligatoire");
            return;
        }

        if (evenement.getLieu() == null || evenement.getLieu().isEmpty()) {
            System.out.println("Le lieu de l'événement est obligatoire");
            return;
        }


        // Mise à jour de l'événement
        String query = "UPDATE evenement SET nom = ?, date = ?, lieu = ?, id_users = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, evenement.getNom());
            stmt.setDate(2, new java.sql.Date(evenement.getDate().getTime()));
            stmt.setString(3, evenement.getLieu());
            String participantsString = String.join(",", evenement.getId_users().stream()
                    .map(Object::toString)
                    .collect(Collectors.toList()));

            stmt.setInt(4,2);
            stmt.setInt(5,i);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public ObservableList<Evenement> readAll() {
        ObservableList<Evenement> evenements = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM evenement";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                Date date = resultSet.getDate("date");
                String lieu = resultSet.getString("lieu");
                String idUsersString = resultSet.getString("id_users");
                List<Integer> idUsers = new ArrayList<>();
                if (idUsersString != null) {
                    String[] idUsersArray = idUsersString.split(",");
                    for (String idUser : idUsersArray) {
                        idUsers.add(Integer.parseInt(idUser.trim()));
                    }
                }
                Evenement evenement = new Evenement(id, nom, date, lieu, idUsers);
                evenements.add(evenement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenements;
    }



    @Override
    public Evenement readById(int i) {
        String query = "SELECT * FROM evenement WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, i);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    Date date = rs.getDate("date");
                    String lieu = rs.getString("lieu");
                    List<Integer> participants = Arrays.stream(rs.getString("id_users").split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    return new Evenement(i, nom, date, lieu, participants);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
