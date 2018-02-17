package tikape.drinkkihaku;

public class Drinkki {
    
    private Integer id;
    private String nimi;
    
    public Drinkki(Integer id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }
    
    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }    
}
