package com.example.food_manager.domain.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.food_manager.R
import com.example.food_manager.domain.ExpenseItemListModel

class ExpenseListAdapter (val ctx: Context, val expenses: ArrayList<ExpenseItemListModel>): BaseAdapter() {
    override fun getCount(): Int {
        return expenses.size
    }

    override fun getItem(index: Int): Any {
        return expenses.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(index: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_expense_item_short, null)
        val title = view.findViewById<TextView>(R.id.expenseTitle)
        val subTitle = view.findViewById<TextView>(R.id.expenseSubtitle)
        val urlImage = view.findViewById<ImageView>(R.id.expenseImage)
        val price = view.findViewById<TextView>(R.id.expensePrice)

        title.text = expenses.get(index).title
        subTitle.text = expenses.get(index).subtitle
        urlImage.tag = expenses.get(index).urlImage
        Glide.with(ctx).load(expenses.get(index).urlImage).into(urlImage)
        price.text = "R$" + expenses.get(index).price
        return view
    }
}