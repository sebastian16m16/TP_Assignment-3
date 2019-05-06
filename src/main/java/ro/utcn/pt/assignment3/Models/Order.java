package ro.utcn.pt.assignment3.Models;

import java.util.ArrayList;

public class Order {

    public int order_id;
    public int client_id;
    public String client_name;
    public double totalSum;
    public int product_id;
    public String product_name;
    public int quantity;

    public Order() {
    }

    public Order(int order_id, int client_id, String client_name, double totalSum, int product_id, String product_name, int quantity) {
        this.order_id = order_id;
        this.client_id = client_id;
        this.client_name = client_name;
        this.totalSum = totalSum;
        this.product_id = product_id;
        this.product_name = product_name;
        this.quantity = quantity;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
