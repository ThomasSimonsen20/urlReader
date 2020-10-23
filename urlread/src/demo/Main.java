package demo;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String url = "https://www.dr.dk";
        //url = "https://www.eb.dk";
        URLReader uread = new URLReader();
        ArrayList<String> lst = uread.readUrl(url);


        ArrayList<String> lst2 = new ArrayList<>();
        for(String line : lst) {
            if(line.length() > 15000) {
                String[] strArr = line.split("<");
                for (String ss : strArr) {
                    lst2.add(ss);
                }
            }
        }

        lst.addAll(lst2);


        System.out.println("Læst lst = " + lst.size());

        JDBCWriter jw = new JDBCWriter();
        boolean gotcon = jw.setConnection();
        System.out.println("Got connection = " + gotcon);

        int ires = jw.writeLines(url,lst);
        System.out.println("Gemt antal linjer = " + ires);

        /*
        for (String line:
             lst) {
            System.out.println(line);
        } */
    }
}
