package Service;
import java.util.List;


public interface IEvenement<Evenement> {
    void insert(Evenement e);
    void delete(int e);
    void update( int i,Evenement e);
    List<Entity.Evenement> readAll();
    Evenement readById(int id);
}