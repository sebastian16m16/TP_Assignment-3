package ro.utcn.pt.assignment3.PresentationLayer;

import ro.utcn.pt.assignment3.DataLayer.DBConnection;
import ro.utcn.pt.assignment3.DataLayer.OrderOp;
import ro.utcn.pt.assignment3.DataLayer.ProductOp;
import ro.utcn.pt.assignment3.Models.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderGUI extends JFrame{
    private JPanel orderPanel;
    private JTable resultsTable;
    private JButton viewAllOrdersButton;
    private JButton editOrderButton;
    private JButton deleteOrderButton;
    private JButton placeOrderButton;
    private JButton backButton;

    public OrderGUI(){
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setTitle("Orders");





        add(orderPanel);

        final OrderOp orderOp = new OrderOp();
        final ProductOp productOp = new ProductOp();
        final DBConnection connection = DBConnection.getConnection();

        final Font font = new Font("", 1, 20);
        final Font font1 = new Font("", Font.BOLD, 15);


        final Object[] columns = {"Order ID", "Product ID", "Product Name", "Client ID", "Client Name", "Quantity", "Total SUM"};
        final DefaultTableModel tableModel = new DefaultTableModel();

        tableModel.setColumnIdentifiers(columns);


        resultsTable.setRowHeight(30);
        resultsTable.setBackground(Color.cyan);
        resultsTable.setForeground(Color.black);
        resultsTable.setFont(font);
        resultsTable.setModel(tableModel);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        resultsTable.setDefaultRenderer(Object.class, centerRenderer);

        JTableHeader header = resultsTable.getTableHeader();
        header.setBackground(Color.red);
        header.setForeground(Color.white);
        header.setFont(font1);






        viewAllOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] row = new Object[7];
                tableModel.setRowCount(0);

                try{

                    ArrayList<Order> foundOrders = orderOp.getAllOrders(connection.connection);

                    for(int i=0; i<foundOrders.size(); i++){
                        row[0] = foundOrders.get(i).getOrder_id();
                        row[1] = foundOrders.get(i).getProduct_id();
                        row[2] = foundOrders.get(i).getProduct_name();
                        row[3] = foundOrders.get(i).getClient_id();
                        row[4] = foundOrders.get(i).getClient_name();
                        row[5] = foundOrders.get(i).getQuantity();
                        row[6] = foundOrders.get(i).getTotalSum();

                        tableModel.addRow(row);
                        tableModel.setColumnIdentifiers(columns);
                        resultsTable.setModel(tableModel);
                    }

                }catch (SQLException e1){
                    e1.printStackTrace();
                }

            }
        });


        editOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Order orderToEdit = orderOp.getOrderByID(connection.connection,
                            Integer.parseInt(JOptionPane.showInputDialog(null, "Insert the ID of the order you want to EDIT!")));

                    JOptionPane.showMessageDialog(null, "The available quantity of the product is: " + productOp.getProductQuantityByID(connection.connection, orderToEdit.getProduct_id()));

                    int newQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Insert the new Quantity you want to order: "));

                    Object response = JOptionPane.showInputDialog(null, "The new Quantity for the product: " +
                            orderToEdit.getProduct_name() + " is " + newQuantity + "\n Do you want to continue?", "Edit Alert!",
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "No");

                    if(response == "Yes"){

                        if(productOp.getProductQuantityByID(connection.connection, orderToEdit.getProduct_id()) > newQuantity){
                            orderOp.editOrderQuantity(connection.connection, orderToEdit, newQuantity);
                            JOptionPane.showMessageDialog(null, "Order Edited Successfully!");

                        }else
                            JOptionPane.showMessageDialog(null, "Out of stock for this amount!");

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Insert the id of the order you want to delete!"));

                try {
                    Order orderToDelete = orderOp.getOrderByID(connection.connection, id);

                    Object response = JOptionPane.showInputDialog(null, "Are you sure you want to delete the order: \n" +
                    "Product Name: " + orderToDelete.getProduct_name() + "\n" +
                    "Quantity: " + orderToDelete.getQuantity()+ "\n" +
                    "Client Name: " + orderToDelete.getClient_name()+"?", "Delete Alert",
                            JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "No");

                    if(response == "Yes"){
                        orderOp.deleteOrder(connection.connection, orderToDelete);
                        JOptionPane.showMessageDialog(null, "Order Deleted Successfully!");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaceOrderGUI placeOrderGUI = null;
                try {
                    placeOrderGUI = new PlaceOrderGUI();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                placeOrderGUI.setVisible(true);
                setVisible(false);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuGUI menuGUI = new MenuGUI();
                menuGUI.setVisible(true);
                setVisible(false);
            }
        });
    }
/*
    private void editFile(String name, String product, Double price, double sum, int quantity){

        File file = new File("C:\\Learning\\Java\\TehniciProgramare\\PT2019_32711_Muresan_Sebastian_Assignment3\\bills\\" + name + "- BILL " + nr + ".txt");

        if(file.exists()){

        }

    }

 */

}
