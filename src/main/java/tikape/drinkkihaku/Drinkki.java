package tikape.drinkkihaku;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import tikape.database.ArvosteluDAO;
import tikape.database.Database;
import tikape.database.DrinkkiRaakaAineDAO;

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
        Database database = new Database();
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
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(arvostelu) + "/100, " + arvosteluja + " arvostelua";
    }
       
    @Override
    public int compareTo(Drinkki drinkki) {
        return nimi.compareTo(drinkki.getNimi());
    }
    
    public Map<RaakaAine, Integer> getAinesosat() throws Exception {
        Database database = new Database();
        DrinkkiRaakaAineDAO dao = new DrinkkiRaakaAineDAO(database);
        return dao.ainesosat(id);
    }
    
    public Integer tilavuus() throws Exception {
        Map<RaakaAine, Integer> ainesosat = getAinesosat();
        Integer maara = 0;
        for (RaakaAine raakaAine : ainesosat.keySet()) {
            maara += ainesosat.get(raakaAine);
        }
        return maara;
    }
    
    public String getTilavuusRivi() throws Exception {
        return tilavuus() + " ml";
    }
    
    public Double alkoholiprosentti() throws Exception {
        Map<RaakaAine, Integer> ainesosat = getAinesosat();
        Double alkoholia = 0.0;
        for (RaakaAine raakaAine : ainesosat.keySet()) {
            alkoholia += raakaAine.getAlkoholiprosentti() * ainesosat.get(raakaAine);
        }
        Integer tilavuus = tilavuus();
        if (tilavuus == 0) return 0.0;
        return alkoholia / tilavuus;
    }
    
    public String getAlkoholiprosenttiRivi() throws Exception {
        if (tilavuus() == 0) return "Drinkille ei löydy raaka-aineita!";
        Double alkoholiprosentti = alkoholiprosentti();
        if (alkoholiprosentti == 0.0) return "alkoholiton";
         DecimalFormat df = new DecimalFormat("#.#");
        return df.format(alkoholiprosentti) + " % vol.";
    }
    
}
