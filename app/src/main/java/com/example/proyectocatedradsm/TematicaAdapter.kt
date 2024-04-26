package com.example.proyectocatedradsm

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TematicaAdapter(
    private val tematicas: MutableList<Tematica>,
    private val colorTematica: String
) :
    RecyclerView.Adapter<TematicaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)
        val cardView: CardView = itemView.findViewById(R.id.cardViewTematica)
    }

    private val coloresTematica = HashMap<Int, String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tematica_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (tematicas.isEmpty()) {
            showMessage(holder.itemView.context)
            holder.itemView.visibility = View.GONE
            return
        }

        val tematica = tematicas[position]
        holder.textViewNombre.text = tematica.nombre
        holder.textViewDescripcion.text = tematica.descripcion

        holder.itemView.setOnClickListener {
            // Llamar al método para mostrar el modal de opciones
            modalOpciones(holder.itemView.context, tematica)
        }

        val colorInt = Color.parseColor(tematica.color)
        coloresTematica[position] = colorInt.toString()

        // Establecer el color de fondo del CardView de esta tarjeta
        holder.cardView.setCardBackgroundColor(colorInt)
    }

    private fun modalOpciones(context: Context, tematica: Tematica) {
        // Aquí puedes crear e mostrar el modal de opciones
        // Por ejemplo, puedes usar un AlertDialog
        AlertDialog.Builder(context)
            .setTitle("Opciones para ${tematica.nombre}")
            .setMessage("¿Qué deseas hacer con esta temática?")
            .setPositiveButton("Ponerme a prueba") { dialog, which ->
                val intent = Intent(context, EditarFichaActivity::class.java)
                intent.putExtra("tematica_id", tematica.idTematica)
                context.startActivity(intent)
            }
            .setNegativeButton("Ver fichas") { dialog, which ->
                val intent = Intent(context, FichasActivity::class.java)
                intent.putExtra("tematica_id", tematica.idTematica)
                context.startActivity(intent)
            }
            .setNeutralButton("Cancelar") { dialog, which ->
            }
            .show()
    }

    override fun getItemCount(): Int {
        return if (tematicas.isEmpty()) 1 else tematicas.size
    }

    private fun showMessage(context: Context) {
        Toast.makeText(context, "La lista de temáticas está vacía.", Toast.LENGTH_SHORT).show()
    }

    private fun cambiarColor(color: String) {

    }
}