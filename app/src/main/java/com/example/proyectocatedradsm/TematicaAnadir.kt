package com.example.proyectocatedradsm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectocatedradsm.model.Fichas
import com.example.proyectocatedradsm.model.Tematicas

class TematicaAnadir : AppCompatActivity() {
    private var managerTematicas: Tematicas? = null
    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        managerTematicas = Tematicas(this)
        setContentView(R.layout.activity_tematica_anadir)
        val colorSpinner: Spinner = findViewById(R.id.colorSpinner)
        var selectedColor: String=""
        var hexColor: String=""
        val txtTematica = findViewById<EditText>(R.id.editTextTematica)
        val txtDescripcion = findViewById<EditText>(R.id.editTextDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardarTematica)
        ArrayAdapter.createFromResource(
            this,
            R.array.coloresTematica,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            colorSpinner.adapter = adapter
        }

        colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
               selectedColor= parent.getItemAtPosition(position).toString()
                when   (selectedColor){
                    "Verde" -> hexColor = "#12a352"
                    "azul" -> hexColor = "#75cff0"
                    "Rojo" -> hexColor = "#ec7953"
                    else -> hexColor = "#ec7953"
                }
                println( selectedColor + " "+hexColor)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Método requerido, pero no es necesario implementar nada aquí
            }
        }


        btnGuardar.setOnClickListener{
            var tematica: String = ""
            var descricion: String = ""
            tematica=txtTematica.text.toString()
            descricion=txtDescripcion.text.toString()
            println( "la tematica $tematica, la des $descricion , color $selectedColor, $hexColor")
            managerTematicas!!.addNewTematica(tematica,descricion,hexColor)
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
            Toast.makeText(
                this, "Tematica se ha guardados correctamente.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}