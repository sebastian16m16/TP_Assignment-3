package ro.utcn.pt.assignment3.PresentationLayer;

import ro.utcn.pt.assignment3.DataLayer.ClientOP;
import ro.utcn.pt.assignment3.DataLayer.DBConnection;
import ro.utcn.pt.assignment3.Models.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        super();
        setTitle("Client Operations");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(clientOpPanel);
        final ClientOP clientOP = new ClientOP();
        final DBConnection connection = DBConnection.getConnection();

        final Font font = new Font("", 1, 20);
        final Font font1 = new Font("", Font.BOLD, 15);

        final Object[] columns = {"ID", "Name", "Address"};


        final DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columns);
        resultsTable.setModel(tableModel);
        resultsTable.setRowHeight(30);
        resultsTable.setBackground(Color.cyan);
        resultsTable.setForeground(Color.black);
        resultsTable.setFont(font);

        JTableHeader header = resultsTable.getTableHeader();
        header.setBackground(Color.red);
        header.setForeground(Color.white);
        header.setFont(font1);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        resultsTable.setDefaultRenderer(Object.class, centerRenderer);



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
                    if(!client.getName().equals("") || !client.getAddress().equals("")) {
                        clientOP.addClient(connection.connection, client);
                        JOptionPane.showMessageDialog(null, "Client created!");
                    }
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
                            String newName = JOptionPane.showInputDialog(null, "Enter the new name of the client!");
                            if(!newName.equals("")) {
                                clientOP.editClientName(connection.connection, client, newName);
                                JOptionPane.showMessageDialog(null, "Client's Name updated!");
                            }
                        }else if(response == "Address"){
                            Client client = clientOP.getClientbyID(connection.connection, id);
                            String newAddress = JOptionPane.showInputDialog(null,"Enter the new Address of the client!");

                            if(!newAddress.equals("")) {
                                clientOP.editClientAddress(connection.connection, client, newAddress);

                                JOptionPane.showMessageDialog(null, "Client's address updated!");
                            }
                        }else {
                            Client client = clientOP.getClientbyID(connection.connection, id);


                            String newName = JOptionPane.showInputDialog(null, "Enter the new name of the client!");
                            if(!newName.equals("")) {
                                clientOP.editClientName(connection.connection, client, newName);
                            }

                            String newAddress = JOptionPane.showInputDialog(null,"Enter the new Address of the client!");
                            if(!newAddress.equals("")) {
                                clientOP.editClientAddress(connection.connection, client, newAddress);

                            }
                            if(!newName.equals("") && !newAddress.equals(""))
                                JOptionPane.showMessageDialog(null, "ClientS updated!");
                            else
                                System.out.println("Canceled!");
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
