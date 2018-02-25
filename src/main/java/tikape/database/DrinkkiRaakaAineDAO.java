package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tikape.drinkkihaku.Drinkki;
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
                "INSERT INTO DrinkkiRaakaAine (drinkkiId, raakaAineId, maara) "
                        + "VALUES (?, ?, ?)");
            stmt.setInt(1, drinkkiRaakaAine.getDrinkkiId());
            stmt.setInt(2, drinkkiRaakaAine.getRaakaAineId());
            stmt.setInt(3, drinkkiRaakaAine.getMaara());
            
            stmt.executeUpdate();
            stmt.close();
            conn.close();
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

    public List<RaakaAine> getRaakaAineet(Integer key) throws SQLException{
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT DISTINCT raakaAine.nimi, raakaAine.id, raakaAine.alkoholiprosentti FROM RaakaAine INNER JOIN DrinkkiRaakaAine ON RaakaAine.id = DrinkkiRaakaAine.raakaAineId"
        + " INNER JOIN Drinkki ON DrinkkiRaakaAine.drinkkiId = ?;" );
        stmt.setInt(1, key);
        ResultSet rs = stmt.executeQuery();
        ArrayList<RaakaAine> lista = new ArrayList<>();
        while(rs.next()){
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            Double alkoholiProsentti = rs.getDouble("alkoholiprosentti");
            RaakaAine r = new RaakaAine(id, nimi, alkoholiProsentti);
            lista.add(r);
        }
        rs.close();
        stmt.close();
        conn.close();
        return lista;
    }
    
    public Map<Integer, Integer> maarat(Integer drinkkiId)throws SQLException{
        RaakaAineDAO rdao = new RaakaAineDAO(database);
        Map<Integer, Integer> map = new HashMap<>();
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT raakaAineId, maara FROM DrinkkiRaakaAine WHERE drinkkiId = ?");
        stmt.setInt(1, drinkkiId);
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            Integer id = rs.getInt("raakaAineId");
            Integer maara = rs.getInt("maara");
            map.put(id, maara);
        }
        
        rs.close();
        stmt.close();
        conn.close();
       
        return map;
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
