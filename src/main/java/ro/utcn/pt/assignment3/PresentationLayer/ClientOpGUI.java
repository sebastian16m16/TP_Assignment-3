package ro.utcn.pt.assignment3.PresentationLayer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientOpGUI extends JFrame{
    private JButton addNewCLientButton;
    private JButton editClientButton;
    private JButton deleteClientButton;
    private JButton viewAllClientsButton;
    private JTable resultsTable;
    private JButton backButton;
    private JPanel clientOpPanel;

    ClientOpGUI(){

        setTitle("Client Operations");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(clientOpPanel);

        final Font font = new Font("", 1, 20);
        final Object[] columns = {"name", "age", "address"};

        final DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columns);
        resultsTable.setModel(tableModel);
        resultsTable.setRowHeight(30);
        resultsTable.setBackground(Color.cyan);
        resultsTable.setForeground(Color.black);
        resultsTable.setFont(font);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuGUI menuGUI = new MenuGUI();
                menuGUI.setVisible(true);
                setVisible(false);
            }
        });
    }
}
