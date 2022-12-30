package com.example.food_manager.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.food_manager.domain.RecipeItemListModel
import com.example.food_manager.R

class RecipeListAdapter (val ctx: Context, val recipes: ArrayList<RecipeItemListModel>) : BaseAdapter(){
    override fun getCount(): Int {
        return recipes.size
    }

    override fun getItem(index: Int): Any {
        return recipes.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(index: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var view = inflater.inflate(R.layout.activity_recipe_item_short, null)
        val title = view.findViewById<TextView>(R.id.recipeTitle)
        val subTitle = view.findViewById<TextView>(R.id.recipeSubtitle)
        val urlImage = view.findViewById<ImageView>(R.id.recipeImage)
        val cost = view.findViewById<TextView>(R.id.recipeCost)

        title.text = recipes.get(index).tittle
        subTitle.text = recipes.get(index).subtitle
        urlImage.tag = recipes.get(index).urlImage
        Glide.with(ctx).load(recipes.get(index).urlImage).into(urlImage)
        cost.text = "R$" + recipes.get(index).cost
        return view

    }

}
