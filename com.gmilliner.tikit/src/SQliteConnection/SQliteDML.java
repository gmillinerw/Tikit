package SQliteConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class SQliteDML {

    static String DATA_SOURCE;

    public SQliteDML() {
        DATA_SOURCE = "jdbc:sqlite:com.gmilliner.tikit/DataBase/TikitSQlite.db";
    }

    public static int ExeDML(String query) {
        System.out.println("ExeDML will run SQL Query:\n\t\t" + query);
        try (Connection Connection = DriverManager.getConnection(DATA_SOURCE)) {
            PreparedStatement stmt = Connection.prepareStatement(query);
            System.out.println(stmt.executeUpdate() + " row Was modified");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 1;
        }
        return 0;
    }

    public int ExeBatchPreparedSTMT(String query, Map<String, List<Integer>> Hall, int seatCounter) {
        System.out.println("ExeBatchPreparedSTMT will run prepared statement:\n\t\t" + query);

        try (Connection Connection = DriverManager.getConnection(DATA_SOURCE)) {
            Connection.setAutoCommit(false);
            PreparedStatement pstmt = Connection.prepareStatement(query);
            for (int i = 0; i < seatCounter; i++) {
                pstmt.setInt(1, Hall.get("MovieList").get(i));
                pstmt.setInt(2, Hall.get("TimeList").get(i));
                pstmt.setInt(3, Hall.get("SeatList").get(i));
                pstmt.addBatch();
            }
            int[] count = pstmt.executeBatch();
            int sum = 0;
            for (int i : count) {
                sum += i;
            }
            System.out.println(sum + " statement was rand in bulk");
            Connection.commit();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 1;
        }
        return 0;
    }
}