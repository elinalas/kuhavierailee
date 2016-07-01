package tikape.runko.database;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;
    private boolean debug;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        init();
    }

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(databaseAddress);
    }

//    public void setDebugMode(boolean d) {
//        debug = d;
//    }
//    
    public void init() {

        List<String> lauseet = null;

        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();
        lista.add("DROP TABLE Kommentti;");
        lista.add("DROP TABLE Vieraskirja;");
        
        lista.add("CREATE TABLE Vieraskirja (\n"
                + "		id serial PRIMARY KEY,\n"
                + "		nimi varchar(100),\n"
                + "		kotisivu varchar(400));");
        lista.add("CREATE TABLE Kommentti (\n"
                + "		id serial PRIMARY KEY,\n"
                + "		sisalto varchar(5000),\n"
                + "		lahettaja varchar(50),\n"
                + "		aika timestamp,\n"
                + "		vieraskirja integer,\n"
                + "		FOREIGN KEY(vieraskirja) REFERENCES Vieraskirja(id));");
        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        //Miksi Modan vieraskirja on kaksi kertaa etusivulla, mutta ei ole tietokannassa kuin yhden kerran...
        //(Tietokantaan ei mene mtn mitä täällä tapahtuu?)
        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
//        lista.add("CREATE TABLE Vieraskirja (\n" +
//"		id integer PRIMARY KEY,\n" +
//"		nimi varchar(100),\n" +
//"		kotisivu varchar(400));");
//        lista.add("CREATE TABLE Kommentti (\n" +
//"		id integer PRIMARY KEY,\n" +
//"		sisalto varchar(5000),\n" +
//"		lahettaja varchar(50),\n" +
//"		aika timestamp,\n" +
//"		vieraskirja integer,\n" +
//"		FOREIGN KEY(vieraskirja) REFERENCES Vieraskirja(id));");
//        lista.add("INSERT INTO Vieraskirja (nimi,kotisivu) VALUES ('Modan vieraskirja','modalehti.fi');");
//        lista.add("INSERT INTO Kommentti (sisalto,lahettaja,aika,vieraskirja) VALUES ('Moi! Teijän lehti on ihan paras!','Modafani',01012016,1);");
//        lista.add("INSERT INTO Vieraskirja (nimi,kotisivu) VALUES ('Snif Tukkamuumien vieraskirja','sniftukkamuumit.fi');");
//        lista.add("INSERT INTO Kommentti (sisalto,lahettaja,aika,vieraskirja) VALUES ('Jee meil on oma vieraskirja!','Elina',01012016,2);");
//        lista.add("INSERT INTO Kommentti (sisalto,lahettaja,aika,vieraskirja) VALUES ('This is nice!','Sleksi',01012016,2);");
//        lista.add("INSERT INTO Vieraskirja (nimi,kotisivu) VALUES ('Karttulan vieraskirja','karttula.fi');");
//        lista.add("INSERT INTO Kommentti (sisalto,lahettaja,aika,vieraskirja) VALUES ('Kävimme kaverini kanssa Karttulassa luontoretkellä. Mikään ei ole niin hienoa kuin retki luonnossa kauniina, kesäisenä päivänä.','Simo',01062016,3);");
        return lista;
    }
}
