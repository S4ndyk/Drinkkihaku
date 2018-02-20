package tikape.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import tikape.drinkkihaku.RaakaAine;

public class RaakaAineDAO implements DAO<RaakaAine, Integer> {

    Database database;
    
    public RaakaAineDAO(Database database){
        this.database = database;
    }
    
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
        Double alkoholiprosentti = rs.getDouble("alkoholiprosentti");

        RaakaAine object = new RaakaAine(id, nimi, alkoholiprosentti);

        rs.close();
        stmt.close();
        connection.close();

        return object;
    }

    @Override
    public List findAll() throws SQLException {
        List<RaakaAine> lista;
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine");
            ResultSet rs = stmt.executeQuery();
            lista = new ArrayList<>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nimi = rs.getString("nimi");
                Double alkoholiprosentti = rs.getDouble("alkoholiprosentti");
                
                lista.add(new RaakaAine(id, nimi, alkoholiprosentti));
            }   rs.close();
            stmt.close();
        }

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
        if (findOne(object.getId()) == null) {
            save(object);
        } else {
            update(object);
        }
    }
    
    @Override
    public void save(RaakaAine object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine (nimi, alkoholiprosentti) VALUES (?, ?)");
        stmt.setString(1, object.getNimi());
        stmt.setDouble(2, object.getAlkoholiprosentti());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    @Override
    public void update(RaakaAine object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE RaakaAine SET"
                + " nimi = ?, alkoholiprosentti = ? WHERE id = ?");
        stmt.setString(1, object.getNimi());
        stmt.setDouble(2, object.getAlkoholiprosentti());
        stmt.setInt(3, object.getId());

        stmt.executeUpdate();

        stmt.close();
        conn.close();
    } 
}