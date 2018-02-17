/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.drinkkihaku.Drinkki;

/**
 *
 * @author Santtu
 */
public class DrinkkiDAO implements DAO<Drinkki, Integer>{

    Database database;
    
    public DrinkkiDAO(Database database) {
        this.database = database;
    }
    
    @Override
    public Drinkki findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Drinkki object = new Drinkki(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return object;
    }

    @Override
    public List findAll() throws SQLException {
        List<Drinkki> lista;
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki");
            ResultSet rs = stmt.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nimi = rs.getString("nimi");
                Drinkki object = new Drinkki(id, nimi);
                
                lista.add(object);
            }   rs.close();
            stmt.close();
        }

        return lista;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Drinkki WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    @Override
    public void saveOrUpdate(Drinkki object) throws SQLException {
        if (findOne(object.getId()) == null) {
            save(object);
        } else {
            update(object);
        }
    }
    
    @Override
    public void save(Drinkki object) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Drinkki"
                    + " (nimi)"
                    + " VALUES (?)");
            stmt.setString(1, object.getNimi());
            
            stmt.executeUpdate();
            stmt.close();
        }
    }
    
    @Override
    public void update(Drinkki object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE Drinkki SET"
                + " nimi = ? WHERE id = ?");
        stmt.setString(1, object.getNimi());
        stmt.setInt(2, object.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }   
}