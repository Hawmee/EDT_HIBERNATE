package com.example.controller.enseignants;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainEnseignants extends JPanel {
    private JTable table;
    private JScrollPane tableScrollPane;
    private JPanel header , headerButtons , searchWrapper ,search ,  content , enseignantPage;

    private JButton ajout , chercher;
    private CardLayout enseignantsLayout;
    public MainEnseignants(JPanel enseignantPage){

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
        searchWrapper.setBorder(new EmptyBorder(0 , 425 ,0 ,0));
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
                enseignantsLayout.show(enseignantPage , "ajoutEnseignants");
            }
        });



        String[] ensHeader = {"nom" , "Matricule" , "Occupation" , "Actions"};
        Object[][] dataEns = {
                {"John Doe", "1234", "Salle A"},
                {"Jane Smith", "5678", "Salle B"}
        };

        DefaultTableModel model = new DefaultTableModel(dataEns , ensHeader) {
            @Override
            public boolean isCellEditable(int row , int column){
                return column == 3 ;
            }
        };

        table = new JTable(model);
        table.setBackground(Color.WHITE);
        table.setRowHeight(32);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width ,32));
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor());
        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBackground(Color.WHITE);

        content = new JPanel(new BorderLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(12,2,0,2));
        content.add(tableScrollPane , BorderLayout.CENTER);


        add(header , BorderLayout.NORTH);
        add(content , BorderLayout.CENTER);
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
                System.out.println("Modifier clicked for row: " + selectedRow);
                printRowData(selectedRow);
                enseignantsLayout.show(enseignantPage, "modifier");
                fireEditingStopped();
            });

            deleteButton.addActionListener(e -> {
                System.out.println("Supprimer clicked for row: " + selectedRow);
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

