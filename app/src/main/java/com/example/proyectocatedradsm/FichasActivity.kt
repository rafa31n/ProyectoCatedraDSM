package com.example.proyectocatedradsm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocatedradsm.model.Fichas
import com.example.proyectocatedradsm.model.Tematicas

class FichasActivity : AppCompatActivity() {
    private var managerFichas: Fichas? = null
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fichas)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFichas)
        managerFichas= Fichas(this)
        val intent = intent
        val idTematica = intent.getStringExtra("tematica_id")
       /* managerFichas!!.addNewFicha(
            5,
            "Descripcion para SOCIALES",
            "#ADD8E6",
            "mar"
        )*/
        //Boton
        println("El id que nos dan = $idTematica")
        val cursor = managerFichas!!.searchFichasByTema(idTematica)
        if (cursor != null && cursor.moveToFirst()) {
            val fichas = mutableListOf<Ficha>()
            var idTematica: Int = 0
            var id_ficha: Int = 0
            var anverso: String = ""
            var enverso: String = ""
            var pistas: String = ""

            while (!cursor.isAfterLast) {
                // Obtener datos del cursor y establecerlos en las vistas
                idTematica = cursor.getInt(cursor.getColumnIndex("id_tematica"))
                id_ficha = cursor.getInt(cursor.getColumnIndex("id_ficha"))
                anverso = cursor.getString(cursor.getColumnIndex("anverso"))
                pistas = cursor.getString(cursor.getColumnIndex("pistas"))
                enverso = cursor.getString(cursor.getColumnIndex("enverso"))

                val ficha =
                    Ficha(
                        idTematica,
                        id_ficha,
                        anverso,
                        enverso,
                        pistas
                    )
                fichas.add(ficha)
                cursor.moveToNext()
            }
            recyclerView.layoutManager=LinearLayoutManager(this@FichasActivity)
            recyclerView.adapter = fichaAdapter(fichas)
            cursor.close()
        }else{
            Toast.makeText(
                this, "No se encontro ninguna ficha.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

