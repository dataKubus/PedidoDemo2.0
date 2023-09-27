package com.example.pedidosdemo20.BasesDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

import java.util.ArrayList;

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
        db.execSQL("create table pedidoFavorito(id Integer primary key autoincrement, idPedido Integer, fechaSolicitud text, numeroPedido text, numeroAlbaran text, fechaPedido text, pedidoFavorito Integer, estado text, nevera text, comentarios text)");
        db.execSQL("create table usuario(id Integer primary key autoincrement, idUsuarioAnimalSat Integer, nombreUsuario text, nombreGranja text, email text, rega text, telefono text, direccion text, nombreCiaSeleccionado text, idCiaSeleccionado text, urlFotoPerfil text, idClienteSeleccionado Integer, idRutaSeleccionado Integer)");
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

        db.insert("ciaRegistrado", null, cv);
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
        cv.put("urlFotoPerfil", usuario.getUrlFotoPerfil());
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
        cv.put("fechaSolicitud", pedidoFavorito.getFechaSolicitud());
        cv.put("numeroPedido", pedidoFavorito.getNumeroPedido());
        cv.put("numeroAlbaran", pedidoFavorito.getNumeroAlbaran());
        cv.put("fechaPedido", pedidoFavorito.getFechaPedido());
        cv.put("pedidoFavorito", pedidoFavorito.getPedidoFavorito());
        cv.put("estado", pedidoFavorito.getEstado());
        cv.put("nevera", pedidoFavorito.getNevera());
        cv.put("comentarios", pedidoFavorito.getComentarios());

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

    public String neveraSeleccionada()
    {
        Cursor cursor = db.rawQuery("select nombre from nevera", null);
        String nev = "";
        if (cursor.moveToFirst())
        {
            nev = cursor.getString(0);
        }
        cursor.close();
        return nev;
    }

    public ArrayList<Usuario> getUsuario()
    {
        Cursor cursor = db.rawQuery("Select id, idUsuarioAnimalSat, nombreUsuario, nombreGranja, email, rega, telefono, direccion, nombreCiaSeleccionado, idCiaSeleccionado, urlFotoPerfil, idClienteSeleccionado, idRutaSeleccionado" + " from usuario", null);
        ArrayList<Usuario> arUser = new ArrayList<>();
        Usuario usuario;
        if (cursor.moveToFirst())
        {
            do {
                usuario = new Usuario();

                usuario.setId(cursor.getInt(0));
                usuario.setIdUsuarioAnimalSat(cursor.getInt(1));
                usuario.setNombreUsuario(cursor.getString(2));
                usuario.setNombreGranja(cursor.getString(3));
                usuario.setEmail(cursor.getString(4));
                usuario.setRega(cursor.getString(5));
                usuario.setTelefono(cursor.getString(6));
                usuario.setDireccion(cursor.getString(7));
                usuario.setNombreCiaSeleccionado(cursor.getString(8));
                usuario.setIdCiaSeleccionado(cursor.getString(9));
                usuario.setUrlFotoPerfil(cursor.getString(10));
                usuario.setIdClienteSeleccionado(cursor.getInt(11));
                usuario.setIdRutaSeleccionado(cursor.getInt(12));

                arUser.add(usuario);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return arUser;
    }

    public ArrayList<PedidoFavorito> getPedidosFavoritos()
    {
        Cursor cursor = db.rawQuery("Select id, idPedido, fechaSolicitud, numeroPedido, numeroAlbaran, fechaPedido, pedidoFavorito, estado, nevera, comentarios " + " from pedidoFavorito", null);
        ArrayList<PedidoFavorito> arPed = new ArrayList<>();
        PedidoFavorito pedidoFavorito;
        if (cursor.moveToFirst())
        {
            do {
                pedidoFavorito = new PedidoFavorito();

                pedidoFavorito.setId(cursor.getInt(0));
                pedidoFavorito.setIdPedido(cursor.getInt(1));
                pedidoFavorito.setFechaSolicitud(cursor.getString(2));
                pedidoFavorito.setNumeroPedido(cursor.getString(3));
                pedidoFavorito.setNumeroAlbaran(cursor.getString(4));
                pedidoFavorito.setFechaPedido(cursor.getString(5));
                pedidoFavorito.setPedidoFavorito(cursor.getInt(6));
                pedidoFavorito.setEstado(cursor.getString(7));
                pedidoFavorito.setNevera(cursor.getString(8));
                pedidoFavorito.setComentarios(cursor.getString(9));

                arPed.add(pedidoFavorito);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return arPed;
    }

    public void actualizarfotoPerfil(String url)
    {
        db.execSQL("update Usuario set urlFotoPerfil = '" + url + "'");
    }
}
