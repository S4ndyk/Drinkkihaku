package tikape.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tikape.drinkkihaku.Drinkki;

public class DrinkkiDAO implements DAO<Drinkki, Integer> {

    Database database;

    public DrinkkiDAO(Database database) {
        this.database = database;
    }

    @Override
    public Drinkki findOne(Integer key) throws SQLException {
        Drinkki object = new Drinkki(-1, "", "");
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki WHERE id = ?");
            stmt.setObject(1, key);
            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                return null;
            }
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            String ohje = rs.getString("ohje");
            object = new Drinkki(id, nimi, ohje);
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return object;
    }

    @Override
    public List<Drinkki> findAll() throws SQLException {
        List<Drinkki> lista = new ArrayList<>();
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nimi = rs.getString("nimi");
                String ohje = rs.getString("ohje");
                Drinkki object = new Drinkki(id, nimi, ohje);

                lista.add(object);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Collections.sort(lista);

        return lista;
    }

    public List<Drinkki> findByName(String haettava) throws SQLException {
        List<Drinkki> kaikki = findAll();
        List<Drinkki> tulos = new ArrayList<>();
        for (Drinkki drinkki : kaikki) {
            if (drinkki.getNimi().toLowerCase().contains(haettava.toLowerCase())) {
                tulos.add(drinkki);
            }
            if (haettava.toLowerCase().contains(drinkki.getNimi().toLowerCase())) {
                tulos.add(drinkki);
            }
        }
        return tulos;
    }

    public Drinkki findOneByName(String haettava) throws SQLException {
        Integer id = -1;
        String nimi = "";
        String ohje = "";
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Drinkki WHERE nimi = ?");
            stmt.setString(1, haettava);
            ResultSet rs = stmt.executeQuery();
            boolean hasOne = rs.next();
            if (!hasOne) {
                rs.close();
                stmt.close();
                conn.close();
                
                return null;
            }   id = rs.getInt("id");
            nimi = rs.getString("nimi");
            ohje = rs.getString("ohje");
            rs.close();
            stmt.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return new Drinkki(id, nimi, ohje);
    }

    @Override
    public void delete(Integer key) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement poistaDrinkki = conn.prepareStatement("DELETE FROM Drinkki WHERE id = ?");
            
            poistaDrinkki.setInt(1, key);
            poistaDrinkki.executeUpdate();
            poistaDrinkki.close();
            
            PreparedStatement poistaDrinkkiRaakaAine = conn.prepareStatement("DELETE FROM DrinkkiRaakaAine WHERE drinkkiId = ?");
            poistaDrinkkiRaakaAine.setInt(1, key);
            poistaDrinkkiRaakaAine.executeUpdate();
            poistaDrinkkiRaakaAine.close();
            
            PreparedStatement poistaArvostelu = conn.prepareStatement("DELETE FROM Arvostelu WHERE drinkkiId = ?");
            poistaArvostelu.setInt(1, key);
            poistaArvostelu.executeUpdate();
            poistaArvostelu.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
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
                    + " (nimi, ohje)"
                    + " VALUES (?, ?)");
            stmt.setString(1, object.getNimi());
            stmt.setString(2, object.getOhje());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Drinkki object) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Drinkki SET"
                    + " nimi = ? WHERE id = ?");
            stmt.setString(1, object.getNimi());
            stmt.setInt(2, object.getId());
            
            stmt.executeUpdate();
            
            stmt.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
