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
import tikape.drinkkihaku.RaakaAine;

/**
 *
 * @author Santtu
 */
public class RaakaAineDAO implements DAO<RaakaAine, Integer> {

    Database database;
    
    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        RaakaAine object = new RaakaAine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return object;
    }

    @Override
    public List findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> lista = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            
            lista.add(new RaakaAine(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return lista;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    @Override
    public void saveOrUpdate(RaakaAine object) throws SQLException {
        if (object.getId() == null) {
            save(object);
        } else {
            update(object);
        }
    }
    
    @Override
    public void save(RaakaAine object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine"
                + " (nimi)"
                + " VALUES (?)");
        stmt.setString(1, object.getNimi());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    @Override
    public void update(RaakaAine object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE RaakaAine SET"
                + " nimi = ? WHERE id = ?");
        stmt.setString(1, object.getNimi());
        stmt.setInt(2, object.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
}
