package com.example.pedidosdemo20.ReciclerView;

import java.util.List;

public class MyList
{
    private int idPedido;
    private String fechaSolicitud;

    private String numeroPedido;
    private String numeroAlbaran;
    private String fechaPedido;
    private int pedidoFavorito;


    public MyList(int idPedido, String fechaSolicitud, String numeroPedido, String numeroAlbaran, String fechaPedido, int pedidoFavorito) {
        this.idPedido = idPedido;
        this.fechaSolicitud = fechaSolicitud;
        this.numeroPedido = numeroPedido;
        this.numeroAlbaran = numeroAlbaran;
        this.fechaPedido = fechaPedido;
        this.pedidoFavorito = pedidoFavorito;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getNumeroAlbaran() {
        return numeroAlbaran;
    }

    public void setNumeroAlbaran(String numeroAlbaran) {
        this.numeroAlbaran = numeroAlbaran;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public int getPedidoFavorito() {
        return pedidoFavorito;
    }

    public void setPedidoFavorito(int pedidoFavorito) {
        this.pedidoFavorito = pedidoFavorito;
    }

}
