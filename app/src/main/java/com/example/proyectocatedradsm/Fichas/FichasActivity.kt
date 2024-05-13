package com.example.proyectocatedradsm.Fichas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocatedradsm.Dashboard
import com.example.proyectocatedradsm.R
import com.example.proyectocatedradsm.model.Fichas

class FichasActivity : AppCompatActivity() {
    private var managerFichas: Fichas? = null

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fichas)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFichas)

        managerFichas = Fichas(this)
        val intent = intent
        val idTematica = intent.getStringExtra("tematica_id")
        val btnNuevaFicha = findViewById<Button>(R.id.btnCrearFicha)

        btnNuevaFicha.setOnClickListener {
            val intent = Intent(this, CrearFicha::class.java)
            intent.putExtra("idTematica", idTematica)
            startActivity(intent)
        }

        val cursor = managerFichas!!.searchFichasByTema(idTematica)
        if (cursor != null && cursor.moveToFirst()) {
            val fichas = mutableListOf<Ficha>()
            var idTematica: Int = 0
            var id_ficha: Int = 0
            var anverso: String = ""
            var reverso: String = ""
            var pistas: String = ""

            while (!cursor.isAfterLast) {
                // Obtener datos del cursor y establecerlos en las vistas
                idTematica = cursor.getInt(cursor.getColumnIndex("id_tematica"))
                id_ficha = cursor.getInt(cursor.getColumnIndex("id_ficha"))
                anverso = cursor.getString(cursor.getColumnIndex("anverso"))
                pistas = cursor.getString(cursor.getColumnIndex("pistas"))
                reverso = cursor.getString(cursor.getColumnIndex("reverso"))

                val ficha =
                    Ficha(
                        id_ficha,
                        idTematica,
                        anverso,
                        reverso,
                        pistas
                    )
                fichas.add(ficha)
                cursor.moveToNext()
            }
            recyclerView.layoutManager = LinearLayoutManager(this@FichasActivity)
            recyclerView.adapter = FichaAdapter(fichas)
            cursor.close()
        } else {
            val txtMensaje = findViewById<TextView>(R.id.txtMensaje)
            txtMensaje.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }
}

