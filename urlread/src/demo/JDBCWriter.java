package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
