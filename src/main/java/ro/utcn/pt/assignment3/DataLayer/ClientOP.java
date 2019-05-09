package ro.utcn.pt.assignment3.DataLayer;

import ro.utcn.pt.assignment3.Models.Client;
import sun.util.cldr.CLDRLocaleDataMetaInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  This Class operates the Data Base Table Client
 *
 * */

public class ClientOP {

    /**
     * This method returns all the clients from the Data Base
     * @param connection - The connection to the Data Base
     * @return An array list of clients
     * */
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

    /**
     *  This method return only the clients name (all clients names)
     * @param connection - Data Base Connection
     * @return An arrayList of clients
     * */
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

    /**
     *  This method returns a client based on the ID
     * @param  connection - Data Base Connection
     * @param  client_id - The id of client in the Data Base
     * @return The searched client
     * */
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

    /**
     *  This Method adds a client to the Data Base
     * @param connection - Data Base Connection
     * @param client - The client that needs to be inserted into the Data Base
     *
     * */
    public void addClient (Connection connection, Client client) throws SQLException{
        String stmt = "Insert into Client (Name, Address) Values (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, client.getAddress());

        preparedStatement.executeUpdate();
    }

    /**
     *  This Method edits an existing client's name
     * @param connection - Data Base connection
     * @param client - the client that is being updated
     * @param newName - The new name of the client
     * */
    public void editClientName (Connection connection, Client client, String newName) throws SQLException{
        String stmt = "Update Client set name = ? where client_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, newName);
        preparedStatement.setInt(2, client.getId());

        preparedStatement.executeUpdate();
    }

    /**
     * This method edits an existing client's address
     * @param connection - Data Base connection
     * @param client - The client that is being updated
     * @param newAddress - The new Address of the client
     * */
    public void editClientAddress(Connection connection, Client client, String newAddress) throws SQLException{
        String stmt = "Update Client set Address = ? where client_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setString(1, newAddress);
        preparedStatement.setInt(2, client.getId());
        preparedStatement.executeUpdate();
    }

    /**
     *  This method deletes and existing client
     * @param connection - Data Base connection
     * @param client - The client that is being deleted
     * */
    public void deleteClient(Connection connection, Client client) throws SQLException{
        String stmt = "Delete From Client where client_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(stmt);
        preparedStatement.setInt(1, client.getId());

        preparedStatement.executeUpdate();
    }

    /**
     *  This method verifies if the client exists (by id)
     * @param connection - Data Base connection
     * @param id - The id of the client in the Data Base
     * @return true or false
     * */
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
