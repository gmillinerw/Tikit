package SQliteConnection;

public abstract class SQlite {
    String DATA_SOURCE;

    public SQlite() {
        this.DATA_SOURCE = "jdbc:sqlite:DataBase/TikitDB.db";
    }

}
