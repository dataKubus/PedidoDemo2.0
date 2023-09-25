package com.example.pedidosdemo20.BasesDatos;

public class PedidoFavorito
{
    private int id;
    private int idPedido;
    private String fechaSolicitud;

    private String numeroPedido;
    private String numeroAlbaran;
    private String fechaPedido;
    private int pedidoFavorito;
    private String estado;
    private String nevera;
    private String comentarios;

    public PedidoFavorito(int id, int idPedido, String fechaSolicitud, String numeroPedido, String numeroAlbaran, String fechaPedido, int pedidoFavorito, String estado, String nevera, String comentarios) {
        this.id = id;
        this.idPedido = idPedido;
        this.fechaSolicitud = fechaSolicitud;
        this.numeroPedido = numeroPedido;
        this.numeroAlbaran = numeroAlbaran;
        this.fechaPedido = fechaPedido;
        this.pedidoFavorito = pedidoFavorito;
        this.estado = estado;
        this.nevera = nevera;
        this.comentarios = comentarios;
    }

    public PedidoFavorito(int idPedido, String fechaSolicitud, String numeroPedido, String numeroAlbaran, String fechaPedido, int pedidoFavorito, String estado, String nevera, String comentarios)
    {
        this.idPedido = idPedido;
        this.fechaSolicitud = fechaSolicitud;
        this.numeroPedido = numeroPedido;
        this.numeroAlbaran = numeroAlbaran;
        this.fechaPedido = fechaPedido;
        this.pedidoFavorito = pedidoFavorito;
        this.estado = estado;
        this.nevera = nevera;
        this.comentarios = comentarios;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNevera() {
        return nevera;
    }

    public void setNevera(String nevera) {
        this.nevera = nevera;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
