package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tikape.drinkkihaku.DrinkkiRaakaAine;
import tikape.drinkkihaku.RaakaAine;

public class DrinkkiRaakaAineDAO implements DAO<DrinkkiRaakaAine, Integer> {

    Database database;
    
    public DrinkkiRaakaAineDAO(Database database){
        this.database = database;
    }

    @Override
    public void saveOrUpdate(DrinkkiRaakaAine drinkkiRaakaAine) throws SQLException {
         try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO DrinkkiRaakaAine (drinkkiId, raakaAineId, ohje, maara, jarjestys) "
                        + "VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, drinkkiRaakaAine.getDrinkkiId());
            stmt.setInt(2, drinkkiRaakaAine.getRaakaAineId());
            stmt.setString(3, drinkkiRaakaAine.getOhje());
            stmt.setInt(4, drinkkiRaakaAine.getMaara());
            stmt.setInt(5, drinkkiRaakaAine.getJarjestys());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<DrinkkiRaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DrinkkiRaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void save(DrinkkiRaakaAine type) throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void update(DrinkkiRaakaAine type) throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    public Map<RaakaAine, Integer> ainesosat(Integer drinkkiId) throws SQLException {
        RaakaAineDAO rdao = new RaakaAineDAO(database);
        Map<RaakaAine, Integer> ainesosat = new HashMap<>();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT raakaAineId, maara FROM DrinkkiRaakaAine WHERE drinkkiId = ?");
        stmt.setInt(1, drinkkiId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ainesosat.put(rdao.findOne(rs.getInt("raakaAineId")), rs.getInt("maara"));
        }   rs.close();
        stmt.close();
        return ainesosat;
    }
    
}
