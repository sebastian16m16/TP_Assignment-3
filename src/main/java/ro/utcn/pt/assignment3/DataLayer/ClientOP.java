package ro.utcn.pt.assignment3.DataLayer;

import ro.utcn.pt.assignment3.Models.Client;

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


}
