package com.example.proyectocatedradsm

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectocatedradsm.model.Fichas
import com.example.proyectocatedradsm.model.Tematicas

class fichaAdapter(
    private val fichas: MutableList<Ficha>,

):  RecyclerView.Adapter<fichaAdapter.ViewHolder>() {
    private var managerFichas: Fichas? = null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewANVERSO: TextView = itemView.findViewById(R.id.textViewANVERSO)
        val textViewENVERSO: TextView = itemView.findViewById(R.id.textViewENVERSO)
        val cardView: CardView = itemView.findViewById(R.id.cardViewFichas)
        val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): fichaAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_ficha_adapter, parent, false)
        return fichaAdapter.ViewHolder(view)
    }
    override fun onBindViewHolder(holder: fichaAdapter.ViewHolder, position: Int) {
        if (fichas.isEmpty()) {
            showMessage(holder.itemView.context)
            holder.itemView.visibility = View.GONE
            return
        }

        val ficha = fichas[position]
        holder.textViewANVERSO.text=ficha.anverso
        holder.textViewENVERSO.text=ficha.enverso


        //Botones
        holder.btnEditar.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "BTN EDITAR.", Toast.LENGTH_SHORT
            ).show()
        }

        holder.btnEliminar.setOnClickListener {

            ficha.id_ficha?.let { it1 -> managerFichas!!.deleteFicha(it1.toString()) }
            fichas.remove(ficha)
            notifyDataSetChanged()
            Toast.makeText(
                holder.itemView.context,
                "BTN ELIMINAR.", Toast.LENGTH_SHORT
            ).show()


        }
    }
    override fun getItemCount(): Int {
        return if (fichas.isEmpty()) 1 else fichas.size
    }
    private fun showMessage(context: Context) {
        Toast.makeText(context, "La lista de Ficha está vacía.", Toast.LENGTH_SHORT).show()
    }
}