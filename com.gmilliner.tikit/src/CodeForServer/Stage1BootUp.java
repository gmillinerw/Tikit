package CodeForServer;

import SQliteConnection.SQliteCRUD;
import SharedUtilities.Utilities;

import java.io.File;
import java.util.*;

class Stage1BootUp extends absGUI {

    private Configuration configuration;

    Stage1BootUp() {
        configuration = new Configuration();
    }

    void startProgram() {
        deleteDB();
        Stage2login DSP1_login = new Stage2login();
        CR8ConfigurationTable();
        setConfiguration();
        this.configuration = cacheConfiguration();
        CR8UserTable();
        CR8SeatsTable();
        CR8ReservationsTable();
        DSP1_login.getMainDisplay();
        CR8Users();
        CR8MovieTable();
        CR8Movies();
        cr8Seats(.9F, .3F, cacheConfiguration());
    }

    private void deleteDB() {
        File file = new File("com.gmilliner.tikit/DataBase/TikitSQlite.db");

        if (file.delete()) {
            System.out.println("Database was deleted successfully");
        } else {
            System.out.println("Database was note deleted");
        }
    }

    private void CR8ConfigurationTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE IF NOT EXISTS Configuration\n\t\t"
                + "(property VARCHAR(15) NOT NULL,\n\t\t"
                + "setting INTEGER NOT NULL)";
        System.out.println((SQliteCRUD.ExeDML(query) == 0) ? "User table was created" : "[Error] USERS Table was not Created");
    }

    private void setConfiguration() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String[] property = {"numMovies", "numTimes", "numSeats", "price", "Property5", "Property6"};
        int[] setting = {6, 5, (6 * 7 * 2), 6000, 0, 0};
        for (int index = 0; index < property.length; index++) {
            String query = "INSERT INTO Configuration (Property, setting)\n\t\t"
                    + "VALUES('" + property[index] + "'," + setting[index] + ")";
            System.out.println((ExeDML(query) == 0) ? "User was created" : "[Error] USER was not Created");
        }
    }

    private void CR8UserTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE IF NOT EXISTS \"USERS\"\n" +
                "(\n" +
                "  \"userID\" INTEGER                                not null primary key AUTOINCREMENT,\n" +
                "  FirstName VARCHAR(15) DEFAULT 'First Name'       NOT NULL,\n" +
                "  LastName  VARCHAR(15) DEFAULT 'Last Name'        NOT NULL,\n" +
                "  EMail     VARCHAR(25) DEFAULT 'some@one.Com'     NOT NULL UNIQUE,\n" +
                "  username  VARCHAR(25) DEFAULT 'some@one.Com'     NOT NULL,\n" +
                "  Password  VARCHAR(10) DEFAULT 'secret'           NOT NULL,\n" +
                "  BankCard  VARCHAR(25) DEFAULT '4444333322221111' NOT NULL,\n" +
                "  isLogin   BOOLEAN     DEFAULT false              NOT NULL,\n" +
                "  isAdmin   BOOLEAN     DEFAULT true               NOT NULL,\n" +
                "  Token     INTEGER\n" +
                ");";

        System.out.println((ExeDML(query) == 0) ? "User table was created" : "[Error] USERS Table was not Created");
    }

    private void CR8Users() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "INSERT INTO Users (FirstName, LastName, EMail,username, Password, BankCard, isLogin,isAdmin)\n\t\t"
                + "VALUES('Guilder','Milliner','guilderw@gmail.com','guilderw@gmail.com','123','444333222111',false,true)";
        System.out.println((ExeDML(query) == 0) ? "User was created" : "[Error] USER was not Created");

        query = "INSERT INTO Users (FirstName, LastName, EMail,username, Password, BankCard, isLogin, isAdmin)\n\t\t"
                + "VALUES ('Glennmar','Milliner','glennmarm@gmail.com','glennmarm@gmail.com','123','444333222111',false,true)";
        System.out.println((ExeDML(query) == 0) ? "User was created" : "[Error] USER was not Created");
    }

    private void CR8SeatsTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE IF NOT EXISTS SEATS\n" +
                "(\n" +
                "  seatID   INTEGER               not null primary key AUTOINCREMENT,\n" +
                "  movieID  INTEGER               NOT NULL,\n" +
                "  Time    INTEGER               NOT NULL,\n" +
                "  seatNum  INTEGER               not null,\n" +
                "  isBooked BOOLEAN DEFAULT false NOT NULL,\n" +
                "  BookedBy VARCHAR(25)\n" +
                ");";
        System.out.println((ExeDML(query) == 0) ? "SEATS table was created" : "[Error] SEATS Table was not Created");
    }

    private void CR8ReservationsTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE IF NOT EXISTS Reservations\n" +
                "(\n" +
                "  ReservationID INTEGER     not null primary key AUTOINCREMENT,\n" +
                "  seatID        INTEGER     not null,\n" +
                "  seatNum       INTEGER     not null,\n" +
                "  movieID       INTEGER     NOT NULL,\n" +
                "  Time          INTEGER     NOT NULL,\n" +
                "  BookedBy      VARCHAR(25) NOT NULL\n" +
                ");";
        System.out.println((ExeDML(query) == 0) ? "SEATS table was created" : "[Error] SEATS Table was not Created");
    }

    private void CR8MovieTable() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String query;
        query = "CREATE TABLE IF NOT EXISTS Movies\n" +
                "(\n" +
                "  MovieID           INTEGER                            not null primary key AUTOINCREMENT,\n" +
                "  MovieName         VARCHAR(20) DEFAULT 'Movie Name'   NOT NULL,\n" +
                "  iconImageLocation VARCHAR(75) DEFAULT 'Relative URL' NOT NULL\n" +
                ");";
        System.out.println((ExeDML(query) == 0) ? "Movie table was created" : "[Error] Movie Table was not Created");
    }

    private void CR8Movies() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        String[] movieName = {"BABY DRIVER", "INTERSTELLAR", "LOGAN", "THE DARK KNIGHT", "THE GODFATHER", "WONDER WOMAN"};
        String[] iconImageLocation = {"/Media/BABY DRIVER.png", "/Media/INTERSTELLAR.png", "/Media/LOGAN.png", "/Media/THE DARK KNIGHT.png", "/Media/THE GODFATHER", "/Media/WONDER WOMAN.png"};
        String query;
        for (int index = 0; index < movieName.length; index++) {
            query = "INSERT INTO movies (MovieName, iconImageLocation)\n\t\t"
                    + "VALUES ('" + movieName[index] + "','\"" + iconImageLocation[index] + "\"')";
            System.out.println((ExeDML(query) == 0) ? "Movie table was created" : "[Error] Movie Table was not Created");
        }
    }

    //maxPCT: is the maximum percentage of seats that will be randomised
    //minPCT: is the minimum percentage of seats that will be randomised
    private void cr8Seats(float maxPCT, float minPCT, Configuration Configuration) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        int seatCounter = 0;
        int numSeats = Configuration.getNumSeats();
        Map<String, List<Integer>> Hall = new HashMap<>();
        Hall.put("MovieList", new ArrayList<>());
        Hall.put("TimeList", new ArrayList<>());
        Hall.put("SeatList", new ArrayList<>());

        for (int M = 1; M < 1 + Configuration.getNumMovies(); M++) {
            for (int T = 1; T < 1 + Configuration.getNumTimes(); T++) {
                for (int S = 1; S < 1 + numSeats; S++) {
                    Hall.get("MovieList").add(M);
                    Hall.get("TimeList").add(T);
                    Hall.get("SeatList").add(S);
                    seatCounter++;
                }
            }
        }

        String query = "INSERT INTO Seats (movieID, Time, seatNum, isBooked) VALUES (?, ?, ?, false)";
        ExeBatchPreparedSTMT(query, Hall, seatCounter);

        System.out.println(seatCounter + " seats should have been created");
        randSeats(maxPCT, minPCT, Configuration);
    }

    private void randSeats(float maxPCT, float minPCT, Configuration Configuration) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        int randCounter = 0;
        Utilities Utilities = new Utilities();
        int numSeats = Configuration.getNumSeats();
        Map<String, List<Integer>> Hall = new HashMap<>();
        Hall.put("MovieList", new ArrayList<>());
        Hall.put("TimeList", new ArrayList<>());
        Hall.put("SeatList", new ArrayList<>());

        for (int M = 1; M < 1 + Configuration.getNumMovies(); M++) {
            for (int T = 1; T < 1 + Configuration.getNumTimes(); T++) {
                for (int S = 0; S < (Utilities.GenerateRanNum((int) (numSeats * maxPCT), (int) (numSeats * minPCT))); S++) {
                    Hall.get("MovieList").add(M);
                    Hall.get("TimeList").add(T);
                    Hall.get("SeatList").add(Utilities.GenerateRanNum(numSeats, 1));
                    randCounter++;
                }
            }
        }
        String query = "UPDATE Seats SET isBooked=true where movieID = ? and Time = ? and seatNum=?";
        ExeBatchPreparedSTMT(query, Hall, randCounter);
        System.out.println(randCounter + " seats should have been randomise");
    }
}
