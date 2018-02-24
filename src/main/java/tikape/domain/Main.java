package tikape.domain;

import java.util.*;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.database.*;
import tikape.drinkkihaku.*;

public class Main {

    public static void main(String[] args) throws Exception {

        Database database = new Database("jdbc:sqlite:db/database.db");
        staticFiles.location("/public");
        DrinkkiDAO Ddao = new DrinkkiDAO(database);
        RaakaAineDAO Rdao = new RaakaAineDAO(database);
        ArvosteluDAO Adao = new ArvosteluDAO(database);
        DrinkkiRaakaAineDAO DRdao = new DrinkkiRaakaAineDAO(database);

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

        //drinkin omat sivut 
        post("/drinkki/:id", (req, res) -> {
            HashMap map = new HashMap();
            Integer id = Integer.parseInt(req.params(":id"));
            Drinkki drinkki = Ddao.findOne(id);
            map.put("drinkki", drinkki);
            return new ModelAndView(map, "/drinkki");
        }, new ThymeleafTemplateEngine());

        //poistamistoiminto
        post("/poista", (req, res) -> {
            Ddao.delete(Integer.parseInt(req.queryParams("poista")));
            res.redirect("/arkisto");
            return "";
        });

        //raaka-aine sivu
        get("/raaka-aine", (req, res) -> {
            HashMap map = new HashMap();
            map.put("raakaAineet", Rdao.findAll());
            return new ModelAndView(map, "raaka-aine");
        }, new ThymeleafTemplateEngine());

        //lisää uuden raaka-aineen
        post("/raaka-aine", (req, res) -> {
            String nimi = req.queryParams("nimi");
            Double alkoholiprosentti = Double.parseDouble(req.queryParams("alkoholiprosentti"));
            Rdao.saveOrUpdate(new RaakaAine(-1, nimi, alkoholiprosentti));
            res.redirect("/raaka-aine");
            return "";
        });

        //raaka-aineen poistamistoiminto 
        post("/raaka-aine/poista", (req, res) -> {
            Rdao.delete(Integer.parseInt(req.queryParams("poista")));
            res.redirect("/raaka-aine");
            return "";
        });

        //arvostelu sivu
        post("/uusiarvostelu/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.queryParams("arvostelu"));
            Drinkki drinkki = Ddao.findOne(id);

            HashMap map = new HashMap();
            map.put("drinkki", drinkki);

            return new ModelAndView(map, "/arvostelu");
        }, new ThymeleafTemplateEngine());

        //uuden arvostelun lisäys
        post("/arvostelu", (req, res) -> {
            String teksti = req.queryParams("teksti");
            Integer pisteet = Integer.parseInt(req.queryParams("pisteet"));
            Integer DrinkkiId = Integer.parseInt(req.queryParams("id"));
            Adao.saveOrUpdate(new Arvostelu(-1, DrinkkiId, teksti, pisteet));
            res.redirect("/arkisto");
            return "";
        });

        //uusidrinkki
        get("/uusidrinkki", (req, res) -> {
            HashMap map = new HashMap();
            map.put("raakaAineet", Rdao.findAll());
            return new ModelAndView(map, "uusidrinkki");
        }, new ThymeleafTemplateEngine());

        post("/uusidrinkkiviimeistely", (req, res) -> {
            HashMap map = new HashMap();
            List<String> merkkijonoLista = Arrays.asList(req.queryParamsValues("id"));
            ArrayList<RaakaAine> raakaAineLista = new ArrayList<>();
            for (int i = 0; i < merkkijonoLista.size(); i++) {
                RaakaAine r = Rdao.findOne(Integer.parseInt(merkkijonoLista.get(i)));
                raakaAineLista.add(r);
            }
            String nimi = req.queryParams("nimi");
            String ohje = req.queryParams("ohje");
            map.put("raakaAineet", raakaAineLista);
            map.put("nimi", nimi);
            map.put("ohje", ohje);
            return new ModelAndView(map, "/uusidrinkkiviimeistely");
        }, new ThymeleafTemplateEngine());

        post("/uusidrinkki", (req, res) -> {
            String nimi = req.queryParams("nimi");
            String ohje = req.queryParams("ohje");
            Drinkki d = new Drinkki(-1, nimi, ohje);
            Ddao.saveOrUpdate(d);
            
            List<String> merkkijonoLista = Arrays.asList(req.queryParamsValues("raakaAineet"));
            ArrayList<RaakaAine> raakaAineLista = new ArrayList<>();
            for (int i = 0; i < merkkijonoLista.size(); i++) {
                RaakaAine r = Rdao.findOne(Integer.parseInt(merkkijonoLista.get(i)));
                raakaAineLista.add(r);
            }
            
            for(RaakaAine r : raakaAineLista){
                Integer drinkkiId = Ddao.findOneByName(nimi).getId();
                Integer raakaAineId = r.getId();
                Integer maara = Integer.parseInt(req.queryParams(r.getId().toString()));
                DRdao.saveOrUpdate(new DrinkkiRaakaAine(drinkkiId, raakaAineId, maara));
            }
            
            res.redirect("/arkisto");
            return "";
        });

        post("/haku", (req, res) -> {
            String nimi = req.queryParams("nimi");
            HashMap map = new HashMap();
            map.put("drinkit", Ddao.findByName(nimi));
            return new ModelAndView(map, "/haku");
        }, new ThymeleafTemplateEngine());
    }
}
