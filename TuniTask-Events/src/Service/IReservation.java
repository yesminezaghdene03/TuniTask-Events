package Service;

import java.util.List;
import Entity.Reservation;
import javafx.collections.ObservableList;

public interface IReservation<T> {
    void insert(T e);
    void delete(T e);
    void update(T e);
    ObservableList<T> readAll();
    T readById(int id);
    ObservableList<T> readByUserId(int id);

    List<Entity.Evenement> readReservEvent(int iduser);
}


