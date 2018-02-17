/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.drinkkihaku;

/**
 *
 * @author Santtu
 */
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
