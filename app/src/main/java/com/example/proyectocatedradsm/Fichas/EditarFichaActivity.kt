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

class EditarFichaActivity : AppCompatActivity() {
    //Declarar variables
    private var managerFichas: Fichas? = null

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_ficha)
        managerFichas = Fichas(this)

        val txtAnverso = findViewById<EditText>(R.id.editTextAnverso)
        val txtReverso = findViewById<EditText>(R.id.editTextEnverso)
        val txtPista = findViewById<EditText>(R.id.editTextPistas)

        val btnGuardar = findViewById<Button>(R.id.buttonGuardar)
        val btnCancelar = findViewById<Button>(R.id.buttonCancelar)
        val btnRegresar = findViewById<ImageButton>(R.id.btnRegresar)

        // Obtener parametros intent
        val intent = intent
        val idFicha = intent.getStringExtra("idFicha")
        val idTematicaFicha = intent.getStringExtra("idTematicaFicha")

        val anverso = intent.getStringExtra("anverso")
        val reverso = intent.getStringExtra("reverso")
        val pistas = intent.getStringExtra("pistas")
        val resultado = intent.getStringExtra("resultado")

        txtAnverso.setText(anverso)
        txtReverso.setText(reverso)
        txtPista.setText(pistas)

        btnGuardar.setOnClickListener {
            if (idFicha != null) {
                if (idTematicaFicha != null) {
                    managerFichas!!.updateFicha(
                        idFicha.toInt(),
                        idTematicaFicha.toInt(),
                        txtAnverso.text.toString(),
                        txtReverso.text.toString(),
                        txtPista.text.toString(),
                        resultado?.replace("Resultado: ", "")
                    )
                }
                Toast.makeText(
                    this, "Cambios guardados correctamente.",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, FichasActivity::class.java)
                intent.putExtra("tematica_id", idTematicaFicha)
                startActivity(intent)
            }
        }

        btnCancelar.setOnClickListener {
            val intent = Intent(this, FichasActivity::class.java)
            intent.putExtra("tematica_id", idTematicaFicha)
            startActivity(intent)
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, FichasActivity::class.java)
            intent.putExtra("tematica_id", idTematicaFicha)
            startActivity(intent)
        }
    }
}