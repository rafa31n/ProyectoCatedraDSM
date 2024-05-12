package com.example.proyectocatedradsm

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocatedradsm.model.Tematicas

class TematicaAdapter(
    private val tematicas: MutableList<Tematica>,
    private val colorTematica: String
) :
    RecyclerView.Adapter<TematicaAdapter.ViewHolder>() {
    private var managerTematicas: Tematicas? = null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val textViewDescripcion: TextView = itemView.findViewById(R.id.textViewDescripcion)
        val cardView: CardView = itemView.findViewById(R.id.cardViewTematica)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
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

        //Botones
        holder.btnEditar.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "BTN EDITAR.", Toast.LENGTH_SHORT
            ).show()
        }

        holder.btnEliminar.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "BTN ELIMINAR.", Toast.LENGTH_SHORT
            ).show()
            mostrarModalEliminar(holder.itemView.context, tematica)
        }
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
    private fun mostrarModalEliminar(context: Context, tematica: Tematica) {
        AlertDialog.Builder(context)
            .setTitle("Eliminar Temática")
            .setMessage("¿Estás seguro de que quieres eliminar la temática ${tematica.nombre}?")
            .setPositiveButton("Eliminar") { dialog, which ->
                // Llama a la función para eliminar la temática
              //  println("id de la cosa ${tematica.idTematica}")
               eliminarTematica(context,tematica)

            }
            .setNegativeButton("Cancelar") { dialog, which ->
                // No hacer nada, simplemente cerrar el diálogo
                dialog.dismiss()
            }
            .show()
    }
    private fun eliminarTematica(context: Context, tematica: Tematica) {
       val idTematica = tematica.idTematica ?: return // Si el ID es null, retorna sin hacer nada
     //   println("El id de tematica= $idTematica")
       // managerTematicas!!.deleteTematica(idTematica) // Usando el operador de llamada segura
        notifyDataSetChanged()
        Toast.makeText(context, "Temática ${tematica.nombre} eliminada.", Toast.LENGTH_SHORT).show()
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