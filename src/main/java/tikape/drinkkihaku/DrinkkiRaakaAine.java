package tikape.drinkkihaku;

/**
 *
 * @author Santtu
 */
public class DrinkkiRaakaAine {
    private Integer drinkkiId;
    private Integer raakaAineId;
    private String ohje;
    private Integer maara;
    private Integer jarjestys;
    
    public DrinkkiRaakaAine(Integer drinkkiId, Integer raakaAineId, String ohje, Integer maara, Integer jarjestys) {
        this.drinkkiId = drinkkiId;
        this.raakaAineId = raakaAineId;
        this.ohje = ohje;
        this.maara = maara;
        this.jarjestys = jarjestys;
    }

    public Integer getDrinkkiId() {
        return drinkkiId;
    }

    public Integer getRaakaAineId() {
        return raakaAineId;
    }

    public String getOhje() {
        return ohje;
    }

    public Integer getMaara() {
        return maara;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }
}
