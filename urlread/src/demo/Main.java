package demo;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String url = "https://www.dr.dk";
        URLReader uread = new URLReader();
        ArrayList<String> lst = uread.readUrl(url);
        System.out.println("Læst lst = " + lst.size());

        /*
        for (String line:
             lst) {
            System.out.println(line);
        } */
    }
}
