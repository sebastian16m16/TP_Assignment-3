package ro.utcn.pt.assignment3.PresentationLayer;

import ro.utcn.pt.assignment3.DataLayer.ClientOP;
import ro.utcn.pt.assignment3.DataLayer.DBConnection;
import ro.utcn.pt.assignment3.DataLayer.OrderOp;
import ro.utcn.pt.assignment3.DataLayer.ProductOp;
import ro.utcn.pt.assignment3.Models.Client;
import ro.utcn.pt.assignment3.Models.Order;
import ro.utcn.pt.assignment3.Models.Product;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  This Class contains the interface in which the user Creates an order which is placed in the Data Base
 * */

public class PlaceOrderGUI extends JFrame {
    private JList productList;
    private JList clientList;
    private JTextField desiredQuantityField;
    private JLabel quantityLabel;
    private JPanel placeOrderPanel;
    private JButton backButton;
    private JButton placeOrderButton;
    private JLabel sumLabel;
    private JLabel priceLabel;
    private JButton enterQuantityButton;
    private JLabel clientNameLabel;
    private JLabel clientIDLabel;

    int product_id = 0;
    int client_id = 0;

    /**
     *  The constructor creates the window in which the order is created
     * */
    public PlaceOrderGUI() throws SQLException {

        super();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Place Order");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(enterQuantityButton);


        add(placeOrderPanel);

        desiredQuantityField.setText("0");
        sumLabel.setText("0");
        clientNameLabel.setText("");
        clientIDLabel.setText("");
        quantityLabel.setText("");
        priceLabel.setText("");

        final DBConnection connection = DBConnection.getConnection();

        final ProductOp productOp = new ProductOp();
        final ClientOP clientOP = new ClientOP();

//        ArrayList<String> products = productOp.getAllProductsNames(connection.connection);
//        ArrayList<Client> clients = clientOP.returnClients(connection.connection);

        ArrayList<Product> allProducts = productOp.viewAllProducts(connection.connection);
        ArrayList<Client> allClients = clientOP.returnClients(connection.connection);


        DefaultListModel<String> productDefaultListModel = new DefaultListModel<>();
        DefaultListModel<String> clientDefaultListModel = new DefaultListModel<>();
        final int[] clientsID = new int[allClients.size()];

        for(int i=0; i<allProducts.size(); i++){
            productDefaultListModel.addElement(allProducts.get(i).getName());
        }
        productList.setModel(productDefaultListModel);

        for(int i=0; i<allClients.size(); i++){
            clientDefaultListModel.addElement(allClients.get(i).getName());
            clientsID[i] = allClients.get(i).getId();
        }
        clientList.setModel(clientDefaultListModel);


        /**
         *  Here the user selects the product that he/she wants to order
         * */
        productList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Product theProduct = null;
                try {
                    theProduct = productOp.getProductByName(connection.connection, (String) productList.getSelectedValue());
                    quantityLabel.setText(theProduct.getQuantity()+"");
                    priceLabel.setText(theProduct.getPrice()+"");

                    final Product product = theProduct;
                    product_id = product.getProduct_id();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        /**
         *  The user selects the registered client which will get the order
         * */
        clientList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                try {
                    client_id = clientsID[clientList.getSelectedIndex()];
                    Client client = clientOP.getClientbyID(connection.connection, client_id);
                    clientNameLabel.setText(client.getName());
                    clientIDLabel.setText(client.getId()+"");

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });


        /**
         *  This button processes the textField in which the user enters the quantity desired for the order
         * */
        enterQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = null;
                try {
                    product = productOp.getProductByID(connection.connection, product_id);
                    if(Integer.parseInt(desiredQuantityField.getText()) <= product.getQuantity()) {
                        double totalSum = product.price * Double.parseDouble(desiredQuantityField.getText());
                        sumLabel.setText(totalSum + "");
                    }else{
                        JOptionPane.showMessageDialog(null, "Out of stock for this amount!");
                        desiredQuantityField.setText(product.getQuantity()+"");
                        double totalSum = product.price * Double.parseDouble(desiredQuantityField.getText());
                        sumLabel.setText(totalSum + "");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });



        /**
         *  This button checks to see if all fields are completed and after that it places the order in the Data Base
         * */
        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderOp orderOp = new OrderOp();

                try {

                    Client client = null;
                    Product product = null;

                if(!clientNameLabel.getText().equals("")){
                         client = clientOP.getClientbyID(connection.connection, client_id);
                }else{
                    JOptionPane.showMessageDialog(null, "Please select a Client!");
                }

                if(!quantityLabel.getText().equals("")){
                     product = productOp.getProductByID(connection.connection, product_id);
                }else
                    JOptionPane.showMessageDialog(null, "Please select a product!");

                if(desiredQuantityField.getText().equals("0")){
                    JOptionPane.showMessageDialog(null, "Please enter the quantity you want to order!");
                }

                if(!clientNameLabel.getText().equals("") && !quantityLabel.getText().equals("") && !desiredQuantityField.getText().equals("0")){
                    orderOp.placeOrder(connection.connection, product, client, Integer.parseInt(desiredQuantityField.getText()), Double.parseDouble(sumLabel.getText()));
                    JOptionPane.showMessageDialog(null, "Order Registered!");

                   Order registeredOrder = orderOp.getExactOrder(connection.connection, client.getName(),product.getName(),Double.parseDouble(sumLabel.getText()),
                            Integer.parseInt(desiredQuantityField.getText()));

                    createFile(client.getName(), client.getAddress(), product.name, Double.parseDouble(sumLabel.getText()),product.price, Integer.parseInt(desiredQuantityField.getText()));;



                }


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         *  This button opens the previous window (int his case "the order window")
         * */
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderGUI orderGUI = new OrderGUI();
                orderGUI.setVisible(true);
                setVisible(false);
            }
        });


    }

    /**
     *  Method that creates a bill for the order
     * @param name - The name of the client
     * @param product - The name of the product
     * @param address - The address of the client
     * @param sum - The total sum for the order
     * @param price - The price of the product
     * @param quantity - The quantity ordered
     * */
    private void createFile(String name, String address, String product, Double sum, Double price, int quantity){
        int nr = 1;
        File file = new File("C:\\Learning\\Java\\TehniciProgramare\\PT2019_32711_Muresan_Sebastian_Assignment3\\bills\\" + name + "- BILL " + nr + ".txt");

        while(file.exists()){
            nr++;
            file = new File("C:\\Learning\\Java\\TehniciProgramare\\PT2019_32711_Muresan_Sebastian_Assignment3\\bills\\" + name + "- BILL #" + nr + ".txt");
        }
        try {

            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println("Name: " + name);
            printWriter.println("Address: " + address);
            printWriter.println("Product: " + product + "  ..........  Price: " + price + " --- x" + quantity);
            printWriter.println("---------------");
            printWriter.println("Total Sum = " + sum);
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


}
