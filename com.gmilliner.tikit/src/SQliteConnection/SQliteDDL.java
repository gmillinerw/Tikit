package SQliteConnection;

import CodeForServer.absDAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQliteDDL extends SQlite {
   /*
      methods to create a Data Definition Language (DDL) Statements using the a
      try cache
     */

    /**
     * will return a String ArrayList, as a column.
     */
    Map<String, List<Object>> getmapQuery(String query) {
        System.out.println("intQuery will run SQL Query:\n\t\t" + query);

        Map<String, List<Object>> result = new HashMap<>();
        String[] keys = query.replace(" ", "").replace("SELECT", "").split("FROM")[0].split(",");
        for (String Key : keys) {
            result.put(Key, new ArrayList<>());
        }
        try (Connection Connection = DriverManager.getConnection(DATA_SOURCE)) {
            PreparedStatement stmt = Connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                for (int i = 1; i < keys.length; i++) {
                    result.get(keys[i]).add(resultSet.getInt(keys[i]));
                }
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    ArrayList<Integer> getintListQuery(String query) {
        System.out.println("intQuery will run SQL Query:\n\t\t" + query);

        ArrayList<Integer> result = new ArrayList<>();

        try (Connection Connection = DriverManager.getConnection(DATA_SOURCE)) {
            PreparedStatement stmt = Connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    protected ArrayList<String> ExeDDL(String query) {
        System.out.println("ExeDDL will run SQL Query:\n\t\t" + query);
        ArrayList<String> QueryResults = new ArrayList<>();

        try (Connection Connection = DriverManager.getConnection(DATA_SOURCE)) {
            PreparedStatement stmt = Connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            ResultSetMetaData resultSetMD = resultSet.getMetaData();
            int columnCount;

            System.out.println("\t\t\t---- START OF QUERY RESULTS ----");
            columnCount = resultSetMD.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String name = resultSetMD.getColumnTypeName(i);
                System.out.print("  \t");
                System.out.print(name);
            }
            System.out.println();

            for (int i = 1; i <= columnCount; i++) {
                System.out.print("  \t");
                String columnName = resultSetMD.getColumnName(i);
                System.out.print(columnName);
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    QueryResults.add(resultSet.getString(i));
                    System.out.print("  \t");
                    System.out.print(resultSet.getString(i));
                }
                System.out.println();
            }

        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        System.out.println("\t\t\t---- END OF QUERY RESULTS ----\n");
        return QueryResults;
    }

    /**
     * will return a single int value.
     */
    protected int intQuery(String query) {
        System.out.println("intQuery will run SQL Query:\n\t\t" + query);
        int result = 0;
        try (Connection Connection = DriverManager.getConnection(DATA_SOURCE)) {
            PreparedStatement stmt = Connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    /**
     * will return a single boolean value.
     */
    protected boolean booleanQuery(String query) {
        System.out.println("booleanDDL will run SQL Query:\n\t\t" + query);
        boolean result = false;
        try (Connection Connection = DriverManager.getConnection(DATA_SOURCE)) {
            PreparedStatement stmt = Connection.prepareStatement(query, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    /**
     * will return a single string value.
     */
    protected String stringQuery(String query) {
        System.out.println("stringDDL will run SQL Query:\n\t\t" + query);
        String result = null;
        try (Connection Connection = DriverManager.getConnection(DATA_SOURCE)) {
            PreparedStatement stmt = Connection.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString(1);
            }
            PrintResultSet(resultSet);
        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Query failed to run");
        }
        return result;
    }

    /**
     * will pint out the result set to the console.
     */
    void PrintResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                ResultSetMetaData resultSetMD = resultSet.getMetaData();
                int columnCount = resultSetMD.getColumnCount();
                System.out.println("\t\t\t---- START OF QUERY RESULTS ----\n");

                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("  \t" + resultSetMD.getColumnTypeName(i));
                }
                System.out.print("\n");

                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("  \t" + resultSetMD.getColumnName(i));
                }
                System.out.print("\n");
                for (int i = 1; i <= columnCount; i++) {
                    if (i == 1) {
                        resultSet.next();
                        System.out.print("  \t" + resultSet.getString(i));
                    }
                    while (resultSet.next()) {
                        System.out.print("  \t" + resultSet.getString(i));
                    }
                    System.out.print("\n");
                }
                System.out.println("\t\t\t---- END OF QUERY RESULTS ----\n");
            }

        } catch (SQLException ex) {
            Logger.getLogger(absDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
