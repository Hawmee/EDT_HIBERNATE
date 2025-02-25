package com.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.io.FileDescriptor.out;

/**
 * Hello world!
 *
 */
public class App {

    private static void out(String text){
        System.out.println(text);
    }
    public static void main(String[] args){
        LocalDate today = LocalDate.now();
        DayOfWeek todayDay = today.getDayOfWeek();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        LocalDate saturday = today.with(DayOfWeek.SATURDAY);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMMM" , Locale.FRENCH);

        out(String.format("today:" + todayDay));

        out("Week Range:" + monday.format(formatter)+" - "+ saturday.format(formatter));

    }
}

