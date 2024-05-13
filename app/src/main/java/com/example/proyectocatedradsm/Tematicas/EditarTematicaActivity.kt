package com.example.proyectocatedradsm.Tematicas

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectocatedradsm.Dashboard
import com.example.proyectocatedradsm.R
import com.example.proyectocatedradsm.model.Tematicas

class EditarTematicaActivity : AppCompatActivity() {

    private var managerTematicas: Tematicas? = null
    private var colorBase: String = "base"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tematica)

        managerTematicas = Tematicas(this)
        val btnEditar = findViewById<Button>(R.id.btnEditarTematica)
        val btnCancel = findViewById<Button>(R.id.btnCancelEditTematica)
        val textNombre = findViewById<EditText>(R.id.txtNombreTematica)
        val textDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val spinner: Spinner? = findViewById(R.id.coloresCombobox)
        val btnCancelar = findViewById<Button>(R.id.btnCancelEditTematica)
        val btnRegresar = findViewById<ImageButton>(R.id.btnRegresar)

        val extras = intent.extras
        var validarText = false
        val nombre: String? = extras?.getString("nombre")
        val descripcion: String? = extras?.getString("descripcion")
        val color: String? = extras?.getString("color")
        val id = extras?.getInt("id")

        btnCancelar.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        val coloresMap = HashMap<String, String>()
        coloresMap["Verde"] = "#12a352"
        coloresMap["Azul"] = "#75cff0"
        coloresMap["Rojo"] = "#ec7953"
        coloresMap["Morado"] = "#B3B7EE"
        coloresMap["Vino"] = "#A61C3C"
        coloresMap["Gris"] = "#6D6466"

        val llave = coloresMap.filterValues { it == color }.keys.toString()
        val llaveSinPlecas: String = (llave.replace("[\\[\\]]".toRegex(), ""))
        val coloresList = ArrayList<String>(coloresMap.keys)
        val defaultValue = coloresList.indexOf(llaveSinPlecas)

        val adaptor = ArrayAdapter(this, android.R.layout.simple_spinner_item, coloresList)
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adaptor
        spinner?.setSelection(defaultValue)
        textNombre.setText(nombre)
        textDescripcion.setText(descripcion)

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    this@EditarTematicaActivity,
                    "No ha seleccionado ningun elemento.",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                colorBase = spinner?.getItemAtPosition(position).toString();
            }

        }

        btnEditar.setOnClickListener {

            if (funValidarText(textNombre.text.toString()) && funValidarText(textDescripcion.text.toString())) {
                validarText = true;
            }

            if (validarText) {
                managerTematicas!!.updateTematica(
                    id,
                    textNombre.text.toString(),
                    textDescripcion.text.toString(),
                    coloresMap[colorBase].toString()
                )
                Toast.makeText(
                    this@EditarTematicaActivity,
                    "Update realizado con exito.",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, Dashboard::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(this, "Ingrese todos los valores", Toast.LENGTH_LONG).show()
            }

        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

    }

    fun funValidarText(text: String): Boolean {
        return !text.isEmpty()
    }
}