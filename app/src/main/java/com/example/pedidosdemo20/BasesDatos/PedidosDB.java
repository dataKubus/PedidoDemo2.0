package com.example.pedidosdemo20.BasesDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PedidosDB extends SQLiteOpenHelper
{
    private SQLiteDatabase db;
    private static PedidosDB pedidosDB = null;

    public static PedidosDB getInstance(Context context)
    {
        if (pedidosDB == null)
        {
            pedidosDB = new PedidosDB(context);
        }
        return pedidosDB;
    }

    private PedidosDB(Context context)
    {
        super(context, "pedidos.db", null, 1);

        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table pedidoFavorito(id Integer primary key autoincrement, idPedido Integer)");
        db.execSQL("create table usuario(id Integer primary key autoincrement, idUsuarioAnimalSat Integer, nombreUsuario text, nombreGranja text, email text, rega text, telefono text, direccion text, nombreCiaSeleccionado text, idCiaSeleccionado text, idClienteSeleccionado Integer, idRutaSeleccionado Integer)");
        db.execSQL("create table ciaRegistrado(id Integer primary key autoincrement, idUsuario Integer, idCliente Integer, idRuta Integer, nombreCia text, idBSM text, nombreGranja text, direccionGranja text, telefonoGranja text)");
        db.execSQL("create table nevera(id Integer primary key autoincrement, nombre text, numSerie text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }

    public void insertarCiaGuardado(CiaRegistrado ciaGuardado)
    {
        ContentValues cv = new ContentValues();

        cv.put("idUsuario", ciaGuardado.getIdUsuario());
        cv.put("idCliente", ciaGuardado.getIdCliente());
        cv.put("idRuta", ciaGuardado.getIdRuta());
        cv.put("nombreCia", ciaGuardado.getNombreCia());
        cv.put("idBSM", ciaGuardado.getIdBSM());
        cv.put("nombreGranja", ciaGuardado.getNombreGranja());
        cv.put("direccionGranja", ciaGuardado.getDireccionGranja());
        cv.put("telefonoGranja", ciaGuardado.getTelefonoGranja());

        db.insert("ciaGuardado", null, cv);
    }

    public void insertarUsuario(Usuario usuario)
    {
        ContentValues cv = new ContentValues();

        cv.put("idUsuarioAnimalSat", usuario.getIdUsuarioAnimalSat());
        cv.put("nombreUsuario", usuario.getNombreUsuario());
        cv.put("nombreGranja", usuario.getNombreGranja());
        cv.put("email", usuario.getEmail());
        cv.put("rega", usuario.getRega());
        cv.put("telefono", usuario.getTelefono());
        cv.put("direccion", usuario.getDireccion());
        cv.put("nombreCiaSeleccionado", usuario.getNombreCiaSeleccionado());
        cv.put("idCiaSeleccionado", usuario.getIdCiaSeleccionado());
        cv.put("idClienteSeleccionado", usuario.getIdClienteSeleccionado());
        cv.put("idRutaSeleccionado", usuario.getIdRutaSeleccionado());

        db.insert("usuario", null, cv);
    }

    public String emailUser()
    {
        Cursor cursor = db.rawQuery("select email from usuario", null);
        String email = "";
        if (cursor.moveToFirst())
        {
            email = cursor.getString(0);
        }
        cursor.close();
        return email;
    }
    
    public void insertarPedidoFavorito(PedidoFavorito pedidoFavorito)
    {
        ContentValues cv = new ContentValues();

        cv.put("idPedido", pedidoFavorito.getIdPedido());

        db.insert("pedidoFavorito", null, cv);
    }

    public int strPedidoFav(int idPedido)
    {
        Cursor cursor = db.rawQuery("select idPedido from pedidoFavorito where idPedido = '" + idPedido + "'", null);
        int ped = 0;
        if (cursor.moveToFirst())
        {
            ped = cursor.getInt(0);
        }
        cursor.close();
        return ped;
    }

    public void elimiarPedidoFavorito(int idPdido)
    {
        db.execSQL("delete from pedidoFavorito where idPedido = '" + idPdido + "'");
    }

    public void insertarNevera(Nevera nev)
    {
        ContentValues cv = new ContentValues();

        cv.put("nombre", nev.getNombre());
        cv.put("numSerie", nev.getNumSerie());

        db.insert("nevera", null, cv);
    }
}
