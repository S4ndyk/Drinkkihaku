package tikape.drinkkihaku;

public class Arvostelu {
    private Integer id;
    private String teksti;
    private Integer pisteet;
    
    public Arvostelu(Integer id, String teksti, Integer pisteet){
        this.id = id;
        this.teksti = teksti;
        
        //annetaan rajat pisteytykselle 
        if(pisteet > 100  || pisteet < 1){
            throw new RuntimeException("Muuttuja pisteet on liian suuri tai pieni");
        }
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
}
