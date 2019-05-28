package main.java.endpoints;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import main.java.com.DiningHallImporter;


public class Test {
    public static void main(String[] args) {
        DiningHallImporter dh0 = new DiningHallImporter(LocalDate.of(2019, 04, 18));
        DiningHallImporter dh1 = new DiningHallImporter(LocalDate.of(2019, 04, 19));
        DiningHallImporter dh2 = new DiningHallImporter(LocalDate.of(2019, 04, 20));
        DiningHallImporter dh3 = new DiningHallImporter(LocalDate.of(2019, 04, 21));
        DiningHallImporter dh4 = new DiningHallImporter(LocalDate.of(2019, 04, 22));
        DiningHallImporter dh5 = new DiningHallImporter(LocalDate.of(2019, 04, 23));
        DiningHallImporter dh6 = new DiningHallImporter(LocalDate.of(2019, 04, 24));
        DiningHallImporter dh7 = new DiningHallImporter(LocalDate.of(2019, 04, 25));
        DiningHallImporter dh8 = new DiningHallImporter(LocalDate.of(2019, 04, 26));
        DiningHallImporter dh9 = new DiningHallImporter(LocalDate.of(2019, 04, 27));
        DiningHallImporter dh10 = new DiningHallImporter(LocalDate.of(2019, 04, 28));
        DiningHallImporter dh11 = new DiningHallImporter(LocalDate.of(2019, 04, 29));
        DiningHallImporter dh12 = new DiningHallImporter(LocalDate.of(2019, 04, 30));
        dh0.run();
        dh1.run();
        dh2.run();
        dh3.run();
        dh4.run();
        dh5.run();
        dh6.run();
        dh7.run();
        dh8.run();
        dh9.run();
        dh10.run();
        dh11.run();
        dh12.run();

    }
}