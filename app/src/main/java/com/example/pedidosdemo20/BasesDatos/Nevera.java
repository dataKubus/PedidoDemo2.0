package com.example.pedidosdemo20.BasesDatos;

public class Nevera
{
    private int id;
    private String nombre;
    private String numSerie;

    public Nevera(int id, String nombre, String numSerie)
    {
        this.id = id;
        this.nombre = nombre;
        this.numSerie = numSerie;
    }

    public Nevera(String nombre, String numSerie)
    {
        this.nombre = nombre;
        this.numSerie = numSerie;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNumSerie()
    {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }
}
