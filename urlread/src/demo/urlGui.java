package demo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class urlGui {

    JTextField txtUrl;
    JTextField txtResult;
    ArrayList<String> urlList;
    JDBCWriter jdbcWriter;
    JButton pbSaveToDB;
    JLabel lblCount;
    JButton pbDelete;
    JList lbLines;


    public urlGui() {
        jdbcWriter = new JDBCWriter();
    }

    public void retriveLinesDB() {
        Vector<String> v1 = new Vector<>();
        v1.add("Linje1");
        v1.add("Set for");
        v1.add("teeeest");

        String url = txtUrl.getText();
        String srch = txtResult.getText();
        v1 = jdbcWriter.getLines(url, srch);
        lbLines.setListData(v1);
    }


    public void retrieveUrl() {
        String url = txtUrl.getText();
        System.out.println(url);

        URLReader uread = new URLReader();
        urlList= uread.readUrl(url);

        int sz = urlList.size();
        txtResult.setText("" + sz);

    }

    public void connect() {
        boolean gotCon = jdbcWriter.setConnection();
        System.out.println("Got connection = " + gotCon);
        pbSaveToDB.setEnabled(gotCon);
        pbDelete.setEnabled(gotCon);
    }

    public void saveToDB() {
        String url = txtUrl.getText();
        int rowcnt = jdbcWriter.writeLines(url, urlList);
        System.out.println("Linjer gemt = " + rowcnt);
    }

    public void deleteDB() {
        lblCount.setText("");
        String url = txtUrl.getText();
        String srch = txtResult.getText();
        int cnt = jdbcWriter.deleteRows(url, srch);
        lblCount.setText(""+cnt);
    }

    public void searchDB() {
        String url = txtUrl.getText();
        String srch = txtResult.getText();
        int cnt = jdbcWriter.searchDB(url, srch);
        lblCount.setText(""+cnt);
    }

    public void createGui() {
        final JFrame frame = new JFrame("URL Gui hent en url");
        JPanel panelTop = new JPanel();
        JButton pbConnect = new JButton("Connect");
        JButton pbRetrieveUrl = new JButton("Hent url");
        pbSaveToDB = new JButton("Save to DB");
        JButton pbSearchDB = new JButton("Søg");
        pbDelete = new JButton("Delete");

        frame.getContentPane().add(panelTop, BorderLayout.NORTH);
        panelTop.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        frame.add(panelTop);

        panelTop.add(pbConnect);
        panelTop.add(pbRetrieveUrl);
        panelTop.add(pbSaveToDB);
        panelTop.add(pbSearchDB);
        panelTop.add(pbDelete);
        pbSaveToDB.setEnabled(false);
        pbDelete.setEnabled(false);

        txtUrl = new JTextField("", 50);
        txtResult = new JTextField("", 20);
        lblCount = new JLabel("-1");

        panelTop.add(txtUrl);
        panelTop.add(txtResult);
        panelTop.add(lblCount);
        pbRetrieveUrl.addActionListener(a -> retrieveUrl());
        pbConnect.addActionListener(a -> connect());
        pbSaveToDB.addActionListener(a -> saveToDB());
        pbSearchDB.addActionListener(a -> searchDB());
        pbDelete.addActionListener(a -> deleteDB());

        JPanel panelBottom = new JPanel();
        JButton pbTest = new JButton("Dette er bunden");
        panelBottom.add(pbTest);

        Vector<String> v1 = new Vector<>();
        v1.add("1");
        v1.add("2");
        v1.add("3");
        lbLines = new JList(v1);
        panelBottom.add(lbLines);
        pbTest.addActionListener(a -> retriveLinesDB());


        frame.getContentPane().add(panelBottom, BorderLayout.SOUTH);

        //MAIN WINDOW
        frame.pack();
        frame.setBounds(100, 100, 600, 200);
        frame.setVisible(true);
    }
}
