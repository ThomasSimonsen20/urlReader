package demo;

import java.sql.*;
import java.util.ArrayList;

public class JDBCWriter {

    private Connection connection = null;

    public boolean setConnection() {
        final String url = "jdbc:mysql://localhost:3306/urlread?serverTimezone=UTC";
        boolean bres = false;

        try {
            connection = DriverManager.getConnection(url, "Thomas", "yes");
            bres = true;
        } catch (SQLException ioerr) {
            System.out.println("Vi fik ikk connection = " + ioerr.getMessage());
        }
        return bres;
    }


    public int deleteRows(String aUrl, String aWord) {
        String delstr = "DELETE FROM urlreads where url like ? and line like ?";
        PreparedStatement preparedStatement;
        int res = -1;
        try {
            preparedStatement = connection.prepareStatement(delstr);
            preparedStatement.setString(1, "%" + aUrl + "%");
            preparedStatement.setString(2, "%" + aWord + "%");
            res = preparedStatement.executeUpdate();
            System.out.println("Line deleted = " + res);
        } catch(SQLException sqlerr) {
            System.out.println("Error in delete = " + sqlerr.getMessage());
        }
        return res;
    }

    public int searchDB(String aUrl, String aWord) {
        String searchStr = "SELECT count(*) FROM urlreads where url like " + '"' + "%" + aUrl + "%" + '"' + " and line like " + '"' + "%" + aWord + "%" + '"';
        PreparedStatement preparedStatement;
        int res = -1;
        try {
            preparedStatement = connection.prepareStatement(searchStr);
            System.out.println(searchStr);
            ResultSet resset =  preparedStatement.executeQuery(searchStr);
            if (resset.next()) {
                String str = "" + resset.getObject(1);
                res = Integer.parseInt(str);
                System.out.println("Fundet antal=" + res);
            }
        } catch (SQLException sqlerr) {
            System.out.println("Fejl i search=" + sqlerr.getMessage());
        }
        return res;
    }

    public int writeLines(String aUrl, ArrayList<String> aLst) {

        String insstr = "INSERT INTO urlreads(url, line) values (?, ?)";
        PreparedStatement preparedStatement;
        int row = 0;

        for (String line : aLst) {
            try {
                preparedStatement = connection.prepareStatement(insstr);
                preparedStatement.setString(1,aUrl);
                preparedStatement.setString(2,line);

                int rowCount = preparedStatement.executeUpdate();
                //System.out.println("Indsat række = " + rowCount);
                row = row + rowCount;

            } catch (SQLException sqlerr) {
                System.out.println("Fejl i INSERT = " + sqlerr.getMessage());
            }
        }
        return row;
    }

}
