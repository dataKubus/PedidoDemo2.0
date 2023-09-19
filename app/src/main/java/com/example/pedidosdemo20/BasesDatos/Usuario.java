package com.example.pedidosdemo20.BasesDatos;

public class Usuario
{
    private int id;
    private int idUsuarioAnimalSat;
    private String nombreUsuario;
    private String nombreGranja;
    private String email;
    private String rega;
    private String telefono;
    private String direccion;
    private String nombreCiaSeleccionado;
    private String idCiaSeleccionado;
    private int idClienteSeleccionado;
    private int idRutaSeleccionado;

    public Usuario(int id, int idUsuarioAnimalSat,  String nombreUsuario, String nombreGranja, String email, String rega, String telefono, String direccion, String nombreCiaSeleccionado, String idCiaSeleccionado, int idClienteSeleccionado, int idRutaSeleccionado) {
        this.id = id;
        this.idUsuarioAnimalSat = idUsuarioAnimalSat;
        this.nombreUsuario = nombreUsuario;
        this.nombreGranja = nombreGranja;
        this.email = email;
        this.rega = rega;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombreCiaSeleccionado = nombreCiaSeleccionado;
        this.idCiaSeleccionado = idCiaSeleccionado;
        this.idClienteSeleccionado = idClienteSeleccionado;
        this.idRutaSeleccionado = idRutaSeleccionado;
    }

    public Usuario(int idUsuarioAnimalSat, String nombreGranja,  String nombreUsuario, String email, String rega, String telefono, String direccion, String nombreCiaSeleccionado, String idCiaSeleccionado, int idClienteSeleccionado, int idRutaSeleccionado) {
        this.idUsuarioAnimalSat = idUsuarioAnimalSat;
        this.nombreUsuario = nombreUsuario;
        this.nombreGranja = nombreGranja;
        this.email = email;
        this.rega = rega;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombreCiaSeleccionado = nombreCiaSeleccionado;
        this.idCiaSeleccionado = idCiaSeleccionado;
        this.idClienteSeleccionado = idClienteSeleccionado;
        this.idRutaSeleccionado = idRutaSeleccionado;
    }

    public Usuario()
    {

    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIdUsuarioAnimalSat()
    {
        return idUsuarioAnimalSat;
    }

    public void setIdUsuarioAnimalSat(int idUsuarioAnimalSat)
    {
        this.idUsuarioAnimalSat = idUsuarioAnimalSat;
    }

    public String getNombreUsuario()
    {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario)
    {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreGranja() {
        return nombreGranja;
    }

    public void setNombreGranja(String nombreGranja) {
        this.nombreGranja = nombreGranja;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRega() {
        return rega;
    }

    public void setRega(String rega) {
        this.rega = rega;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreCiaSeleccionado() {
        return nombreCiaSeleccionado;
    }

    public void setNombreCiaSeleccionado(String nombreCiaSeleccionado) {
        this.nombreCiaSeleccionado = nombreCiaSeleccionado;
    }

    public String getIdCiaSeleccionado() {
        return idCiaSeleccionado;
    }

    public void setIdCiaSeleccionado(String idCiaSeleccionado) {
        this.idCiaSeleccionado = idCiaSeleccionado;
    }

    public int getIdClienteSeleccionado() {
        return idClienteSeleccionado;
    }

    public void setIdClienteSeleccionado(int idClienteSeleccionado) {
        this.idClienteSeleccionado = idClienteSeleccionado;
    }

    public int getIdRutaSeleccionado() {
        return idRutaSeleccionado;
    }

    public void setIdRutaSeleccionado(int idRutaSeleccionado) {
        this.idRutaSeleccionado = idRutaSeleccionado;
    }
}
