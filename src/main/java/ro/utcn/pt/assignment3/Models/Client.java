package ro.utcn.pt.assignment3.Models;

public class Client {

    protected int id;
    protected String name;
    protected String address;

    public Client() {
    }

    public Client(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Client(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
