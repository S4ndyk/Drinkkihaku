package tikape.domain;

import java.util.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.*;
import tikape.drinkkihaku.*;

public class Main {
    
    public static void main(String [] args) throws Exception {
        
        Database database = new Database("jdbc:sqlite:db/database.db");
        DrinkkiDAO Ddao = new DrinkkiDAO(database);
        RaakaAineDAO Rdao = new RaakaAineDAO(database);
        
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
        
        //arkisto sivu
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
        
        //drinkin lisäys sivu
        get("/drinkki", (req, res) -> {
            HashMap map = new HashMap();
            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());
        
        //raaka-aine sivu
        get("/raaka-aine", (req, res) -> {
            HashMap map = new HashMap();
            map.put("raakaAineet", Rdao.findAll());
            return new ModelAndView(map, "raaka-aine");
        }, new ThymeleafTemplateEngine());
        
        //lisää uuden raaka-aineen
        post("/raaka-aine", (req, res) -> {
            String nimi = req.queryParams("nimi");
            Rdao.saveOrUpdate(new RaakaAine(-1, nimi));
            res.redirect("/raaka-aine");
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
