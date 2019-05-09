package ro.utcn.pt.assignment3.DataLayer;

import ro.utcn.pt.assignment3.Models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  This Class manipulates the products in the Data Base table product
 * */
public class ProductOp {

    /**
     *  This method returns all the available products
     * @param connection - Data Base Connection
     * @return An arrayList with all the existing products
     * */
    public ArrayList<Product> viewAllProducts(Connection connection) throws SQLException{

        ArrayList<Product> allProducts = new ArrayList<>();

        String stmt = "Select * from Product";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Product product = new Product(resultSet.getInt("product_id"), resultSet.getString("name"),
                    resultSet.getDouble("price"), resultSet.getInt("quantity"));
            allProducts.add(product);

        }
        return allProducts;
    }

    /**
     *  This method returns all the names of the available products
     * @param connection - Data Base connection
     * @return An arrayList with all the names of the available products
     * */
    public ArrayList<String> getAllProductsNames(Connection connection) throws SQLException{
        ArrayList<String> allProducts = new ArrayList<>();

        String stmt = "Select * from Product";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){

            String productName = resultSet.getString("Name");
            allProducts.add(productName);

        }
        return allProducts;
    }

    /**
     *  This method returns the product by it's table id
     * @param connection - Data Base Connection
     * @param product_id - The id of the product in the product table
     * @return The searched product
     * */
    public Product getProductByID(Connection connection, int product_id) throws SQLException{

        Product product = new Product();

        String stmt = "Select * from product where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, product_id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            product.setProduct_id(resultSet.getInt("product_id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setQuantity(resultSet.getInt("quantity"));
        }

        return product;
    }

    /**
     *  This method returns the product by it's name
     * @param connection - Data Base Connection
     * @param name - The name of the product
     * @return The searched product
     * */
    public Product getProductByName(Connection connection, String name) throws SQLException{

        Product foundProduct = new Product();

        String stmt = "Select * from product where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            foundProduct.setName(resultSet.getString("name"));
            foundProduct.setPrice(resultSet.getDouble("price"));
            foundProduct.setQuantity(resultSet.getInt("quantity"));
            foundProduct.setProduct_id(resultSet.getInt("product_id"));
        }

        return foundProduct;
    }

    /**
     *  This method adds a product to the Data Base table product
     * @param connection - Data Base connection
     * @param product - The product to be added to the Data Base
     *
     * */
    public void addProduct(Connection connection, Product product) throws SQLException{
        String stmt = "Insert into product(name, price, quantity) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setInt(3, product.getQuantity());
        preparedStatement.executeUpdate();
    }

    /**
     *  THis method checks to see if the product exists (by name)
     * @param connection - Data Base Connection
     * @param name - The name of the product
     * @return true or false
     * */
    public boolean existsProductByName(Connection connection, String name) throws SQLException{
        String stmt = "Select * from product where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
            return true;
        else
            return false;
    }

    /**
     *  This method edits the name of the product with a given id
     * @param connection - Data Base connection
     * @param newName - The new name for the product
     * @param id - The id of the product in the Data Base
     * */
    public void editProductName(Connection connection, String newName, int id) throws SQLException{
        String stmt = "Update product set name = ? where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, newName);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    /**
     *  This method edits the price of the product with a given id
     * @param connection - Data Base Connection
     * @param newPrice - The new price for the product
     * @param id - The id of the product in the Data Base
     * */
    public void editProductPrice(Connection connection, Double newPrice, int id) throws SQLException{
        String stmt = "Update product set price = ? where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setDouble(1, newPrice);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    /**
     *  This method edits the quantity available for the product
     * @param connection - Data Base connection
     * @param newQuantity - The new quantity for the product
     * @param id - The id of the product in the Data Base
     * */
    public void editProductQuantity(Connection connection, int newQuantity, int id) throws SQLException{
        String stmt = "Update product set quantity = ? where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, newQuantity);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    /**
     *  This method deletes a product
     * @param connection - Data Base Connection
     * @param id - The id of the product to be deleted
     *
     * */
    public void deleteProductByID(Connection connection, int id) throws SQLException{
        String stmt = "Delete from product where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    /**
     *  This method returns the quantity of a product
     * @param connection - Data Base Connection
     * @param id - The id of the product in the Data Base
     * @return The available quantity for that product
     * */
    public int getProductQuantityByID(Connection connection, int id) throws SQLException{
        String stmt = "Select quantity from product where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        int quantity = 0;
        while (resultSet.next()){
            quantity = resultSet.getInt("quantity");
        }
        return quantity;
    }
}
