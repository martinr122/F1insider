package storage;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebPageReader {
    public static String getLeader(String url){
        try {
            Document document = Jsoup.connect(url).get();
            Element table = document.select("table.resultsarchive-table").first();
            Elements rows = table.select("tbody tr");

            Elements cells = rows.select("td");
            String name = cells.get(2).text();

            return name;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    static void vypis(){
        try {
            String url = "https://www.formula1.com/en/results.html/2023/drivers.html";
            Document document = Jsoup.connect(url).get();
            Element table = document.select("table.resultsarchive-table").first();
            Elements rows = table.select("tbody tr");

            for (Element row : rows) {
                Elements cells = row.select("td");

                String position = cells.get(1).text();
                String driverName = cells.get(2).text();
                String points = cells.get(5).text();

                System.out.println("Position: " + position);
                System.out.println("Driver: " + driverName);
                System.out.println("Points: " + points);
                System.out.println("------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Driver> getDriversStandings(String url){
        List<Driver> drivers = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Element table = document.select("table.resultsarchive-table").first();
            Elements rows = table.select("tbody tr");

            for (Element row : rows) {
                Elements cells = row.select("td");

                String[] driverName = cells.get(2).text().split(" ");
                int points = Integer.parseInt(cells.get(5).text());

                Driver driver = new Driver(driverName[0], driverName[1], points);
                drivers.add(driver);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return drivers;
    }
}
