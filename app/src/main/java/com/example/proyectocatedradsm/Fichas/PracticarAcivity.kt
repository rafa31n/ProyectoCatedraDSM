package com.example.proyectocatedradsm.Fichas

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectocatedradsm.Dashboard
import com.example.proyectocatedradsm.R
import com.example.proyectocatedradsm.db.HelperDB
import com.example.proyectocatedradsm.model.Fichas


class PracticarAcivity : AppCompatActivity() {
    private var managerFichas: Fichas? = null
    private var indiceActual: Int = 0
    private var fichas: MutableList<Ficha> = mutableListOf()
    private var fichasActual: Ficha? = null
    private var resultadosPorDefecto = HashMap<String, Int>()
    private var volveraintentar: Boolean = false


    @SuppressLint("Range", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practicar_acivity)

        //Defino variable
        val extras = intent?.extras
        var id_tematicaContex: Int? = extras?.getInt("tematica_id")
        managerFichas = Fichas(this)

        val textMostrarRespuesta = findViewById<TextView>(R.id.textMostrarRespuesta)
        val txtReverso = findViewById<TextView>(R.id.textReverso)
        val textAnverso = findViewById<TextView>(R.id.textAnverso)
        val divider = findViewById<View>(R.id.divider)
        val textOtravez = findViewById<TextView>(R.id.textOtravez)
        val textDificil = findViewById<TextView>(R.id.textDificil)
        val textBien = findViewById<TextView>(R.id.textBien)
        val textFacil = findViewById<TextView>(R.id.textFacils)
        val textmostrarPistas = findViewById<TextView>(R.id.textMostrarPista)
        val textPistas = findViewById<TextView>(R.id.textPistas)
        var linearL = findViewById<LinearLayout>(R.id.LinearLayout)
        var otravez: Boolean = false
        val btnRegresar = findViewById<ImageButton>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }


        val cursor = managerFichas!!.searchFichasAllForTematica(id_tematicaContex)
        if (cursor != null && cursor.moveToFirst()) {
            fichas = mutableListOf<Ficha>()
            var id_ficha: Int = 0
            var id_tematica: Int = 0
            var anverso_text: String = ""
            var reverso_text: String = ""
            var palabrasClave: String = ""

            while (!cursor.isAfterLast) {
                id_ficha = cursor.getInt(cursor.getColumnIndex("id_ficha"))
                id_tematica = cursor.getInt(cursor.getColumnIndex("id_tematica"))
                anverso_text = cursor.getString(cursor.getColumnIndex("anverso"))
                reverso_text = cursor.getString(cursor.getColumnIndex("reverso"))
                palabrasClave = cursor.getString(cursor.getColumnIndex("pistas"))

                val ficha =
                    Ficha(
                        id_ficha,
                        id_tematica,
                        anverso_text,
                        reverso_text,
                        palabrasClave,
                        ""
                    )
                fichas.add(ficha)
                cursor.moveToNext()
            }
            fichasActual = fichas[indiceActual]
            textAnverso.text = fichasActual?.anverso
            cursor.close()
        } else {
            Toast.makeText(
                this,
                "No hay fichas guardadas en esta tematica.", Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        Log.d("size", fichas.size.toString())

        textMostrarRespuesta.setOnClickListener {
            txtReverso.visibility = View.VISIBLE
            divider.visibility = View.VISIBLE
            txtReverso.text = fichasActual?.reverso
            textMostrarRespuesta.visibility = View.GONE
            textmostrarPistas.visibility = View.GONE

            linearL = findViewById<LinearLayout>(R.id.LinearLayout)
            linearL.visibility = View.VISIBLE
            if (indiceActual == 0 && !otravez) {
                indiceActual++
            }
            if (indiceActual >= fichas.size) {
                indiceActual = 0
                volveraintentar = true
            }
        }

        textOtravez.setOnClickListener {
            otravez = true
            textMostrarRespuesta.visibility = View.VISIBLE
            textmostrarPistas.visibility = View.VISIBLE
            textPistas.visibility = View.GONE
            linearL.visibility = View.GONE
            textAnverso.setText(fichasActual?.anverso)
            txtReverso.visibility = View.GONE
        }

        textFacil.setOnClickListener {
            //Para guardar los resultados
            capturarResultados("Fácil", fichasActual?.id_ficha)
            fichasActual = fichas[indiceActual]
            textMostrarRespuesta.visibility = View.VISIBLE
            textmostrarPistas.visibility = View.VISIBLE
            textPistas.visibility = View.GONE
            linearL.visibility = View.GONE
            textAnverso.setText(fichasActual?.anverso)
            txtReverso.visibility = View.GONE
            indiceActual++
            nuevoIntento(volveraintentar)

        }

        textBien.setOnClickListener {
            //Para guardar los resultados
            capturarResultados("Bien", fichasActual?.id_ficha)
            fichasActual = fichas[indiceActual]
            textMostrarRespuesta.visibility = View.VISIBLE
            textmostrarPistas.visibility = View.VISIBLE
            textPistas.visibility = View.GONE
            linearL.visibility = View.GONE
            textAnverso.setText(fichasActual?.anverso)
            txtReverso.visibility = View.GONE
            indiceActual++
            nuevoIntento(volveraintentar)
        }

        textDificil.setOnClickListener {
            //Para guardar los resultados
            capturarResultados("Dificil", fichasActual?.id_ficha)
            fichasActual = fichas[indiceActual]
            textMostrarRespuesta.visibility = View.VISIBLE
            textmostrarPistas.visibility = View.VISIBLE
            textPistas.visibility = View.GONE
            linearL.visibility = View.GONE
            textAnverso.setText(fichasActual?.anverso)
            txtReverso.visibility = View.GONE
            indiceActual++
            nuevoIntento(volveraintentar)
        }

        textmostrarPistas.setOnClickListener {
            textPistas.setText("Pistas: " + fichasActual?.pistas)
            textPistas.visibility = View.VISIBLE
        }
    }

    fun resultadosPorDefectoFun() {
        resultadosPorDefecto.put("bien", 0)
        resultadosPorDefecto.put("facil", 0)
        resultadosPorDefecto.put("dificil", 0)
    }

    fun nuevoIntento(intento: Boolean) {

        if (intento) {
            volverAIntentar()
            volveraintentar = false
        }
    }

    fun volverAIntentar() {
        AlertDialog.Builder(this)
            .setTitle("PONTE A PRUEBA")
            .setMessage("¿Quieres volver a intentarlo?")
            .setPositiveButton("Volver a intentar") { dialog, which ->
                resultadosPorDefectoFun()
                dialog.dismiss()
            }
            .setNegativeButton("Salir") { dialog, which ->
                // No hacer nada, simplemente cerrar el diálogo
                dialog.dismiss()
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
            }
            .show()
    }

    fun capturarResultados(tipo: String, id_ficha: Int?) {

        Log.d("ficha", fichasActual?.anverso.toString())
        managerFichas!!.updateFichaResultados(
            id_ficha,
            tipo
        )

        /*val entries = resultadosPorDefecto.entries
        for (entry in entries) {
            if (entry.key.equals(tipo)) {
                val valor = entry.value
                resultadosPorDefecto.put(tipo, valor + 1)
            }
        }
        return resultadosPorDefecto;*/
    }
}