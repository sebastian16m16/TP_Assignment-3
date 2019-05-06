package ro.utcn.pt.assignment3.DataLayer;

import ro.utcn.pt.assignment3.Models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductOp {

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

    public void addProduct(Connection connection, Product product) throws SQLException{
        String stmt = "Insert into product(name, price, quantity) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, product.getName());
        preparedStatement.setDouble(2, product.getPrice());
        preparedStatement.setInt(3, product.getQuantity());
        preparedStatement.executeUpdate();
    }

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

    public void editProductName(Connection connection, String newName, int id) throws SQLException{
        String stmt = "Update product set name = ? where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, newName);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    public void editProductPrice(Connection connection, Double newPrice, int id) throws SQLException{
        String stmt = "Update product set price = ? where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setDouble(1, newPrice);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    public void editProductQuantity(Connection connection, int newQuantity, int id) throws SQLException{
        String stmt = "Update product set quantity = ? where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, newQuantity);
        preparedStatement.setInt(2, id);
        preparedStatement.executeUpdate();
    }

    public void deleteProductByID(Connection connection, int id) throws SQLException{
        String stmt = "Delete from product where product_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

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
