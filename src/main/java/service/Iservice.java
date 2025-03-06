package service;

import java.sql.SQLException;
import java.util.List;


public interface Iservice <T>{
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(String Sujet) throws SQLException;
    public List<T> afficher() throws SQLException;

}
