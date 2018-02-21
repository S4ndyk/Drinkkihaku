package tikape.drinkkihaku;

public class RaakaAine {

    private Integer id;
    private String nimi;
    private Double alkoholiprosentti;
    
    public RaakaAine(Integer id, String nimi, Double alkoholiprosentti) {
        this.id = id;
        this.nimi = nimi;
        this.alkoholiprosentti = alkoholiprosentti;
    }
    
    public Integer getId() {
        return id;
    }

    public String getNimi() {
        return nimi;
    }

    public Double getAlkoholiprosentti() {
        return alkoholiprosentti;
    }
    
    
}