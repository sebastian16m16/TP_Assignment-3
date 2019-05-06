package ro.utcn.pt.assignment3.PresentationLayer;

import ro.utcn.pt.assignment3.DataLayer.ClientOP;
import ro.utcn.pt.assignment3.DataLayer.DBConnection;
import ro.utcn.pt.assignment3.Models.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

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

        final ClientOP clientOP = new ClientOP();
        final DBConnection connection = DBConnection.getConnection();

        final Font font = new Font("", 1, 20);
        final Object[] columns = {"ID", "Name", "Address"};

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

        addNewCLientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client(JOptionPane.showInputDialog(null, "Enter the name of the Client:"),
                        JOptionPane.showInputDialog(null, "Enter the address of the client: "));

                try {
                    clientOP.addClient(connection.connection, client);
                    JOptionPane.showMessageDialog(null, "Client created!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        viewAllClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] row = new Object[3];
                tableModel.setRowCount(0);

                try {
                    ArrayList<Client> foundClients = clientOP.returnClients(connection.connection);

                    for(int i =0; i< foundClients.size(); i++){
                        row[0] = foundClients.get(i).getId();
                        row[1] = foundClients.get(i).getName();
                        row[2] = foundClients.get(i).getAddress();
                        tableModel.addRow(row);
                        resultsTable.setModel(tableModel);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Insert the id of the client you want to edit!"));

                try {
                    if(clientOP.existsClient(connection.connection, id)){

                        Object response = JOptionPane.showInputDialog(null,
                                "What do you want to edit?", "Edit Choice!",
                                JOptionPane.QUESTION_MESSAGE, null, new String[] { "Name", "Address", "Both" },
                                "Name");
                        if(response == "Name"){
                            Client client = clientOP.getClientbyID(connection.connection, id);
                            clientOP.editClientName(connection.connection, client,
                                    JOptionPane.showInputDialog(null, "Enter the new name of the client!"));
                            JOptionPane.showMessageDialog(null, "Client's Name updated!");
                        }else if(response == "Address"){
                            Client client = clientOP.getClientbyID(connection.connection, id);
                            clientOP.editClientAddress(connection.connection, client, JOptionPane.showInputDialog(null,
                                    "Enter the new Address of the client!"));
                            JOptionPane.showMessageDialog(null, "Client's address updated!");
                        }else {
                            Client client = clientOP.getClientbyID(connection.connection, id);
                            clientOP.editClientName(connection.connection, client,
                                    JOptionPane.showInputDialog(null, "Enter the new name of the client!"));
                            clientOP.editClientAddress(connection.connection, client, JOptionPane.showInputDialog(null,
                                    "Enter the new Address of the client!"));
                            JOptionPane.showMessageDialog(null, "ClientS updated!");
                        }
                 }else
                        JOptionPane.showMessageDialog(null, "The client does not exist!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the id of the client you want to delete!"));

                try {
                    if(clientOP.existsClient(connection.connection, id)){
                        Client client = clientOP.getClientbyID(connection.connection, id);
                        Object response = JOptionPane.showInputDialog(null,
                                "Are you sure you want to delete:\n "+client.getName()+"?", "DELETE ALERT!",
                                JOptionPane.QUESTION_MESSAGE, null, new String[] { "No", "Yes"},
                                "No");
                        if(response == "Yes"){
                            clientOP.deleteClient(connection.connection, client);
                            JOptionPane.showMessageDialog(null, "Client deleted!");
                        }
                    }else
                        JOptionPane.showMessageDialog(null, "The client does not exist!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
