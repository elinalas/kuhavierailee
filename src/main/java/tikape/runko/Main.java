package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KommenttiDao;
import tikape.runko.database.VieraskirjaDao;

public class Main {

    public static void main(String[] args) throws Exception {
        
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:vieraskirja.db");
        database.init();

        VieraskirjaDao vieraskirjaDao = new VieraskirjaDao(database);
        KommenttiDao kommenttidao = new KommenttiDao(database, vieraskirjaDao);

        

        get("/", (req, res) -> {
            HashMap map = new HashMap();
            map.put("vieraskirjat", vieraskirjaDao.findAll());

            return new ModelAndView(map, "vieraskirjat");
        }, new ThymeleafTemplateEngine());
        
        post("/", (req, res) -> {
            String nimi = req.queryParams("nimi");
            String kotisivu = req.queryParams("kotisivu");
            if (nimi.length() > 100 || kotisivu.length() > 400) {
                System.out.println("Nimen max. pituus on 100 ja kotisivun max. pituus on 400.");
            } else if (nimi.isEmpty() || kotisivu.isEmpty()) {
                System.out.println("Nimi ja/tai kotisivu eivät voi olla tyhjiä!");
            }
            vieraskirjaDao.uusiVieraskirja(nimi, kotisivu);
            res.redirect("/");
            return "";
        });
         post("/vieraskirjat/:id", (req, res) -> {
            String sisalto = req.queryParams("sisalto");
            String nimimerkki = req.queryParams("nimimerkki");
     
            int vieraskirja = Integer.parseInt(req.params("id"));
            kommenttidao.uusiKommentti(sisalto, nimimerkki, vieraskirja);
            res.redirect("/vieraskirjat/" + vieraskirja);
            return "";
        });
         
         

        get("/vieraskirjat/:id", (req, res) -> {
            HashMap map = new HashMap(); 
            map.put("kommentit", kommenttidao.findOne(Integer.parseInt(req.params("id")), Integer.parseInt(req.queryParams("sivu"))));
            map.put("vieraskirja", vieraskirjaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "kommentti");
        }, new ThymeleafTemplateEngine());
        
    

       
    }
   
}
