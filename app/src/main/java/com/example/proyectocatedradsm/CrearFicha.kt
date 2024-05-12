package com.example.proyectocatedradsm

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectocatedradsm.db.HelperDB
import com.example.proyectocatedradsm.model.Fichas

class CrearFicha : AppCompatActivity(){

    private var managerFichas: Fichas? = null
    private var dbHelper: HelperDB? = null
    private var db: SQLiteDatabase? = null
    private var cursor: Cursor? = null

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crear_fichas)
        var validarText : Boolean = false;
        val txtAnverso = findViewById<EditText>(R.id.textAnverso)
        val txtEnverso = findViewById<EditText>(R.id.textEnverso)
        val txtClavesPistas = findViewById<EditText>(R.id.textClavesPistas)


        managerFichas  = Fichas(this)

        val btnGuardar = findViewById<Button>(R.id.btnCrearFicha)
        val btnCancelar = findViewById<Button>(R.id.btnCancerlarFicha)


        btnGuardar.setOnClickListener {

            if(funValidarText(txtAnverso.text.toString()) && funValidarText(txtEnverso.text.toString()) && funValidarText(txtClavesPistas.text.toString())){
                validarText = true;
            }

            if(validarText){
                managerFichas!!.addNewFicha(
                    1,
                    txtAnverso.text.toString(),
                    txtEnverso.text.toString(),
                    txtClavesPistas.text.toString()
                )
                Toast.makeText(this, "Ficha creada correctamente", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Ingrese todos los valores", Toast.LENGTH_LONG).show()
            }

        }

        btnCancelar.setOnClickListener{
            val intent = Intent(this, Dashboard::class.java)
        }


    }

    fun funValidarText(text: String): Boolean {
        return !text.isEmpty()
    }

}