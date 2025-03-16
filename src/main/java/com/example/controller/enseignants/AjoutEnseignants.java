package com.example.controller.enseignants;

import com.example.DAO.ProfDAO;
import com.example.model.Profs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AjoutEnseignants extends JPanel {

    private JPanel header , content , formContainer , headerContainer, bodyContainer, buttonWrapper ,  enseignantPage;
    private JLabel title , contentTitle , nomLabel , prenomLabel , gradeLabel  ;
    private JTextField nom , prenom , grade;
    private JButton valider , annuler;
    private CardLayout enseignantLayout;
    private GridBagConstraints gbc;

    private MainEnseignants mainEnseignants;

    private JOptionPane jOptionPane = new JOptionPane();


    private ProfDAO profDAO ;
    public AjoutEnseignants(JPanel enseignantPage , MainEnseignants mainEnseignant){

        profDAO = new ProfDAO();
        this.mainEnseignants = mainEnseignant;

        this.enseignantPage = enseignantPage;
        this.enseignantLayout = (CardLayout) enseignantPage.getLayout();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        title = new JLabel("Enseignants > Ajout");
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
        formContainer.setPreferredSize(new Dimension(380 , 300));
        content.add(formContainer , BorderLayout.CENTER);
        headerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contentTitle = new JLabel("Ajouts d'enseignants");
        formContainer.add(headerContainer , BorderLayout.NORTH);
        headerContainer.add(contentTitle);
        contentTitle.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN , 14));
        bodyContainer = new JPanel(new GridBagLayout());
        formContainer.add(bodyContainer , BorderLayout.CENTER);

        gbc= new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill= GridBagConstraints.HORIZONTAL;

        nomLabel = new JLabel("Nom: ");
        prenomLabel = new JLabel("Prenoms: ");
        gradeLabel = new JLabel("Grade: ");
        nom = new JTextField();
        prenom = new JTextField();
        grade= new JTextField();

        nom.setPreferredSize(new Dimension(350,30));
        prenom.setPreferredSize(new Dimension(350,30));
        grade.setPreferredSize(new Dimension(350,30));


        gbc.gridx = 0 ; gbc.gridy= 0 ;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets= new Insets(0,0,5,0);
        bodyContainer.add(nomLabel , gbc);

        gbc.gridx = 0 ; gbc.gridy= 1 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bodyContainer.add(nom , gbc);

        gbc.gridx = 0 ; gbc.gridy= 2 ;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets= new Insets(0,0,5,0);
        bodyContainer.add(prenomLabel , gbc);

        gbc.gridx = 0 ; gbc.gridy= 3 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bodyContainer.add(prenom , gbc);

        gbc.gridx = 0 ; gbc.gridy= 4 ;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets= new Insets(0,0,5,0);
        bodyContainer.add(gradeLabel , gbc);

        gbc.gridx = 0 ; gbc.gridy= 5 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bodyContainer.add(grade , gbc);

        valider= new JButton("Valider");
        annuler = new JButton("Annuler");
        buttonWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonWrapper.add(annuler);
        buttonWrapper.add(valider);

        gbc.gridx = 0 ; gbc.gridy= 6 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bodyContainer.add(buttonWrapper , gbc);



        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enseignantLayout.show(enseignantPage , "mainEnseignants");
            }
        });

        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProfs();
            }
        });

        add(header , BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    private void addProfs(){
        String nomValue = nom.getText();
        String prenomValue = prenom.getText();
        String gradeValue = grade.getText();

        if(nomValue.isEmpty() || prenomValue.isEmpty() || gradeValue.isEmpty()){
            JOptionPane.showMessageDialog(null , "Veuillez remplir les champs.");
            return;
        }

        Profs prof = new Profs(nomValue , prenomValue , gradeValue);
        profDAO.saveProf(prof);
        nom.setText("");
        prenom.setText("");
        grade.setText("");
        mainEnseignants.loadProfs();
        enseignantLayout.show(enseignantPage , "mainEnseignants");
    }
}
