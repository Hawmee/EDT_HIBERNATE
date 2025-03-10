package com.example;

import com.example.controller.MainApp;

import javax.swing.*;
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

//    private static void out(String text){
//        System.out.println(text);
//    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(MainApp::new);
    }
}

