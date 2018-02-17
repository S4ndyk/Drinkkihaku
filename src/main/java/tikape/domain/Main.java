/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.domain;

import java.util.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.*;
import tikape.drinkkihaku.Drinkki;

/**
 *
 * @author Santtu
 */
public class Main {
    
    public static void main(String [] args) throws Exception {
        
        Database database = new Database("jdbc:sqlite:db/database.db");
        DrinkkiDAO Ddao = new DrinkkiDAO(database);
        
        //etusivu
        get("/index", (req, res) -> {
            HashMap map = new HashMap();
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        //ohjaa index sivulle 
        get("/", (req, res) -> {
            res.redirect("index");
            return "";
        });
        
        get("/arkisto", (req, res) -> {
            HashMap map = new HashMap();
            map.put("drinkit", Ddao.findAll());
            return new ModelAndView(map, "arkisto");
        }, new ThymeleafTemplateEngine());
        
        
        //poistamistoiminto (toimivuus riippuu java-jumalien tahdosta)
        post("/poista", (req, res) -> {
            Ddao.delete(Integer.parseInt(req.queryParams("poista")));
            res.redirect("/arkisto");
            return "";        
        });
        
        /*kun käyttäjä valitsee arvosteltavan drinkin !!ei toiminnassa!!
          html tiedostoa muokattava. yritin mutta thymeleaf heittää errorin
        */
        post("/uusiarvostelu/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.queryParams("arvostelu"));
            HashMap map = new HashMap();
            map.put("id", id);
            return new ModelAndView(map, "/arvostelu");
        });
        
        //hakutoiminto. !!ei toteutettu!!
        post("/haku", (req, res) -> {
            String nimi = req.queryParams("nimi");
            List<Drinkki> haku = Ddao.findAll();
            res.redirect("index");
            return "/";
        });
    }
}
