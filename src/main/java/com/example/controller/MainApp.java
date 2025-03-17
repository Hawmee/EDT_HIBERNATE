package com.example.controller;

import com.example.controller.enseignants.AjoutEnseignants;
import com.example.controller.enseignants.MainEnseignants;
import com.example.controller.enseignants.ModifierEnseignants;
import com.example.controller.navigation.Navigation;
import com.example.controller.occupations.MainOccupation;
import com.example.controller.salles.AjoutSalles;
import com.example.controller.salles.MainSalles;
import com.example.controller.salles.ModifierSalles;

import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private JPanel mainWindow;
    private JTextArea GESTIONDESALLESTextArea;
    private JButton enseignantsButton;
    private JButton sallesButton;
    private JButton occupationDesSallesButton;
    private JPanel MainPages;
    private JPanel pageEnseignants;
    private JPanel pageSalles;
    private JPanel pageOccupation;
    private JPanel ajoutEnseignants;
    private JPanel modifEnseignants;
    private JPanel suppEnseignants;
    private JPanel mainEnseignants;
    private JButton ajouterButton;
    private JTextArea enseignantsAjoutTextArea;
    private JPanel ajoutEnsTitle;
    private JPanel ajoutEnsContent;
    private JPanel ensFormContainer;
    private JPanel ajoutSalles;
    private JPanel modifierSalles;
    private JPanel mainSalles;
    private JPanel ajoutOccupations;
    private JPanel modifierOccupations;
    private JPanel mainOccupations;
    private JPanel navBarLayout;

    private MainOccupation mainOccupation;


//    private JPanel mainPagesLayout;

    public MainApp(){
        setTitle("Occupation Salle");
        setSize(1150 , 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainWindow);
        setVisible(true);


        MainPages.add("enseignants" , pageEnseignants);
        MainPages.add("salles" , pageSalles);
        MainPages.add("occupation" , pageOccupation);

        mainOccupation = new MainOccupation(pageOccupation);
        mainOccupations = mainOccupation;
        mainEnseignants = new MainEnseignants(pageEnseignants , mainOccupation);
        mainSalles = new MainSalles(pageSalles , mainOccupation);

        pageEnseignants.add("mainEnseignants" , mainEnseignants);
        pageSalles.add("mainSalles" , mainSalles);
        pageOccupation.add("mainOccupation" , mainOccupations);


        CardLayout enseignantsLayout = (CardLayout) pageEnseignants.getLayout();


        enseignantsLayout.show(pageEnseignants , "mainEnseignants");

        Navigation navigation = new Navigation(MainPages, pageEnseignants , pageSalles , pageOccupation );
        navigation.addNavigationButttons(enseignantsButton , sallesButton , occupationDesSallesButton);

    }


}
