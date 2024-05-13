package com.example.proyectocatedradsm

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocatedradsm.Tematicas.Tematica
import com.example.proyectocatedradsm.Tematicas.TematicaAdapter
import com.example.proyectocatedradsm.Tematicas.TematicaAnadir
import com.example.proyectocatedradsm.db.HelperDB
import com.example.proyectocatedradsm.model.Tematicas

class Dashboard : AppCompatActivity() {
    //Declarar variables
    private var managerTematicas: Tematicas? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTematicas)
        managerTematicas = Tematicas(this)

        //Boton
        val btnCrearTematica=findViewById<Button>(R.id.btnCrearTematica)
        btnCrearTematica.setOnClickListener {
            val intent = Intent(this, TematicaAnadir::class.java)
            startActivity(intent)
        }

        // MOSTRAR TEMATICAS
        val cursor = managerTematicas!!.searchTematicasAll()
        if (cursor != null && cursor.moveToFirst()) {
            val tematicas = mutableListOf<Tematica>()
            var idTematica: Int = 0
            var nombreTematica: String = ""
            var descripcionTematica: String = ""
            var colorTematica: String = ""

            while (!cursor.isAfterLast) {
                // Obtener datos del cursor y establecerlos en las vistas
                idTematica = cursor.getInt(cursor.getColumnIndex("id_tematica"))
                nombreTematica = cursor.getString(cursor.getColumnIndex("nombre"))
                descripcionTematica = cursor.getString(cursor.getColumnIndex("descripcion"))
                colorTematica = cursor.getString(cursor.getColumnIndex("color"))

                val tematica =
                    Tematica(
                        idTematica,
                        nombreTematica,
                        descripcionTematica,
                        colorTematica
                    )
                tematicas.add(tematica)
                cursor.moveToNext()
            }
            recyclerView.layoutManager = LinearLayoutManager(this@Dashboard)
            recyclerView.adapter = TematicaAdapter(tematicas, colorTematica)
            cursor.close()
        } else {
            val txtMensaje = findViewById<TextView>(R.id.txtMensaje)
            txtMensaje.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }
}