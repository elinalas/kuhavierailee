/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Vieraskirja;

public class VieraskirjaDao implements Dao<Vieraskirja, Integer> {

    private Database database;

    public VieraskirjaDao(Database database) {
        this.database = database;
    }

    @Override
    public Vieraskirja findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vieraskirja WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        String kotisivu = rs.getString("kotisivu");

        Vieraskirja o = new Vieraskirja(id, nimi, kotisivu);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    @Override
    public List<Vieraskirja> findAll() throws SQLException {

        Connection connection = database.getConnection();
      
        PreparedStatement stmt = connection.prepareStatement("SELECT vieraskirja.id, vieraskirja.nimi, vieraskirja.kotisivu, COUNT(Kommentti.id) AS maara, MAX(Kommentti.aika) AS viimeisin  FROM Vieraskirja LEFT  JOIN Kommentti ON Vieraskirja.id = Kommentti.vieraskirja GROUP BY vieraskirja.id, vieraskirja.nimi LIMIT 10;");
        ResultSet rs = stmt.executeQuery();
        List<Vieraskirja> vieraskirjat = new ArrayList();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            String kotisivu = rs.getString("kotisivu");
            String viimeisin = rs.getString("viimeisin");
            Integer montako = rs.getInt("maara");
         

            vieraskirjat.add(new Vieraskirja(id, nimi, kotisivu, viimeisin, montako));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vieraskirjat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
    
    public void uusiVieraskirja (String nimi, String kotisivu) throws Exception {
        try (Connection connection = this.database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Vieraskirja(nimi, kotisivu) VALUES (?,?);");
            stmt.setString(1, nimi);
            stmt.setString(2, kotisivu);
            stmt.execute();
            stmt.close();
        }
    }

}
