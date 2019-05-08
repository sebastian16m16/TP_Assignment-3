package ro.utcn.pt.assignment3.DataLayer;

import ro.utcn.pt.assignment3.Models.Client;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientOP {

    public ArrayList<Client> returnClients(Connection connection) throws SQLException{

        ArrayList<Client> foundClients = new ArrayList<>();

        String stmt = "Select * from client";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            Client client = new Client(resultSet.getInt("client_id"), resultSet.getString("name"), resultSet.getString("address"));
            foundClients.add(client);
        }
        return foundClients;
    }

    public ArrayList<String> getAllClientsName(Connection connection) throws SQLException{

        ArrayList<String> foundClients = new ArrayList<>();

        String stmt = "Select * from client";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            String client = resultSet.getString("Name");
            foundClients.add(client);
        }
        return foundClients;
    }

    public Client getClientbyID(Connection connection, int client_id)throws SQLException{
        Client foundClient = new Client();

        String stmt = "Select * from client where client_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, client_id);
        ResultSet resultSet =  preparedStatement.executeQuery();

        while (resultSet.next()){
            foundClient.setId(resultSet.getInt("client_id"));
            foundClient.setName(resultSet.getString("name"));
            foundClient.setAddress(resultSet.getString("Address"));
        }

        return foundClient;
    }

    public void addClient (Connection connection, Client client) throws SQLException{
        String stmt = "Insert into Client (Name, Address) Values (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getAddress());

        preparedStatement.executeUpdate();
    }

    public void editClientName (Connection connection, Client client, String newName) throws SQLException{
        String stmt = "Update Client set name = ? where client_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, newName);
        preparedStatement.setInt(2, client.getId());

        preparedStatement.executeUpdate();
    }

    public void editClientAddress(Connection connection, Client client, String newAddress) throws SQLException{
        String stmt = "Update Client set Address = ? where client_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, newAddress);
        preparedStatement.setInt(2, client.getId());
        preparedStatement.executeUpdate();
    }

    public void deleteClient(Connection connection, Client client) throws SQLException{
        String stmt = "Delete From Client where client_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, client.getId());

        preparedStatement.executeUpdate();
    }

    public boolean existsClient(Connection connection, int id) throws SQLException{
        String stmt = "Select * from client where client_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
            return true;
        else
            return false;
    }


}
