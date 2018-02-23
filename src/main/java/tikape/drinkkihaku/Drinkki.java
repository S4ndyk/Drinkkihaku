package tikape.drinkkihaku;

import java.util.List;
import tikape.database.ArvosteluDAO;
import tikape.database.Database;

public class Drinkki implements Comparable<Drinkki> {
    
    private Integer id;
    private String nimi;
    private String ohje;
    
    public Drinkki(Integer id, String nimi, String ohje) {
        this.ohje = ohje;
        this.id = id;
        this.nimi = nimi;
    }
    
    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public String getOhje(){
        return ohje;
    }
    
    public List<Arvostelu> arvostelut() throws Exception {
        Database database = new Database("jdbc:sqlite:db/database.db");
        ArvosteluDAO dao = new ArvosteluDAO(database);
        return dao.drinkinArvostelut(id);
    }
    
    public Double arvostelu() throws Exception {
        if (arvostelut().isEmpty()) return -1.0;
        return arvostelut().stream().mapToInt(arvostelu -> arvostelu.getPisteet()).average().getAsDouble();
    }
    
    public int arvosteluja() throws Exception {
        return (int) arvostelut().stream().mapToInt(arvostelu -> arvostelu.getPisteet()).count();
    }
    
    public String getArvosteluRivi() throws Exception {
        Double arvostelu = arvostelu();
        int arvosteluja = arvosteluja();
        if (arvostelu == -1.0) return "ei arvosteluja";
        if (arvosteluja == 1) return arvostelu + "/100, " + arvosteluja + " arvostelu";
        return arvostelu.toString().substring(0,4) + "/100, " + arvosteluja + " arvostelua";
    }

    @Override
    public int compareTo(Drinkki drinkki) {
        return nimi.compareTo(drinkki.getNimi());
    }
    
}
