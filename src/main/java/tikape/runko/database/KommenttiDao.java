package tikape.runko.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Kommentti;
import tikape.runko.domain.Vieraskirja;

/**
 *
 * @author elinalassila
 */
public class KommenttiDao {

    private Database database;
    private VieraskirjaDao vieraskirjadao;

    public KommenttiDao(Database database, VieraskirjaDao vieraskirjadao) {
        this.database = database;
        this.vieraskirjadao = vieraskirjadao;
    }

//    public List<Kommentti> findAll() throws SQLException {
//
//        Connection connection = database.getConnection();
//        PreparedStatement stmt = connection.prepareStatement("SELECT Kommentti.sisalto, Kommentti.lahettaja, Kommentti.aika FROM Kommentti INNER JOIN Vieraskirja ON Kommentti.vieraskirja = Vieraskirja.id WHERE Vieraskirja.id = ?;");
//
//        ResultSet rs = stmt.executeQuery();
//        List<Kommentti> kommentit = new ArrayList<>();
//        while (rs.next()) {
//            String aika = rs.getString("aika");
//            String lahettaja = rs.getString("lahettaja");
//            String sisalto = rs.getString("sisalto");
//            Integer vieraskirja = rs.getInt("vieraskirja");
//            Vieraskirja v = vieraskirjadao.findOne(vieraskirja);
//            Kommentti k = new Kommentti(sisalto, lahettaja, aika, v);
//
//            kommentit.add(k);
//        }
//
//        rs.close();
//        stmt.close();
//        connection.close();
//
//        return kommentit;
//    }

    public List<Kommentti> findOne(Integer key, int sivu) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kommentti WHERE vieraskirja = ? ORDER BY aika DESC LIMIT 10 OFFSET " + ((sivu -1) * 10) + ";");
        stmt.setObject(1, key);
       

        ResultSet rs = stmt.executeQuery();
        List<Kommentti> kommentit = new ArrayList<>();
        while (rs.next()) {
            String aika = rs.getString("aika");
            String lahettaja = rs.getString("lahettaja");
            String sisalto = rs.getString("sisalto");
            Integer vieraskirja = rs.getInt("vieraskirja");
            Vieraskirja v = vieraskirjadao.findOne(vieraskirja);
            Kommentti k = new Kommentti(sisalto, lahettaja, aika, v);

            kommentit.add(k);
        }

        rs.close();
        stmt.close();
        connection.close();

        return kommentit;
    }

    public void uusiKommentti(String sisalto, String lahettaja, int vieraskirja) throws Exception {
        try (Connection connection = this.database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Kommentti(sisalto, lahettaja, aika, vieraskirja) VALUES (?,?, CURRENT_TIMESTAMP, ?);");
            stmt.setString(1, sisalto);
            stmt.setString(2, lahettaja);
            stmt.setInt(3, vieraskirja);
            stmt.execute();
            stmt.close();
        }
    }

}
