package com.example.proyectocatedradsm

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectocatedradsm.db.HelperDB
import com.example.proyectocatedradsm.model.Fichas

class EditarFichaActivity : AppCompatActivity() {
    //Declarar variables
    private var managerFichas: Fichas? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null
    var idFicha: Int = 0
    var idTematica_Ficha: Int = 0

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_editar_ficha)
        managerFichas = Fichas(this)

        val txtAnverso = findViewById<EditText>(R.id.editTextAnverso)
        val txtEnverso = findViewById<EditText>(R.id.editTextEnverso)
        val txtPista = findViewById<EditText>(R.id.editTextPistas)

        val btnGuardar = findViewById<Button>(R.id.buttonGuardar)
        val btnCancelar = findViewById<Button>(R.id.buttonCancelar)

        btnGuardar.setOnClickListener {
            managerFichas!!.updateFicha(
                idFicha,
                idTematica_Ficha,
                txtAnverso.text.toString(),
                txtEnverso.text.toString(),
                txtPista.text.toString()
            )
            Toast.makeText(
                this, "Cambios guardados correctamente.",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, Dashboard::class.java)//Cambiar a vista correspondiente
            startActivity(intent)
        }

        btnCancelar.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)//Cambiar a vista correspondiente
            startActivity(intent)
        }

        //
        /*managerFichas!!.addNewFicha(
            1,
            "PREGUNTA aa",
            "RESPUESTA aa",
            "PISTA aa"
        )*/

        // MOSTRAR FICHAS
        val cursor = managerFichas!!.searchFichasAll()
        if (cursor != null && cursor.moveToFirst()) {
            var anverso: String = ""
            var enverso: String = ""
            var pista: String = ""

            anverso = cursor.getString(cursor.getColumnIndex("anverso"))
            enverso = cursor.getString(cursor.getColumnIndex("enverso"))
            pista = cursor.getString(cursor.getColumnIndex("pistas"))
            idFicha = cursor.getInt(cursor.getColumnIndex("id_ficha"))
            idTematica_Ficha = cursor.getInt(cursor.getColumnIndex("id_tematica"))

            txtAnverso.setText(anverso)
            txtEnverso.setText(enverso)
            txtPista.setText(pista)

        } else {
            Toast.makeText(
                this, "NO HAY FICHAS",
                Toast.LENGTH_LONG
            ).show()
        }

    }
}