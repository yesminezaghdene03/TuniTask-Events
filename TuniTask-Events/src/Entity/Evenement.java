package Entity;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Evenement {

    private int id;
    private String nom;
    private Date date;
    private String lieu;
    private List<Integer> id_users;

    // Constructeur par défaut
    public Evenement() {
    }

    // Constructeur paramétré
    public Evenement(int id, String nom, Date date, String lieu, List<Integer> id_users) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.id_users = id_users;
    }
    public Evenement(String nom, Date date, String lieu, List<Integer> id_users) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.id_users = id_users;
    }
    public Evenement(String nom, LocalDate date, String lieu) {
        this.nom = nom;
        this.date = java.sql.Date.valueOf(date);
        this.lieu = lieu;
    }
    public Evenement(String nom, Date date, String lieu) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
    }



    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Date getDate() {
        return date;
    }

    public String getLieu() {
        return lieu;
    }

    public List<Integer> getId_users() {
        return id_users;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setId_users(List<Integer> id_users) {
        this.id_users = id_users;
    }

    // Méthode toString pour afficher les informations de l'événement
    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", lieu='" + lieu + '\'' +
                ", id_users=" + id_users +
                '}';
    }
}
