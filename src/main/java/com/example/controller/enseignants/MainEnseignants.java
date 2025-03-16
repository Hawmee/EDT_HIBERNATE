package com.example.controller.enseignants;

import com.example.DAO.ProfDAO;
import com.example.model.Profs;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainEnseignants extends JPanel {
    private JTable table;

    private JOptionPane jOptionPane = new JOptionPane();
    private JScrollPane tableScrollPane;
    private JPanel header , headerButtons , searchWrapper ,search ,  content , enseignantPage;

    private JButton ajout , chercher;
    private CardLayout enseignantsLayout;

    private DefaultTableModel tableModel;
    private ProfDAO profDAO;

    public MainEnseignants(){
        tableModel = new DefaultTableModel(new String[]{"CodeProf","Nom", "Prenom" , "Grade" , "Actions"} , 0) {
            @Override
            public boolean isCellEditable(int row , int column){
                return column == 4 ;
            }
        };

        profDAO = new ProfDAO();
    }
    public MainEnseignants(JPanel enseignantPage){
        profDAO = new ProfDAO();

        this.enseignantPage = enseignantPage;
        this.enseignantsLayout = (CardLayout) enseignantPage.getLayout();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(0,8,0,8));
        headerButtons = new JPanel(new BorderLayout());
        content = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Enseignants");
        headerLabel.setBorder(new EmptyBorder(22 , 12 , 22 , 22));
        headerLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN , 14));
        header.add(headerLabel , BorderLayout.NORTH);


        ajout = new JButton("Ajouter");
        ajout.setPreferredSize(new Dimension(ajout.getPreferredSize().width , 30));
        chercher = new JButton("chercher");
        chercher.setPreferredSize(new Dimension(chercher.getPreferredSize().width , 30));
        searchWrapper = new JPanel(new BorderLayout());
        searchWrapper.setBorder(new EmptyBorder(0 , 580 ,0 ,0));
        searchWrapper.setBackground(Color.WHITE);
        search = new JPanel(new BorderLayout()); // Keep BorderLayout for text and button alignment

        JTextField searchText = new JTextField(15);
        searchText.setMargin(new Insets(0,12,0,12));
        search.add(searchText, BorderLayout.WEST);
        search.add(chercher, BorderLayout.CENTER);

        searchWrapper.add(search , BorderLayout.EAST); // Add the entire search panel to the right-aligned wrapper
        headerButtons.add(ajout, BorderLayout.WEST);
        headerButtons.add(searchWrapper, BorderLayout.CENTER);

        header.add(headerButtons, BorderLayout.WEST);



        ajout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enseignantPage.add(new AjoutEnseignants(enseignantPage, MainEnseignants.this) , "ajoutEnseignants");
                enseignantsLayout.show(enseignantPage , "ajoutEnseignants");
            }
        });

        chercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = searchText.getText();
                if(!searchValue.isEmpty()){
                    try{
                        Integer criteria = Integer.parseInt(searchValue);
                        List<Profs> profs = profDAO.findProf(criteria);
                        updateTable(profs);
                    }catch(NumberFormatException ex){
                        List<Profs> profs = profDAO.findProf(searchValue);
                        updateTable(profs);
                    }
                }else{
                    loadProfs();
                }
            }
        });


        tableModel = new DefaultTableModel(new String[]{"CodeProf","Nom", "Prenom" , "Grade" , "Actions"} , 0) {
            @Override
            public boolean isCellEditable(int row , int column){
                return column == 4 ;
            }
        };

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
//        table.removeColumn(table.getColumnModel().getColumn(0));
        table.setRowHeight(32);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width ,32));
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor());
        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(Color.WHITE);

        content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(12,2,0,2));
        content.add(tableScrollPane , BorderLayout.CENTER);

        loadProfs();


        add(header , BorderLayout.NORTH);
        add(content , BorderLayout.CENTER);
    }

    public void loadProfs(){
        if (tableModel != null) {
            tableModel.setRowCount(0);
        } else {
            System.err.println("Erreur : tableModel est null dans loadProfs");
        };
        List<Profs> profs = profDAO.getAllProfs();
        for (Profs p : profs){
            tableModel.addRow(new Object[]{p.getCodeprof() , p.getNom() , p.getPrenoms() , p.getGrade() , null});
        }
    }

    private void updateTable(List<Profs> profs){
        tableModel.setRowCount(0);
        for (Profs p : profs ){
            tableModel.addRow(new Object[]{p.getCodeprof(), p.getNom(), p.getPrenoms(), p.getGrade() , null});
        }
    }

    private void deleteProf(int selectedProfId){
        profDAO.deleteProfs(selectedProfId);
        JOptionPane.showMessageDialog(null , "Prof supprimé !");
    }


    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton editButton = new JButton("Modifier");
        private final JButton deleteButton = new JButton("Supprimer");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 1));
            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(Color.WHITE);
            return this;
        }
    }

    // 🔹 Button Editor (Handles Click Events)
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel();
        private final JButton editButton = new JButton("Modifier");
        private final JButton deleteButton = new JButton("Supprimer");
        private int selectedRow;

        public ButtonEditor() {
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            panel.add(editButton);
            panel.add(deleteButton);

            editButton.addActionListener(e -> {
                if(selectedRow != -1){
                    int selectedProfId = (int) table.getModel().getValueAt(selectedRow , 0);
                    enseignantPage.add(new ModifierEnseignants(enseignantPage , MainEnseignants.this , selectedProfId) , "modifierEnseignant");
                    enseignantsLayout.show(enseignantPage , "modifierEnseignant");
                }
            });

            deleteButton.addActionListener(e -> {
                if(selectedRow != -1 && selectedRow < table.getRowCount()) {
                    int selectedProfId = (int) table.getModel().getValueAt(selectedRow, 0);
                    String[] options = {"Valider", "Annuler"};
                    int choice = JOptionPane.showOptionDialog(
                            null,
                            "Voulez vous supprimer cet element ?",
                            "Suppression de prof",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                    if(choice == 0){
                        // Make sure to stop cell editing BEFORE modifying the table model
                        if (table.isEditing()) {
                            table.getCellEditor().stopCellEditing();
                        }

                        deleteProf(selectedProfId);

                        // Use SwingUtilities.invokeLater to update the table after the current event
                        SwingUtilities.invokeLater(() -> {
                            loadProfs();
                            selectedRow = -1;
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Aucun élément sélectionné ou données invalides.");
                }

                // Always fire editing stopped at the end
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.selectedRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "Actions";
        }

        private void printRowData(int row){
            System.out.println("Data in row " + row + ":");
            for (int column = 0; column < table.getColumnCount(); column++) {
                Object cellValue = table.getValueAt(row, column);  // Get the value at the selected row and column
                System.out.println(table.getColumnName(column) + ": " + cellValue);  // Print column name and value
            }
        }
    }
}

