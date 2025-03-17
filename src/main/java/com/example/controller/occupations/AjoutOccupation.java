package com.example.controller.occupations;

import com.example.DAO.OccupationDAO;
import com.example.DAO.ProfDAO;
import com.example.DAO.SalleDAO;
import com.example.controller.salles.MainSalles;
import com.example.model.Occupations;
import com.example.model.Profs;
import com.example.model.Salles;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AjoutOccupation extends JPanel {
    private JPanel header , content , formContainer , headerContainer, bodyContainer, buttonWrapper ,  occPages;
    private JLabel title , contentTitle , salle , prof , date ;
    private JComboBox<Profs> profsJComboBox ;
    private JComboBox<Salles> sallesJComboBox;

    private JDateChooser dateChooser;
    private JSpinner timeSpinner;
    private List<Salles> sallesList;
    private List<Profs> profsList;

    private Profs selectedProf ;
    private Salles selectedSalle;
    private JButton valider , annuler;

    private CardLayout occLayout;
    private GridBagConstraints gbc;
    private MainOccupation mainOccupation;
    private OccupationDAO occDAO;

    public AjoutOccupation(JPanel occPages , MainOccupation mainOcc){
        occDAO = new OccupationDAO();

        this.mainOccupation = mainOcc;
        this.occPages = occPages;
        this.occLayout = (CardLayout) occPages.getLayout();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        title = new JLabel("Occupation des salles > Ajout");
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
        contentTitle = new JLabel("Occuper une Salle");
        formContainer.add(headerContainer , BorderLayout.NORTH);
        headerContainer.add(contentTitle);
        contentTitle.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN , 14));
        bodyContainer = new JPanel(new GridBagLayout());
        formContainer.add(bodyContainer , BorderLayout.CENTER);

        gbc= new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill= GridBagConstraints.HORIZONTAL;

        salle = new JLabel("Salle: ");
        prof=new JLabel("Prof");
        date = new JLabel("Date");
        sallesJComboBox = new JComboBox<>();
        profsJComboBox = new JComboBox<>();
        mainOccupation.loadLists();
        sallesList = mainOccupation.sallesList;
        profsList = mainOccupation.profsList;

        for(Salles s: sallesList){
            sallesJComboBox.addItem(s);
        }

        for(Profs p: profsList){
            profsJComboBox.addItem(p);
        }
        sallesJComboBox.setPreferredSize(new Dimension(350,30));
        sallesJComboBox.setBackground(Color.WHITE);
        profsJComboBox.setPreferredSize(new Dimension(350,30));
        profsJComboBox.setBackground(Color.WHITE);

        dateChooser = new JDateChooser();
        SpinnerDateModel timeModel = new SpinnerDateModel();
        timeSpinner = new JSpinner(timeModel);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner , "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        dateChooser.setDate(new Date());

//        sallesJComboBox.setSelectedItem(sallesList.get(0));
//        profsJComboBox.setSelectedItem(profsList.get(0));
////        selectedSalle=(Salles) sallesJComboBox.getSelectedItem();
////        selectedProf=(Profs) sallesJComboBox.getSelectedItem();
//
//        sallesJComboBox.addActionListener(e->{
//            selectedSalle = (Salles) sallesJComboBox.getSelectedItem();
//        });
//
//        profsJComboBox.addActionListener(e->{
//            selectedProf = (Profs) profsJComboBox.getSelectedItem();
//        });

        gbc.gridx = 0 ; gbc.gridy= 0 ;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets= new Insets(0,0,5,0);
        bodyContainer.add(salle , gbc);

        gbc.gridx = 0 ; gbc.gridy= 1 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bodyContainer.add(sallesJComboBox , gbc);

        gbc.gridx = 0 ; gbc.gridy= 2 ;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets= new Insets(0,0,5,0);
        bodyContainer.add(prof , gbc);

        gbc.gridx = 0 ; gbc.gridy= 3 ;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        bodyContainer.add(profsJComboBox , gbc);

        gbc.gridx = 0 ; gbc.gridy= 4 ;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets= new Insets(0,0,5,0);
        bodyContainer.add(date , gbc);

        JPanel dateTimePanel = new JPanel(new GridLayout(1, 2, 5, 0)); // 5px gap between them
        dateTimePanel.add(dateChooser);
        dateTimePanel.add(timeSpinner);
        dateTimePanel.setPreferredSize(new Dimension(350, 30)); // Match JComboBox width

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Make it span two columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bodyContainer.add(dateTimePanel, gbc);



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
                addOcc();
            }
        });

        annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                occLayout.show(occPages , "mainOccupation");
            }
        });

        add(header , BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    private void addOcc(){
//        String designation = nom.getText();
//
////        if(designation.isEmpty()){
////            JOptionPane.showMessageDialog(null , "Veuillez remplir le champ !");
////            return;
////        }
////
////        Salles salle = new Salles(designation);
////        salleDAO.saveSalle(salle);
////        nom.setText("");
////        mainSalles.loadSalles();
////        JOptionPane.showMessageDialog(null , "Nouvelle salle ajoutée !");
////        sallesLayout.show(sallesPage , "mainSalles");
        selectedSalle=(Salles) sallesJComboBox.getSelectedItem();
        selectedProf=(Profs) profsJComboBox.getSelectedItem();
        Date date = dateChooser.getDate();
        Date time = (Date) timeSpinner.getValue();

        Date dateTime = combineDateTime(date , time);

        Occupations occ= new Occupations(selectedProf , selectedSalle , date);
        occDAO.saveOcc(occ);
        JOptionPane.showMessageDialog(null , "Salle occupée avec success !");
        mainOccupation.loadOcc();
        occLayout.show(occPages , "mainOccupation");
    }

    private static Date combineDateTime (Date date , Date time){
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);

        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTime(time);

        dateCalendar.set(Calendar.HOUR_OF_DAY , timeCalendar.get(Calendar.HOUR_OF_DAY));
        dateCalendar.set(Calendar.MINUTE , timeCalendar.get(Calendar.MINUTE));
        dateCalendar.set(Calendar.SECOND , timeCalendar.get(Calendar.SECOND));

        return dateCalendar.getTime();
    }
}
