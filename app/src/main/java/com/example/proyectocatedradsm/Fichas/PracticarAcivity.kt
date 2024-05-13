package com.example.proyectocatedradsm.Fichas

import android.annotation.SuppressLint
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

        //resultadosPorDefecto()

        val cursor = managerFichas!!.searchFichasAllForTematica(id_tematicaContex)
        if (cursor != null && cursor.moveToFirst()) {
            fichas = mutableListOf<Ficha>()
            var id_ficha: Int = 0
            var id_tematica: Int = 0
            var anverso_text: String = ""
            var reverso_text: String = ""
            var palabrasClave: String = ""

            while (!cursor.isAfterLast) {
                id_ficha = cursor.getInt(cursor.getColumnIndex("id_tematica"))
                id_tematica = cursor.getInt(cursor.getColumnIndex("id_ficha"))
                anverso_text = cursor.getString(cursor.getColumnIndex("anverso"))
                reverso_text = cursor.getString(cursor.getColumnIndex("reverso"))
                palabrasClave = cursor.getString(cursor.getColumnIndex("pistas"))

                val ficha =
                    Ficha(
                        id_ficha,
                        id_tematica,
                        anverso_text,
                        reverso_text,
                        palabrasClave
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
                "NO HAY FICHAS EN LA TEMATICA ", Toast.LENGTH_SHORT
            ).show()
        }

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
                indiceActual = 0;
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
            fichasActual = fichas[indiceActual]
            textMostrarRespuesta.visibility = View.VISIBLE
            textmostrarPistas.visibility = View.VISIBLE
            textPistas.visibility = View.GONE
            linearL.visibility = View.GONE
            textAnverso.setText(fichasActual?.anverso)
            txtReverso.visibility = View.GONE
            indiceActual++
            //Para guardar los resultados
            capturarResultados("facil")
        }

        textBien.setOnClickListener {
            fichasActual = fichas[indiceActual]
            textMostrarRespuesta.visibility = View.VISIBLE
            textmostrarPistas.visibility = View.VISIBLE
            textPistas.visibility = View.GONE
            linearL.visibility = View.GONE
            textAnverso.setText(fichasActual?.anverso)
            txtReverso.visibility = View.GONE
            indiceActual++
            //Para guardar los resultados
            capturarResultados("bien")
        }

        textDificil.setOnClickListener {
            fichasActual = fichas[indiceActual]
            textMostrarRespuesta.visibility = View.VISIBLE
            textmostrarPistas.visibility = View.VISIBLE
            textPistas.visibility = View.GONE
            linearL.visibility = View.GONE
            textAnverso.setText(fichasActual?.anverso)
            txtReverso.visibility = View.GONE
            indiceActual++
            //Para guardar los resultados
            capturarResultados("dificil")
        }

        textmostrarPistas.setOnClickListener {
            textPistas.setText("Pistas: " + fichasActual?.pistas)
            textPistas.visibility = View.VISIBLE
        }
    }

    fun resultadosPorDefecto() {
        resultadosPorDefecto.put("bien", 0)
        resultadosPorDefecto.put("facil", 0)
        resultadosPorDefecto.put("dificil", 0)
    }

    fun capturarResultados(tipo: String): HashMap<String, Int> {
        val entries = resultadosPorDefecto.entries
        for (entry in entries) {
            if (entry.key.equals(tipo)) {
                val valor = entry.value
                resultadosPorDefecto.put(tipo, valor + 1)
            }
        }
        return resultadosPorDefecto;
    }
}