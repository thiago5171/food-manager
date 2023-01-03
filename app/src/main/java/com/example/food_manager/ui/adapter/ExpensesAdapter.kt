package com.example.food_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_manager.R
import com.example.food_manager.data.dao.ExpenseDAO
import com.example.food_manager.domain.Expense

class ExpensesAdapter(val expenses: ArrayList<Expense>, val expenseDAO: ExpenseDAO) : RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView
        val priceView: TextView
        val descriptionView: TextView
        val deleteButton: ImageView

        init {
            titleView = view.findViewById(R.id.expenseTitle)
            priceView = view.findViewById(R.id.expensePrice)
            descriptionView = view.findViewById(R.id.recipe_description)
            deleteButton = view.findViewById(R.id.delete_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.activity_)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}