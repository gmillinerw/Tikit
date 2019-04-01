package CodeForServer;


public abstract class absDAL {

    String DATA_SOURCE;

    public absDAL() {
        //this.DATA_SOURCE = ":memory:;Version=3;New=True;";
        this.DATA_SOURCE = "jdbc:sqlite:DataBase/TikitDB.db";
    }
}
