package com.example.proyectocatedradsm.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.proyectocatedradsm.Tematica
import com.example.proyectocatedradsm.db.HelperDB

class Tematicas(context: Context?) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA TEMATICAS
        val TABLE_NAME_TEMATICAS = "tematicas"

        //nombre de los campos de la tabla tematicas
        val COL_ID = "id_tematica"
        val COL_NOMBRE = "nombre"
        val COL_DESCRIPCION = "descripcion"
        val COL_COLOR = "color"

        //sentencia SQL para crear la tabla.
        val CREATE_TABLE_TEMATICAS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TEMATICAS + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_NOMBRE + " varchar(255) NOT NULL,"
                        + COL_DESCRIPCION + " varchar(255) NOT NULL,"
                        + COL_COLOR + " varchar(255) NOT NULL);"
                )
    }

    // ContentValues
    fun generarContentValues(
        nombre: String?,
        descripcion: String?,
        color: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(Tematicas.COL_NOMBRE, nombre)
        valores.put(Tematicas.COL_DESCRIPCION, descripcion)
        valores.put(Tematicas.COL_COLOR, color)
        return valores
    }

    //Agregar un nuevo registro
    fun addNewTematica(
        nombre: String?,
        descripcion: String?,
        color: String?,
    ) {
        db!!.insert(
            TABLE_NAME_TEMATICAS,
            null,
            generarContentValues(nombre, descripcion, color)
        )
    }

    fun updateTematica(
        id_tematica: Int?,
        nombre: String?,
        descripcion: String?,
        color: String?
    ) {
        db!!.update(
            Tematicas.TABLE_NAME_TEMATICAS, generarContentValues(
                 nombre,
                descripcion, color
            ),
            "${Tematicas.COL_ID}=?", arrayOf(id_tematica.toString())
        )
    }

    // Eliminar un registro


    fun deleteTematica(id: String) {

        db!!.delete(TABLE_NAME_TEMATICAS, "$COL_ID=?", arrayOf(id))
        db!!.close()

    }

    //Modificar un registro
    /*fun updateProducto(
        id: Int,
        idcategoria: Int?,
        descripcion: String?,
        precio: Double?,
        cantidad: Int?
    ) {
        db!!.update(
            TABLE_NAME_PRODUCTOS, generarContentValues(
                idcategoria, descripcion,
                precio, cantidad
            ),
            "$COL_ID=?", arrayOf(id.toString())
        )
    }*/

    // Mostrar un registro particular
    fun searchTematica(nameTema: String): Cursor? {
        val columns = arrayOf(
            COL_ID, COL_NOMBRE, COL_DESCRIPCION, COL_COLOR,

        )
        return db!!.query(
            TABLE_NAME_TEMATICAS, columns,
            "$COL_ID=?", arrayOf(nameTema.toString()), null, null, null
        )
    }

    // Mostrar todos los registros
    fun searchTematicasAll(): Cursor? {
        val columns = arrayOf(
            COL_ID, COL_NOMBRE, COL_DESCRIPCION, COL_COLOR
        )
        return db!!.query(
            TABLE_NAME_TEMATICAS, columns,
            null, null, null, null, "${Tematicas.COL_NOMBRE} ASC"
        )
    }

}