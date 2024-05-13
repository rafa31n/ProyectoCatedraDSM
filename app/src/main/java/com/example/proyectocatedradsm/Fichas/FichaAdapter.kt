package com.example.proyectocatedradsm.Fichas


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocatedradsm.R
import com.example.proyectocatedradsm.Tematicas.Tematica
import com.example.proyectocatedradsm.model.Fichas
import com.example.proyectocatedradsm.model.Tematicas

class FichaAdapter(
    private val fichas: MutableList<Ficha>,

    ) : RecyclerView.Adapter<FichaAdapter.ViewHolder>() {
    private var managerFichas: Fichas? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewAnverso: TextView = itemView.findViewById(R.id.textViewANVERSO)
        val textViewReverso: TextView = itemView.findViewById(R.id.textViewENVERSO)
        val cardView: CardView = itemView.findViewById(R.id.cardViewFichas)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        managerFichas = Fichas(parent.context)
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_ficha_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (fichas.isEmpty()) {
            showMessage(holder.itemView.context)
            holder.itemView.visibility = View.GONE
            return
        }

        val ficha = fichas[position]
        holder.textViewAnverso.text = ficha.anverso
        holder.textViewReverso.text = ficha.reverso

        //Botones
        holder.btnEditar.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditarFichaActivity::class.java)
            intent.putExtra("anverso", ficha.anverso)
            intent.putExtra("reverso", ficha.reverso)
            intent.putExtra("pistas", ficha.pistas)

            intent.putExtra("idFicha", ficha.id_ficha.toString())
            intent.putExtra("idTematicaFicha", ficha.idTematica.toString())
            holder.cardView.context.startActivity(intent)
        }

        holder.btnEliminar.setOnClickListener {
            mostrarModalEliminar(holder.itemView.context, ficha)
        }
    }

    private fun mostrarModalEliminar(context: Context, ficha: Ficha) {
        AlertDialog.Builder(context)
            .setTitle("Eliminar ficha")
            .setMessage("¿Estás seguro de que quieres eliminar la ficha ${ficha.anverso}?")
            .setPositiveButton("Eliminar") { dialog, which ->
                ficha.id_ficha?.let { it1 -> managerFichas!!.deleteFicha(it1.toString()) }
                fichas.remove(ficha)
                notifyDataSetChanged()
                Toast.makeText(
                    context, "Ficha ${ficha.anverso} eliminada.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                // No hacer nada, simplemente cerrar el diálogo
                dialog.dismiss()
            }
            .show()
    }

    override fun getItemCount(): Int {
        return if (fichas.isEmpty()) 1 else fichas.size
    }

    private fun showMessage(context: Context) {
        Toast.makeText(context, "La lista de Ficha está vacía.", Toast.LENGTH_SHORT).show()
    }
}
