package com.example.pedidosdemo20.BasesDatos;

public class PedidoFavorito
{
    private int id;
    private int idPedido;

    public PedidoFavorito(int id, int idPedido)
    {
        this.id = id;
        this.idPedido = idPedido;
    }

    public PedidoFavorito(int idPedido)
    {
        this.idPedido = idPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
}
