package com.example.pedidosdemo20.ReciclerView;

public class MyListEnCurso
{
    private int idPedido;
    private String fechaSolicitud;

    private String numeroPedido;
    private String numeroAlbaran;
    private String fechaPedido;
    private int pedidoFavorito;
    private String estado;

    private int numDosis;
    private String lineaGenetica;
    private String formato;

    public MyListEnCurso(int idPedido, String fechaSolicitud, String numeroPedido, String numeroAlbaran, String fechaPedido, int pedidoFavorito, String estado, int numDosis, String lineaGenetica, String formato) {
        this.idPedido = idPedido;
        this.fechaSolicitud = fechaSolicitud;
        this.numeroPedido = numeroPedido;
        this.numeroAlbaran = numeroAlbaran;
        this.fechaPedido = fechaPedido;
        this.pedidoFavorito = pedidoFavorito;
        this.estado = estado;
        this.numDosis = numDosis;
        this.lineaGenetica = lineaGenetica;
        this.formato = formato;
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

    public int getNumDosis() {
        return numDosis;
    }

    public void setNumDosis(int numDosis) {
        this.numDosis = numDosis;
    }

    public String getLineaGenetica() {
        return lineaGenetica;
    }

    public void setLineaGenetica(String lineaGenetica) {
        this.lineaGenetica = lineaGenetica;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}
