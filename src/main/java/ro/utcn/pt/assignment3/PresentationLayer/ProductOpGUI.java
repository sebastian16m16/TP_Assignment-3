package ro.utcn.pt.assignment3.PresentationLayer;

import jdk.nashorn.internal.scripts.JO;
import ro.utcn.pt.assignment3.DataLayer.DBConnection;
import ro.utcn.pt.assignment3.DataLayer.ProductOp;
import ro.utcn.pt.assignment3.Models.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductOpGUI extends JFrame{
    private JTable resultsTable;
    private JButton addProductButton;
    private JButton editProductButton;
    private JButton deleteProductButton;
    private JButton viewAllProductsButton;
    private JPanel productOpPanel;
    private JButton backButton;

    public ProductOpGUI(){

        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Product Operations");
        setSize(1330, 900);
        setLocationRelativeTo(null);
        final MenuGUI menuGUI = new MenuGUI();

        add(productOpPanel);



        final DBConnection connection = DBConnection.getConnection();
        final ProductOp productOp = new ProductOp();
        final Font font = new Font("", 1, 20);
        final Font font1 = new Font("", Font.BOLD, 15);

        final Object[] columns = {"ID", "Name", "Price", "Quantity"};

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


        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product newProduct = new Product(JOptionPane.showInputDialog(null, "Enter the name of the product!"),
                        Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the price of the product!")),
                        Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the Quantity of the product!")));

                try {
                    if(!productOp.existsProductByName(connection.connection, newProduct.getName())){
                        productOp.addProduct(connection.connection, newProduct);
                        JOptionPane.showMessageDialog(null, "Product added successfully!");
                    }else
                        JOptionPane.showMessageDialog(null, "This Product is already in the dataBase!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menuGUI.setVisible(true);
                setVisible(false);
            }
        });

        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the id of the product you want to edit!"));
                Product product = productOp.getProductByID(connection.connection, id);
                Object response = JOptionPane.showInputDialog(null, "What do you want to edit?" +
                                "\n Name = " + product.getName() +
                                "\n Price = " + product.getPrice() +
                                "\n Quantity = " + product.getQuantity(),"Edit Option!",
                        JOptionPane.QUESTION_MESSAGE, null, new String[]{"Name", "Price", "Quantity", "All"}, "Name");

                if(response == "Name"){
                    String newName = JOptionPane.showInputDialog(null, "Insert new Name of the Product!");

                        productOp.editProductName(connection.connection, newName, id);
                        JOptionPane.showMessageDialog(null, "Name updated!");

                }else if(response == "Price"){
                    Double newPrice = Double.parseDouble(JOptionPane.showInputDialog(null, "Insert new Price of the Product!"));

                    productOp.editProductPrice(connection.connection, newPrice, id);
                    JOptionPane.showMessageDialog(null, "Price updated!");

                }else if(response == "Quantity"){
                    int newQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Insert new Quantity of the Product!"));

                    productOp.editProductQuantity(connection.connection, newQuantity, id);
                    JOptionPane.showMessageDialog(null, "Quantity updated!");
                }else if(response == "All"){
                    String newName = JOptionPane.showInputDialog(null, "Insert new Name of the Product!");
                    productOp.editProductName(connection.connection, newName, id);

                    Double newPrice = Double.parseDouble(JOptionPane.showInputDialog(null, "Insert new Price of the Product!"));
                    productOp.editProductPrice(connection.connection, newPrice, id);

                    int newQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Insert new Quantity of the Product!"));
                    productOp.editProductQuantity(connection.connection, newQuantity, id);

                    JOptionPane.showMessageDialog(null, "Product updated!");
                }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Insert the id of the product you want to DELETE!"));
                    Product product = productOp.getProductByID(connection.connection, id);

                    Object response = JOptionPane.showInputDialog(null, "Are you sure?" +
                                    "\n Name = " + product.getName() +
                                    "\n Price = " + product.getPrice() +
                                    "\n Quantity = " + product.getQuantity(), "DELETE ALERT!", JOptionPane.QUESTION_MESSAGE,
                            null, new String[]{"Yes", "No"}, "No");

                    if(response == "Yes"){
                        productOp.deleteProductByID(connection.connection, id);
                        JOptionPane.showMessageDialog(null, "Product deleted!");
                    }

                }catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
        });

        viewAllProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Product> foundProducts = productOp.viewAllProducts(connection.connection);
                    Object[] row = new Object[4];
                    tableModel.setRowCount(0);

                    for(int i=0; i<foundProducts.size(); i++){
                        row[0] = foundProducts.get(i).getProduct_id();
                        row[1] = foundProducts.get(i).getName();
                        row[2] = foundProducts.get(i).getPrice();
                        row[3] = foundProducts.get(i).getQuantity();

                        tableModel.addRow(row);
                        resultsTable.setModel(tableModel);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
        });
    }
}
