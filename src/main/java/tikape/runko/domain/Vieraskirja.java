package tikape.runko.domain;

public class Vieraskirja {

    private Integer id;
    private String nimi;
    private String kotisivu;
    private String viimeisinviesti;
    private int viestienmaara;

    public Vieraskirja(Integer id, String nimi, String kotisivu) {
        this.id = id;
        this.nimi = nimi;
        this.kotisivu = kotisivu;
    }

    public Vieraskirja(Integer id, String nimi, String kotisivu, String viimeisinviesti, int viestienmaara) {
        this.id = id;
        this.nimi = nimi;
        this.kotisivu = kotisivu;
        this.viimeisinviesti = viimeisinviesti;
        this.viestienmaara = viestienmaara;
    }
    
    
    
    

    public Integer getId() {
        return id;
    }


    public String getNimi() {
        return nimi;
    }

    public String getKotisivu() {
        return kotisivu;
    }

    public String getViimeisinviesti() {
        return viimeisinviesti;
    }

    public int getViestienmaara() {
        return viestienmaara;
    }
    
    

    
  

}
