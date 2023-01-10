package com.example.food_manager.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_manager.R
import com.example.food_manager.data.dao.IngredientDAO
import com.example.food_manager.domain.Ingredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IngredientsAdapter(private val ingredients: ArrayList<Ingredient>, val ingredientsDAO: IngredientDAO)
    : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView
        val descriptionView: TextView
        val priceView: TextView
        val quantityView: TextView
        val deleteView: ImageView

        init {
            nameView = view.findViewById(R.id.single_ingredient_name)
            descriptionView = view.findViewById(R.id.single_ingredient_description)
            priceView = view.findViewById(R.id.single_ingredient_price)
            quantityView = view.findViewById(R.id.single_ingredient_quantity_plus_unit)
            deleteView = view.findViewById(R.id.delete_ingredient_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_single_ingredient, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.nameView.text = ingredients[position].name
        viewHolder.descriptionView.text = ingredients[position].description
        viewHolder.priceView.text = "R$" + ingredients[position].price.toString()
        viewHolder.quantityView.text = ingredients[position].quantity.toString() + " " +
                ingredients[position].unitMeasurement
        viewHolder.deleteView.setOnClickListener {
            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    ingredientsDAO.deleteOne(ingredients[position])
                    ingredients.clear()
                    ingredients.addAll(ingredientsDAO.findAll())
                }
            }
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, ingredients.size)
        }
    }


    override fun getItemCount() = ingredients.size
}