package com.example.proyectocatedradsm.Fichas

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectocatedradsm.Dashboard
import com.example.proyectocatedradsm.R
import com.example.proyectocatedradsm.db.HelperDB
import com.example.proyectocatedradsm.model.Fichas

class CrearFicha : AppCompatActivity() {
    private var managerFichas: Fichas? = null

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_fichas)

        val intent = intent
        val idTematica = intent.getStringExtra("idTematica")
        managerFichas = Fichas(this)

        var validarText: Boolean = false;
        val txtAnverso = findViewById<EditText>(R.id.textAnverso)
        val txtEnverso = findViewById<EditText>(R.id.textEnverso)
        val txtClavesPistas = findViewById<EditText>(R.id.textClavesPistas)
        val btnGuardar = findViewById<Button>(R.id.btnCrearFicha)
        val btnCancelar = findViewById<Button>(R.id.btnCancerlarFicha)
        val btnRegresar = findViewById<ImageButton>(R.id.btnRegresar)

        btnGuardar.setOnClickListener {
            if (funValidarText(txtAnverso.text.toString()) &&
                funValidarText(txtEnverso.text.toString()) &&
                funValidarText(
                    txtClavesPistas.text.toString()
                )
            ) {
                validarText = true;
            }

            if (validarText) {
                managerFichas!!.addNewFicha(
                    idTematica?.toInt(),
                    txtAnverso.text.toString(),
                    txtEnverso.text.toString(),
                    txtClavesPistas.text.toString()
                )
                Toast.makeText(
                    this, "Ficha creada correctamente",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, FichasActivity::class.java)
                intent.putExtra("tematica_id", idTematica)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this, "Ingrese todos los valores",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnCancelar.setOnClickListener {
            val intent = Intent(this, FichasActivity::class.java)
            intent.putExtra("tematica_id", idTematica)
            startActivity(intent)
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, FichasActivity::class.java)
            intent.putExtra("tematica_id", idTematica)
            startActivity(intent)
        }
    }

    fun funValidarText(text: String): Boolean {
        return !text.isEmpty()
    }
}