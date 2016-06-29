package tikape.runko.domain;

import java.sql.Date;

/**
 *
 * @author elinalassila
 */
public class Kommentti {
    
    private String sisalto;
    private String lahettaja;
    private String aika;
    private Vieraskirja vieraskirja;

    public Kommentti(String sisalto, String lahettaja, String aika, Vieraskirja vieraskirja) {
        this.sisalto = sisalto;
        this.lahettaja = lahettaja;
        this.aika = aika;
        this.vieraskirja = vieraskirja;
    }

    public String getAika() {
        return aika;
    }

    public String getSisalto() {
        return sisalto;
    }

    public String getLahettaja() {
        return lahettaja;
    }

    public Vieraskirja getVieraskirja() {
        return vieraskirja;
    }

    public void setVieraskirja(Vieraskirja vieraskirja) {
        this.vieraskirja = vieraskirja;
    }
    
    
    
    
    
    
}
