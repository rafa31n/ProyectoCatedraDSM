package com.example.proyectocatedradsm.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.proyectocatedradsm.db.HelperDB

class Fichas(context: Context?) {
    private var helper: HelperDB? = null
    private var db: SQLiteDatabase? = null

    init {
        helper = HelperDB(context)
        db = helper!!.getWritableDatabase()
    }

    companion object {
        //TABLA TEMATICAS
        val TABLE_NAME_FICHAS = "fichas"

        //nombre de los campos de la tabla tematicas
        val COL_ID = "id_ficha"
        val COL_IDTEMATICA = "id_tematica"
        val COL_ANVERSO = "anverso"
        val COL_ENVERSO = "enverso"
        val COL_PISTAS = "pistas"
        val COL_REGISTRO = "resultados"


        //sentencia SQL para crear la tabla.
        val CREATE_TABLE_FICHAS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FICHAS + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_IDTEMATICA + " integer NOT NULL,"
                        + COL_ANVERSO + " varchar(255) NOT NULL,"
                        + COL_ENVERSO + " varchar(255) NOT NULL,"
                        + COL_PISTAS + " varchar(255) NOT NULL,"
                        + COL_REGISTRO +" varchar(255) NULL,"
                        + "FOREIGN KEY(id_tematica) REFERENCES tematicas (id_tematica));"
                )
    }

    // ContentValues
    fun generarContentValues(
        id_tematica: Int?,
        anverso: String?,
        enverso: String?,
        pistas: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(Fichas.COL_IDTEMATICA, id_tematica)
        valores.put(Fichas.COL_ANVERSO, anverso)
        valores.put(Fichas.COL_ENVERSO, enverso)
        valores.put(Fichas.COL_PISTAS, pistas)
        return valores
    }

    fun generarContentValuesRegistro(
        registro: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(Fichas.COL_IDTEMATICA, registro)
        return valores
    }

    //Agregar un nuevo registro
    fun addNewFicha(
        id_tematica: Int?,
        anverso: String?,
        enverso: String?,
        pistas: String?
    ) {
        db!!.insert(
            TABLE_NAME_FICHAS,
            null,
            generarContentValues(id_tematica, anverso, enverso, pistas)
        )
    }

    // Eliminar un registro
    /*fun deleteProducto(id: Int) {
        db!!.delete(TABLE_NAME_PRODUCTOS, "$COL_ID=?", arrayOf(id.toString()))
    }*/

    //Modificar un registro
    fun updateFicha(
        id_ficha: Int,
        id_tematica: Int?,
        anverso: String?,
        enverso: String?,
        pistas: String?
    ) {
        db!!.update(
            TABLE_NAME_FICHAS, generarContentValues(
                id_tematica, anverso,
                enverso, pistas
            ),
            "$COL_ID=?", arrayOf(id_ficha.toString())
        )
    }

    fun updateFichaResultados(
        id_ficha: Int,
        registros: String?

    ) {
        db!!.update(
            TABLE_NAME_FICHAS, generarContentValuesRegistro(
                registros
            ),
            "$COL_ID=?", arrayOf(id_ficha.toString())
        )
    }

    // Mostrar un registro particular
    /*fun searchProducto(id: Int): Cursor? {
        val columns = arrayOf(
            COL_ID, COL_IDCATEGORIA, COL_DESCRIPCION, COL_PRECIO,
            COL_CANTIDAD
        )
        return db!!.query(
            TABLE_NAME_PRODUCTOS, columns,
            "$COL_ID=?", arrayOf(id.toString()), null, null, null
        )
    }*/

    // Mostrar todos los registros
    fun searchFichasAll(): Cursor? {
        val columns = arrayOf(
            COL_ID, COL_IDTEMATICA, COL_ANVERSO, COL_ENVERSO, COL_PISTAS
        )
        return db!!.query(
            TABLE_NAME_FICHAS, columns,
            null, null, null, null, "${Fichas.COL_ANVERSO} ASC"
        )
    }

    fun searchFichasAllForTematica(id: Int?): Cursor? {
        val columns = arrayOf(
            COL_ID, COL_IDTEMATICA, COL_ANVERSO, COL_ENVERSO, COL_PISTAS
        )
        return db!!.query(
            TABLE_NAME_FICHAS, columns,
            "$COL_IDTEMATICA=?", arrayOf(id.toString()), null, null, "${Fichas.COL_ID} DESC"
        )
    }
}