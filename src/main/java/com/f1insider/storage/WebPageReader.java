package com.f1insider.storage;

import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebPageReader {
    public static String getLeader(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Element table = document.select("table.resultsarchive-table").first();
            Elements rows = table.select("tbody tr");

            Elements cells = rows.select("td");
            String name = cells.get(2).text();

            if (url.contains("driver")) {
                String[] nameSplited = name.split(" ");
                String finalName = "";

                for (int i = 0; i < nameSplited.length - 1; i++) {
                    finalName += nameSplited[i];
                    finalName += " ";
                }

                return finalName;
            } else {
                return name;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<Driver> getDriversStandings(String url) {
        List<Driver> drivers = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Element table = document.select("table.resultsarchive-table").first();

            if (table == null) {
                return null;
            }

            Elements rows = table.select("tbody tr");

            for (Element row : rows) {
                Elements cells = row.select("td");

                String[] driverName = cells.get(2).text().split(" ");
                int points = Integer.parseInt(cells.get(5).text());

                String surname = "";

                for (int i = 1; i < driverName.length - 1; i++) {
                    surname += driverName[i];
                    surname += " ";
                }

                Driver driver = new Driver(driverName[0], surname, points);
                drivers.add(driver);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public static List<Team> getTeamsStandings(String url) {
        List<Team> teams = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Element table = document.select("table.resultsarchive-table").first();

            if (table == null) {
                return null;
            }

            Elements rows = table.select("tbody tr");

            for (Element row : rows) {
                Elements cells = row.select("td");

                String name = cells.get(2).text();
                int points = Integer.parseInt(cells.get(5).text());
                Team team = new Team(name, points);
                teams.add(team);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return teams;
    }
}
