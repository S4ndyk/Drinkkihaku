package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.drinkkihaku.Arvostelu;

public class ArvosteluDAO implements DAO<Arvostelu, Integer> {
    Database database;
    
    public ArvosteluDAO(Database database) {
        this.database = database;
    }
    
    @Override
    public Arvostelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Arvostelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer drinkkiId = rs.getInt("drinkkiId");
        String teksti = rs.getString("teksti");
        Integer pisteet = rs.getInt("pisteet");

        Arvostelu object = new Arvostelu(id, drinkkiId,teksti, pisteet);

        rs.close();
        stmt.close();
        connection.close();

        return object;
    }

    @Override
    public List findAll() throws SQLException {
        List<Arvostelu> lista;
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Arvostelu");
            ResultSet rs = stmt.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer drinkkiId = rs.getInt("drinkkiId");
                String teksti = rs.getString("teksti");
                Integer pisteet = rs.getInt("pisteet");
                Arvostelu object = new Arvostelu(id, drinkkiId,teksti, pisteet);
                
                lista.add(object);
            }   rs.close();
            stmt.close();
        }

        return lista;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Arvostelu WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    @Override
    public void saveOrUpdate(Arvostelu object) throws SQLException {
        if (findOne(object.getId()) == null) {
            save(object);
        } else {
            update(object);
        }
    }
    
    @Override
    public void save(Arvostelu object) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Arvostelu"
                    + " (drinkkiId, teksti, pisteet)"
                    + " VALUES (?, ?, ?)");
            
            stmt.setInt(1, object.getDrinkkiId());
            stmt.setString(2, object.getTeksti());
            stmt.setInt(3, object.getPisteet());
            
            stmt.executeUpdate();
            stmt.close();
        }
    }
    
    @Override
    public void update(Arvostelu object) throws SQLException {
        
    }   
}
    

