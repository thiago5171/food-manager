package com.example.food_manager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_manager.R
import com.example.food_manager.data.dao.ExpenseDAO
import com.example.food_manager.domain.Expense.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpensesAdapter(val expenses: ArrayList<Expense>, val expensesDAO: ExpenseDAO) : RecyclerView.Adapter<ExpensesAdapter.ViewHolder>() {
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
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_expense_item_short, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.titleView.text = expenses[position].name
        viewHolder.priceView.text = "R$${expenses[position].price}"
        viewHolder.descriptionView.text = expenses[position].description

        viewHolder.deleteButton.setOnClickListener {
            val scope = MainScope()
            scope.launch {
                withContext(Dispatchers.IO) {
                    expensesDAO.deleteOne(expenses[position])
                    expenses.clear()
                    expenses.addAll(expensesDAO.findAll())
                }
            }
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, expenses.size)
        }
    }

    override fun getItemCount() = expenses.size


}