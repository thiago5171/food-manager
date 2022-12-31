package com.example.food_manager.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.food_manager.R
import com.example.food_manager.databinding.ActivitySingleIngredientBinding
import com.example.food_manager.domain.recipe.Ingredient

class IngredientsAdapter(
    val context: Context,
    private val ingredients: List<Ingredient>
) : BaseAdapter() {
    override fun getCount(): Int {
        return ingredients.size
    }

    override fun getItem(index: Int): Any {
        return ingredients[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(index: Int, viewLayout: View?, viewGroup: ViewGroup?): View {
        var layout: View? = viewLayout
        val inflater = LayoutInflater.from(context)

        if (layout == null) {
            layout = inflater.inflate(R.layout.activity_single_ingredient, viewGroup, false)
        }

        val ingredient = getItem(index) as Ingredient

        val name = layout!!.findViewById<TextView>(R.id.ingredient_name)
        val quantity = layout.findViewById<TextView>(R.id.ingredient_quantity)
//        val unitMeasurement = layout.findViewById<TextView>(R.id.ingredient_unit_measurement)

        name.text = ingredient.name
        quantity.text = ingredient.quantity.toString()
//        unitMeasurement.text = ingredient.unitMeasurement
        return layout
    }
}