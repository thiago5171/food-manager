package com.example.food_manager.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        val delete = view.findViewById<ImageView>(R.id.deleteButton)
        val id= recipes.get(index).getId()
        delete.setOnClickListener{
            val toast = Toast.makeText(ctx, "deletou mentalmente $id ", Toast.LENGTH_SHORT
            )
            toast.show()
        }
        title.text = recipes.get(index).getTitle()
        subTitle.text = recipes.get(index).getSubtitle()
        urlImage.tag = recipes.get(index).getUrlImage()
        Glide.with(ctx).load(recipes.get(index).getUrlImage()).into(urlImage)
        cost.text = "R$" + recipes.get(index).getCost()
        return view

    }

}
