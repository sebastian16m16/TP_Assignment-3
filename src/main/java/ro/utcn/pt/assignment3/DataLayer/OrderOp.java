package ro.utcn.pt.assignment3.DataLayer;

import com.sun.org.apache.regexp.internal.RE;
import com.sun.org.apache.xpath.internal.operations.Or;
import ro.utcn.pt.assignment3.Models.Client;
import ro.utcn.pt.assignment3.Models.Order;
import ro.utcn.pt.assignment3.Models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderOp {

    public ArrayList<Order> getAllOrders(Connection connection) throws SQLException{

        ArrayList<Order> allOrders= new ArrayList<>();

        String stmt = "Select * from productOrder";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Order foundOrder = new Order(resultSet.getInt("order_id"), resultSet.getInt("client_id"),
                    resultSet.getString("client_name"), resultSet.getDouble("totalSum"), resultSet.getInt("product_id"),
                    resultSet.getString("product_name"), resultSet.getInt("quantity"));
            allOrders.add(foundOrder);
        }

        return allOrders;
    }

    public void editOrderQuantity(Connection connection, Order order, int newQuantity) throws SQLException{
        String stmt = "Update productOrder set quantity = ? where order_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, newQuantity);
        preparedStatement.setInt(2, order.getOrder_id());
        preparedStatement.executeUpdate();

        String stmt2 = "Update product set quantity = quantity + ? - ? where product_id = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(stmt2);
        preparedStatement1.setInt(1, order.getQuantity());
        preparedStatement1.setInt(2, newQuantity);
        preparedStatement1.setInt(3, order.getProduct_id());
        preparedStatement1.executeUpdate();

    }

    public void deleteOrder(Connection connection, Order order) throws SQLException{

        String stmt1 = "Update product set quantity = ? where product_id = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(stmt1);
        preparedStatement1.setInt(1, order.getQuantity());
        preparedStatement1.setInt(2, order.getProduct_id());
        preparedStatement1.executeUpdate();

        String stmt = "Delete from productOrder where order_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, order.getOrder_id());
        preparedStatement.executeUpdate();
    }

    public Order getOrderByID(Connection connection, int id) throws SQLException{

        Order foundOrder = new Order();

        String stmt = "Select * from productOrder where order_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            foundOrder.setOrder_id(resultSet.getInt("order_id"));
            foundOrder.setProduct_id(resultSet.getInt("product_id"));
            foundOrder.setProduct_name(resultSet.getString("product_name"));
            foundOrder.setClient_id(resultSet.getInt("client_id"));
            foundOrder.setClient_name(resultSet.getString("client_name"));
            foundOrder.setQuantity(resultSet.getInt("quantity"));
            foundOrder.setTotalSum(resultSet.getDouble("TotalSUM"));
        }

        return foundOrder;
    }

    public void placeOrder(Connection connection, Product product, Client client, int quantity, double totalSum) throws SQLException{

        String stmt = "INSERT INTO productOrder (product_id, product_name, client_id, client_name, quantity, totalSum) values (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);

        preparedStatement.setInt(1, product.getProduct_id());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setInt(3, client.getId());
        preparedStatement.setString(4, client.getName());
        preparedStatement.setInt(5, quantity);
        preparedStatement.setDouble(6, totalSum);

        preparedStatement.executeUpdate();

        String stmt1 = "Update product set quantity = quantity - ? where product_id = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(stmt1);
        preparedStatement1.setInt(1, quantity);
        preparedStatement1.setInt(2, product.getProduct_id());
        preparedStatement1.executeUpdate();
    }

    public Order getExactOrder(Connection connection, String name, String product, Double sum, int quantity)throws SQLException{
        String stmt = "Select * from productOrder where client_name = ? and product_name = ? and totalSum = ? and quantity = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, product);
        preparedStatement.setDouble(3, sum);
        preparedStatement.setInt(4, quantity);

        Order order = new Order();

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            order.setClient_name(resultSet.getString("client_name"));
            order.setProduct_name(resultSet.getString("product_name"));
            order.setTotalSum(resultSet.getDouble("totalSum"));
            order.setQuantity(resultSet.getInt("quantity"));
        }

        return order;
    }


}
