package tikape.drinkkihaku;

public class Arvostelu {
    private Integer id;
    private Integer DrinkkiId;
    private String teksti;
    private Integer pisteet;
    
    public Arvostelu(Integer id, Integer DrinkkiId ,String teksti, Integer pisteet){
        this.id = id;
        this.teksti = teksti;
        this.DrinkkiId = DrinkkiId;
        this.pisteet = pisteet;
    }

    public Integer getId() {
        return id;
    }

    public String getTeksti() {
        return teksti;
    }
    
    public Integer getPisteet() {
        return pisteet;
    }

    public Integer getDrinkkiId() {
        return DrinkkiId;
    }
}
