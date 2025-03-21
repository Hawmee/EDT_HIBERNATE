package com.example.controller.salles;

import com.example.DAO.SalleDAO;
import com.example.model.Salles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifierSalles extends JPanel {

    private JPanel header , content , formContainer , headerContainer, bodyContainer, buttonWrapper ,  sallesPage;
    private JLabel title , contentTitle , nomLabel ;
    private JTextField nom ;
    private JButton valider , annuler;

    private CardLayout sallesLayout;
    private GridBagConstraints gbc;

    private MainSalles mainSalles;
    private int selectedCodeSal;
    private SalleDAO salleDAO;

    public ModifierSalles(JPanel sallesPage , MainSalles mainSalles , int selectedCodeSal){
        salleDAO = new SalleDAO();

        this.mainSalles = mainSalles;
        this.selectedCodeSal= selectedCodeSal;
        this.sallesPage = sallesPage;
        this.sallesLayout = (CardLayout) sallesPage.getLayout();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        title = new JLabel("Salles > Modification");
        header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(0,8,0,8));
        title.setBorder(new EmptyBorder(22 , 12 , 22 , 22));
        title.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN , 14));
        header.add(title , BorderLayout.NORTH);

        content= new JPanel(new FlowLayout(FlowLayout.CENTER));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(50,0,0,0));
        formContainer = new JPanel(new BorderLayout());
        formContainer.setPreferredSize(new Dimension(380 , 150));
        content.add(formContainer , BorderLayout.CENTER);
        headerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentTitle = new JLabel("Modification d'une salle");
        formContainer.add(headerContainer , BorderLayout.NORTH);
        headerContainer.add(contentTitle);
        contentTitle.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN , 14));
        bodyContainer = new JPanel(new GridBagLayout());
        formContainer.add(bodyContainer , BorderLayout.CENTER);

        gbc= new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill= GridBagConstraints.HORIZONTAL;

        nomLabel = new JLabel("Designation : ");
        nom = new JTextField();
        nom.setPreferredSize(new Dimension(350,30));

        gbc.gridx = 0 ; gbc.gridy= 0 ;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets= new Insets(0,0,5,0);
        bodyContainer.add(nomLabel , gbc);

        gbc.gridx = 0 ; gbc.gridy= 1 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bodyContainer.add(nom , gbc);


        valider= new JButton("Valider");
        annuler = new JButton("Annuler");
        buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonWrapper.add(annuler);
        buttonWrapper.add(valider);

        gbc.gridx = 0 ; gbc.gridy= 2 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bodyContainer.add(buttonWrapper , gbc);



        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSalle();
            }
        });
        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sallesLayout.show(sallesPage , "mainSalles");
            }
        });

        add(header , BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        populateForm(selectedCodeSal);
    }

    private void populateForm(int codeSal){
        Salles salle = salleDAO.findSalleById(codeSal);

        if(salle!=null){
            nom.setText(salle.getDesignation());
        }
    }

    private void updateSalle(){
        String designation = nom.getText();

        Salles salle = salleDAO.findSalleById(selectedCodeSal);
        if(salle!=null){
            if(designation.isEmpty()){
                JOptionPane.showMessageDialog(null , "Veuillez remplir le champ !");
            }

            salle.setDesignation(designation);
            salleDAO.saveSalle(salle);
            JOptionPane.showMessageDialog(null , "Salle mise a Jour !");
            mainSalles.loadSalles();
            mainSalles.mainOcc.loadOcc();
            mainSalles.mainOcc.loadLists();
            sallesLayout.show(sallesPage , "mainSalles");
        }
    }
}
