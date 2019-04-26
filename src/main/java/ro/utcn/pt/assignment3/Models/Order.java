package ro.utcn.pt.assignment3.Models;

import java.util.ArrayList;

public class Order {

    public int order_id;
    public int client_id;
    public double totalSum;
    public int product_id;

    public Order() {
    }

    public Order(int order_id, int client_id, double totalSum, int product_id) {
        this.order_id = order_id;
        this.client_id = client_id;
        this.totalSum = totalSum;
        this.product_id = product_id;
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
