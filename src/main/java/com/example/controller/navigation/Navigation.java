package com.example.controller.navigation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navigation {
    private CardLayout mainLayout , enseignantLayout , sallesLayout , occupationLayout;
    private JPanel mainPages , enseignantPage , sallesPages , occupationPages;

    public Navigation( JPanel mainPages , JPanel enseignantPage , JPanel sallesPages , JPanel occupationPages){
        this.mainPages=mainPages;
        this.enseignantPage=enseignantPage;
        this.sallesPages = sallesPages;
        this.occupationPages = occupationPages;
        this.mainLayout = (CardLayout) mainPages.getLayout();
        this.enseignantLayout =(CardLayout) enseignantPage.getLayout();
        this.sallesLayout = (CardLayout) sallesPages.getLayout();
        this.occupationLayout = (CardLayout) occupationPages.getLayout();

    }

    public void addNavigationButttons(JButton enseignantsButton , JButton salleButton , JButton occupationButton){
        enseignantsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainLayout.show(mainPages , "enseignants");
                enseignantLayout.show(enseignantPage , "mainEnseignants");
            }
        });

        salleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainLayout.show(mainPages, "salles");
                sallesLayout.show(sallesPages , "mainSalles");
            }
        });

        occupationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainLayout.show(mainPages , "occupation");
                occupationLayout.show(occupationPages , "mainOccupation");
            }
        });
    }
}

