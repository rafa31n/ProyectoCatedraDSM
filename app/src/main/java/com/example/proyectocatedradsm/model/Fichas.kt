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
        val COL_REVERSO = "reverso"
        val COL_PISTAS = "pistas"
        val COL_RESULTADO = "resultado"


        //sentencia SQL para crear la tabla.
        val CREATE_TABLE_FICHAS = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FICHAS + "("
                        + COL_ID + " integer primary key autoincrement,"
                        + COL_IDTEMATICA + " integer NOT NULL,"
                        + COL_ANVERSO + " varchar(255) NOT NULL,"
                        + COL_REVERSO + " varchar(255) NOT NULL,"
                        + COL_PISTAS + " varchar(255) NOT NULL,"
                        + COL_RESULTADO + " integer NULL,"
                        + "FOREIGN KEY(id_tematica) REFERENCES tematicas (id_tematica));"
                )
    }

    // ContentValues
    fun generarContentValues(
        id_tematica: Int?,
        anverso: String?,
        reverso: String?,
        pistas: String?
    ): ContentValues? {
        val valores = ContentValues()
        valores.put(Fichas.COL_IDTEMATICA, id_tematica)
        valores.put(Fichas.COL_ANVERSO, anverso)
        valores.put(Fichas.COL_REVERSO, reverso)
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
        reverso: String?,
        pistas: String?
    ) {
        db!!.insert(
            TABLE_NAME_FICHAS,
            null,
            generarContentValues(id_tematica, anverso, reverso, pistas)
        )
    }

    // Eliminar un registro
    fun deleteFicha(id: String) {
        db!!.delete(TABLE_NAME_FICHAS, "$COL_ID=?", arrayOf(id))
        db!!.close()
    }

    // Eliminar todas las fichas de una tematica
    fun eliminarFichasTematica(id: Int) {
        db!!.delete(TABLE_NAME_FICHAS, "$COL_IDTEMATICA=?", arrayOf(id.toString()))
        db!!.close()
    }

    //Modificar un registro
    fun updateFicha(
        id_ficha: Int,
        id_tematica: Int?,
        anverso: String?,
        reverso: String?,
        pistas: String?
    ) {
        db!!.update(
            TABLE_NAME_FICHAS, generarContentValues(
                id_tematica,
                anverso,
                reverso,
                pistas
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

    // Mostrar todos los registros
    fun searchFichasAll(): Cursor? {
        val columns = arrayOf(
            COL_ID, COL_IDTEMATICA, COL_ANVERSO, COL_REVERSO, COL_PISTAS
        )
        return db!!.query(
            TABLE_NAME_FICHAS, columns,
            null, null, null, null, "${Fichas.COL_ANVERSO} ASC"
        )
    }

    fun searchFichasByTema(idTematica: String?): Cursor? {
        val columns = arrayOf(COL_ID, COL_IDTEMATICA, COL_ANVERSO, COL_REVERSO, COL_PISTAS)
        val selection = "$COL_IDTEMATICA = ?"
        val selectionArgs = arrayOf(idTematica.toString())
        return db!!.query(
            TABLE_NAME_FICHAS, columns,
            selection, selectionArgs, null, null,
            "${Fichas.COL_ANVERSO} ASC"
        )
    }

    fun searchFichasAllForTematica(id: Int?): Cursor? {
        val columns = arrayOf(
            COL_ID, COL_IDTEMATICA, COL_ANVERSO, COL_REVERSO, COL_PISTAS
        )
        return db!!.query(
            TABLE_NAME_FICHAS, columns,
            "$COL_IDTEMATICA=?",
            arrayOf(id.toString()), null, null,
            "${Fichas.COL_ID} DESC"
        )
    }

}