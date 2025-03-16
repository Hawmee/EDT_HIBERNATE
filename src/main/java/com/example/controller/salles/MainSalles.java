package com.example.controller.salles;

import com.example.DAO.SalleDAO;
import com.example.controller.enseignants.MainEnseignants;
import com.example.model.Salles;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainSalles extends JPanel {
    private JTable table;
    private JScrollPane tableScrollPane;
    private JPanel header , headerButtons , searchWrapper ,search ,  content , sallesPages;

    private JButton ajout , chercher;
    private CardLayout sallesLayout;

    private DefaultTableModel tableModel;
    private SalleDAO salleDAO;

    public MainSalles(JPanel sallesPages){
        salleDAO = new SalleDAO();


        this.sallesPages = sallesPages;
        this.sallesLayout = (CardLayout) sallesPages.getLayout();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(new EmptyBorder(0,8,0,8));
        headerButtons = new JPanel(new BorderLayout());
        content = new JPanel(new BorderLayout());

        JLabel headerLabel = new JLabel("Salles");
        headerLabel.setBorder(new EmptyBorder(22 , 12 , 22 , 22));
        headerLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN , 14));
        header.add(headerLabel , BorderLayout.NORTH);


        ajout = new JButton("Ajouter");
        ajout.setPreferredSize(new Dimension(ajout.getPreferredSize().width , 30));

        headerButtons.add(ajout, BorderLayout.WEST);

        header.add(headerButtons, BorderLayout.WEST);


        ajout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sallesPages.add(new AjoutSalles(sallesPages , MainSalles.this), "ajout");
                sallesLayout.show(sallesPages , "ajout");
            }
        });



        String[] ensHeader = {"nom" , "Matricule" , "Occupation" , "Actions"};
        Object[][] dataEns = {
                {"John Doe", "1234", "Salle A"},
                {"Jane Smith", "5678", "Salle B"}
        };

        tableModel = new DefaultTableModel(new String[]{"CodeSal" , "Designation" , "Actions"} , 0) {
            @Override
            public boolean isCellEditable(int row , int column){
                return column == 2 ;
            }
        };

        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setRowHeight(32);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width ,32));
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumn("Actions").setCellRenderer(new MainSalles.ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new MainSalles.ButtonEditor());
        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(Color.WHITE);

        content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(12,2,0,2));
        content.add(tableScrollPane , BorderLayout.CENTER);


        add(header , BorderLayout.NORTH);
        add(content , BorderLayout.CENTER);
        loadSalles();
    }

    public void loadSalles(){
        if(tableModel!=null){
            tableModel.setRowCount(0);
        }else {
            System.err.println("Erreur : tableModel est null dans loadSalles");
        }
        List<Salles> salles = salleDAO.getAllSalles();
        for(Salles s:salles){
            tableModel.addRow(new Object[]{s.getCodesal() , s.getDesignation() , null});
        }
    }

    private void deleteSalle(int codeSal){
        salleDAO.deletSalle(codeSal);
        JOptionPane.showMessageDialog(null , "Salle Supprimée !");
        loadSalles();
    }


    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private final JButton editButton = new JButton("Modifier");
        private final JButton deleteButton = new JButton("Supprimer");

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 1));
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
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
            panel.add(editButton);
            panel.add(deleteButton);

            editButton.addActionListener(e -> {
                if(selectedRow!=-1){
                    int selectedCodeSal = (int) table.getModel().getValueAt(selectedRow , 0);
                    sallesPages.add(new ModifierSalles(sallesPages , MainSalles.this , selectedCodeSal) , "modif");
                    sallesLayout.show(sallesPages, "modif");
                    fireEditingStopped();
                }
            });

            deleteButton.addActionListener(e -> {
                if(selectedRow != -1 && selectedRow< table.getRowCount()){
                    int selectedCodeSal = (int) table.getModel().getValueAt(selectedRow , 0);
                    String[] options = {"Valider", "Annuler"};
                    int choice = JOptionPane.showOptionDialog(
                            null,
                            "Voulez vous supprimer cet element ?",
                            "Suppression de salle",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                    if(choice ==0){
                        if(table.isEditing()){
                            table.getCellEditor().stopCellEditing();
                        }
                        deleteSalle(selectedCodeSal);

//                        SwingUtilities.invokeLater(()->{
//                            loadSalles();
//                            selectedRow=-1;
//                        });
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Aucun élément sélectionné ou données invalides.");
                }
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
