package tikape.drinkkihaku;

public class DrinkkiRaakaAine {
    private Integer drinkkiId;
    private Integer raakaAineId;
    private Integer maara;
    
    public DrinkkiRaakaAine(Integer drinkkiId, Integer raakaAineId, Integer maara) {
        this.drinkkiId = drinkkiId;
        this.raakaAineId = raakaAineId;
        this.maara = maara;
    }

    public Integer getDrinkkiId() {
        return drinkkiId;
    }

    public Integer getRaakaAineId() {
        return raakaAineId;
    }

    public Integer getMaara() {
        return maara;
    }
}
