package br.com.wilson.helloworldturbo.models;

import java.io.Serializable;

public class Pedido implements Serializable {
    private int orderId;
    private String dataPedido;

    @Override
    public String toString() {
        return "Pedido: " + this.orderId + " - Data: " +
                this.dataPedido.replace("T", " ");
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }
}
