package com.example.controller.enseignants;

import com.example.DAO.ProfDAO;
import com.example.model.Profs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifierEnseignants extends JPanel {

    private JPanel header , content , formContainer , headerContainer, bodyContainer, buttonWrapper ,  enseignantPage;
    private JLabel title , contentTitle , nomLabel , prenomLabel , gradeLabel  ;
    private JTextField nom , prenom , grade;
    private JButton valider , annuler;
    private CardLayout enseignantLayout;
    private GridBagConstraints gbc;

    private MainEnseignants mainEnseignants;
    private int selectedProfId;

    private ProfDAO profDAO;
    public ModifierEnseignants(JPanel enseignantPage , MainEnseignants mainEnseignants, int profId){
        profDAO = new ProfDAO();
        this.mainEnseignants = mainEnseignants;
        this.selectedProfId = profId;
        this.enseignantPage = enseignantPage;
        this.enseignantLayout = (CardLayout) enseignantPage.getLayout();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        title = new JLabel("Enseignants > Modification");
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
        contentTitle = new JLabel("Modification d'un Enseignant");
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


        valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProf();
            }
        });

        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enseignantLayout.show(enseignantPage , "mainEnseignants");
            }
        });

        populateForm(selectedProfId);
        add(header , BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }


    public void populateForm(int profId){
        Profs prof = profDAO.findProfById(profId);

        if(prof!=null){
            nom.setText(prof.getNom());
            prenom.setText(prof.getPrenoms());
            grade.setText(prof.getGrade());
        }
    }

    private void updateProf(){
        String nomValue = nom.getText().toUpperCase();
        String prenomValue = mainEnseignants.capitalize(prenom.getText());
        String gradeValue = mainEnseignants.capitalize(grade.getText());

        Profs prof = profDAO.findProfById(selectedProfId);

        if(prof != null){

            if(nomValue.isEmpty() || prenomValue.isEmpty() || gradeValue.isEmpty()){
                JOptionPane.showMessageDialog(null , "Veulliez remplir les champs");
                return;
            }

            prof.setNom(nomValue);
            prof.setPrenoms(prenomValue);
            prof.setGrade(gradeValue);

            profDAO.saveProf(prof);
            JOptionPane.showMessageDialog(null , "Prof mis a Jour !");
            mainEnseignants.loadProfs();
            mainEnseignants.mainOcc.loadLists();
            mainEnseignants.mainOcc.loadOcc();
            enseignantLayout.show(enseignantPage , "mainEnseignants");
        }
    }
}
