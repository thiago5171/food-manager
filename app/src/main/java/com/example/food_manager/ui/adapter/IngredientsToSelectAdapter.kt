package com.example.food_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_manager.R
import com.example.food_manager.domain.Ingredient

class IngredientsToSelectAdapter(private val ingredients: ArrayList<Ingredient>)
    : RecyclerView.Adapter<IngredientsToSelectAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView
        val quantityView: TextView
        val cancelView: ImageView

        init {
            nameView = view.findViewById(R.id.ingredient_name)
            quantityView = view.findViewById(R.id.ingredient_quantity)
            cancelView = view.findViewById(R.id.ingredient_cancel_icon)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_single_ingredient_to_select, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameView.text = ingredients[position].name
        viewHolder.quantityView.text = ingredients[position].quantity.toString()
        viewHolder.cancelView.setOnClickListener {
            ingredients.remove(ingredients[position])
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, ingredients.size)
        }
    }


    override fun getItemCount() = ingredients.size
}