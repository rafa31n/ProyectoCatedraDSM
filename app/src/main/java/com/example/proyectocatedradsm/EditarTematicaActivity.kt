package com.example.proyectocatedradsm

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectocatedradsm.model.Fichas
import com.example.proyectocatedradsm.model.Tematicas
import java.util.Collections

class EditarTematicaActivity : AppCompatActivity() {

    private var managerTematicas: Tematicas? = null
    private var colorBase : String = "base"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tematica)

        managerTematicas = Tematicas(this)

        val btnEditar = findViewById<Button>(R.id.btnEditarTematica)
        val btnCancel = findViewById<Button>(R.id.btnCancelEditTematica)


        val textNombre = findViewById<EditText>(R.id.txtNombreTematica)
        val textDescripcion = findViewById<EditText>(R.id.txtDescripcion)

        val extras = intent.extras
        var validarText = false
        val nombre : String? = extras?.getString("nombre")
        val descripcion : String? = extras?.getString("descripcion")
        val color : String? = extras?.getString("color")
        val id = extras?.getInt("id")

        val spinner : Spinner? = findViewById(R.id.coloresCombobox)

        val coloresMap = HashMap<String, String>()
        coloresMap["Azul"] = "#6696b4"
        coloresMap["Verde"] = "#17e11e"

        val llave = coloresMap.filterValues { it == color }.keys.toString()

        val llaveSinPlecas : String = (llave.replace("[\\[\\]]".toRegex(), ""))


        val coloresList = ArrayList<String>(coloresMap.keys)
        val defaultValue = coloresList.indexOf(llaveSinPlecas)


        val adaptor = ArrayAdapter(this, android.R.layout.simple_spinner_item, coloresList)
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adaptor
        spinner?.setSelection(defaultValue)
        textNombre.setText(nombre)
        textDescripcion.setText(descripcion)

        spinner?.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@EditarTematicaActivity, "No ha seleccionado ningun elemento.", Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                colorBase = spinner?.getItemAtPosition(position).toString();
            }

        }

        btnEditar.setOnClickListener{

            if(funValidarText(textNombre.text.toString()) && funValidarText(textDescripcion.text.toString())){
                validarText = true;

            }

            if(validarText){
                managerTematicas!!.updateTematica(
                    id,
                    textNombre.text.toString(),
                    textDescripcion.text.toString(),
                    coloresMap[colorBase].toString()

                )
                Toast.makeText(this@EditarTematicaActivity, "Update realizado con exito.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)

            }else{
                Toast.makeText(this, "Ingrese todos los valores", Toast.LENGTH_LONG).show()
            }

        }

        btnCancel.setOnClickListener{
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

    }

    fun funValidarText(text: String): Boolean {
        return !text.isEmpty()
    }
}