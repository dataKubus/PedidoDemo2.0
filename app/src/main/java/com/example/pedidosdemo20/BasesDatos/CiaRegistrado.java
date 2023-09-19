package com.example.pedidosdemo20.BasesDatos;

public class CiaRegistrado
{
    private int id;
    private int idUsuario;
    private int idCliente;
    private int idRuta;
    private String nombreCia;
    private String idBSM;
    private String nombreGranja;
    private String direccionGranja;
    private String telefonoGranja;

    public CiaRegistrado(int id, int idUsuario, int idCliente, int idRuta, String nombreCia, String idBSM, String nombreGranja, String direccionGranja, String telefonoGranja)
    {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        this.idRuta = idRuta;
        this.nombreCia = nombreCia;
        this.idBSM = idBSM;
        this.nombreGranja = nombreGranja;
        this.direccionGranja = direccionGranja;
        this.telefonoGranja = telefonoGranja;
    }

    public CiaRegistrado(int idUsuario, int idCliente, int idRuta, String nombreCia, String idBSM, String nombreGranja, String direccionGranja, String telefonoGranja)
    {
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        this.idRuta = idRuta;
        this.nombreCia = nombreCia;
        this.idBSM = idBSM;
        this.nombreGranja = nombreGranja;
        this.direccionGranja = direccionGranja;
        this.telefonoGranja = telefonoGranja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdRuta() {
        return idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getNombreCia() {
        return nombreCia;
    }

    public void setNombreCia(String nombreCia) {
        this.nombreCia = nombreCia;
    }

    public String getIdBSM() {
        return idBSM;
    }

    public void setIdBSM(String idBSM) {
        this.idBSM = idBSM;
    }

    public String getNombreGranja() {
        return nombreGranja;
    }

    public void setNombreGranja(String nombreGranja) {
        this.nombreGranja = nombreGranja;
    }

    public String getDireccionGranja() {
        return direccionGranja;
    }

    public void setDireccionGranja(String direccionGranja) {
        this.direccionGranja = direccionGranja;
    }

    public String getTelefonoGranja() {
        return telefonoGranja;
    }

    public void setTelefonoGranja(String telefonoGranja) {
        this.telefonoGranja = telefonoGranja;
    }
}
